package br.com.moc.orders.domain.service;

import br.com.moc.orders.domain.model.*;
import br.com.moc.orders.domain.repository.OrderRepository;
import br.com.moc.orders.domain.repository.OrderStockMovementRepository;
import br.com.moc.orders.domain.repository.StockMovementLogRepository;
import br.com.moc.orders.domain.repository.StockMovementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j(topic = "ProcessOrdersService")
public class ProcessOrdersService {

    private final Collection<OrderStatus> statuses = Arrays.asList(OrderStatus.NEW, OrderStatus.IN_PROGRESS);
    private StockMovementRepository stockMovementRepository;
    private OrderRepository orderRepository;
    private OrderStockMovementRepository orderStockMovementRepository;
    private StockMovementLogRepository stockMovementLogRepository;
    private EmailService emailService;

    @Async("taskExecutor")
    public void processOrders(StockMovementEntity stockMovementEntity) {
        List<StockMovementEntity> stockMovements = this.stockMovementRepository.findByQuantityGreaterThan(0);
        List<OrderEntity> orders = this.orderRepository.findByOrderStatusIn(statuses);

        if (orders.isEmpty()) {
            log.debug("There isn't order to process");
        } else {
            Integer quantityToComplete;
            StockMovementLogEntity stockMovementLogEntity;
            OrderStockMovementEntity orderStockMovementEntity;
            for (OrderEntity order : orders) {
                quantityToComplete = order.getQuantityToComplete();
                for (StockMovementEntity stockMovement : stockMovements) {
                    if (quantityToComplete > 0) {
                        if (stockMovement.getQuantity() >= quantityToComplete) {
                            generateOrderFulfillment(order, stockMovement, quantityToComplete);
                            order = completeOrder(order);
                            stockMovement = updateStockMovementQuantity(stockMovement, (stockMovement.getQuantity() - quantityToComplete));
                            saveStockMovementLog(stockMovement, quantityToComplete, StockOperation.REMOVE_STOCK);

                            emailService.sendEmail(order);

                            quantityToComplete = 0;
                            break;
                        } else {
                            quantityToComplete = quantityToComplete - stockMovement.getQuantity();
                            generateOrderFulfillment(order, stockMovement, stockMovement.getQuantity());
                            order = inProgressOrder(order, quantityToComplete);
                            saveStockMovementLog(stockMovement, stockMovement.getQuantity(), StockOperation.REMOVE_STOCK);
                            stockMovement = updateStockMovementQuantity(stockMovement, 0);
                        }
                    } else {
                        break;
                    }
                }

                stockMovements = this.stockMovementRepository.findByQuantityGreaterThan(0);
            }
        }
    }

    private void generateOrderFulfillment(OrderEntity order, StockMovementEntity stockMovement, Integer quantity) {
        OrderStockMovementEntity orderStockMovementEntity = new OrderStockMovementEntity();
        orderStockMovementEntity.setOrder(order);
        orderStockMovementEntity.setStockMovement(stockMovement);
        orderStockMovementEntity.setQuantityAttended(quantity);
        this.orderStockMovementRepository.save(orderStockMovementEntity);
    }

    private OrderEntity completeOrder(OrderEntity order) {
        order.setQuantityToComplete(0);
        order.setOrderStatus(OrderStatus.COMPLETED);
        return this.orderRepository.saveAndFlush(order);
    }

    private StockMovementEntity updateStockMovementQuantity(StockMovementEntity stockMovement, Integer newQuantity) {
        stockMovement.setQuantity(newQuantity);
        return this.stockMovementRepository.saveAndFlush(stockMovement);
    }

    private void saveStockMovementLog(StockMovementEntity stockMovementEntity, Integer quantity, StockOperation operation) {
        log.debug("Logging Stock Movement {} {} {}", stockMovementEntity.toString(), quantity, operation);
        StockMovementLogEntity stockMovementLog = new StockMovementLogEntity();
        stockMovementLog.setStockMovement(stockMovementEntity);
        stockMovementLog.setQuantity(quantity);
        stockMovementLog.setOperation(operation);
        this.stockMovementLogRepository.save(stockMovementLog);
    }

    private OrderEntity inProgressOrder(OrderEntity order, Integer quantityToComplete) {
        order.setQuantityToComplete(quantityToComplete);
        order.setOrderStatus(OrderStatus.IN_PROGRESS);
        return this.orderRepository.saveAndFlush(order);
    }
}
