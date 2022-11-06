package br.com.moc.orders.domain.service;

import br.com.moc.orders.api.dto.OrderStockMovementTraceResponse;
import br.com.moc.orders.api.dto.OrderTraceResponse;
import br.com.moc.orders.domain.model.*;
import br.com.moc.orders.domain.repository.OrderRepository;
import br.com.moc.orders.domain.repository.OrderStockMovementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;

    private OrderStockMovementRepository orderStockMovementRepository;

    public OrderEntity save(OrderEntity orderEntity) {
        return orderRepository.saveAndFlush(orderEntity);
    }

    public List<OrderEntity> list() {
        return this.orderRepository.findAll();
    }

    public Optional<OrderEntity> findById(Long id) {
        return this.orderRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return orderRepository.existsById(id);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    public long countByItem(Long itemId) {
        ItemEntity item = new ItemEntity();
        item.setId(itemId);
        return orderRepository.countByItem(item);
    }

    public long countByUser(Long userId) {
        UserEntity user = new UserEntity();
        user.setId(userId);
        return orderRepository.countByUser(user);
    }

    public List<OrderEntity> findByOrderStatusIsNot(OrderStatus status) {
        return this.orderRepository.findByOrderStatusIsNot(status);
    }

    public List<OrderTraceResponse> traceOrders() {
        List<OrderTraceResponse> orderTraces = new ArrayList<>();
        List<OrderEntity> orders = this.orderRepository.findAll();

        OrderTraceResponse orderTraceResponse;
        OrderStockMovementTraceResponse orderStockMovementTraceResponse;
        for (OrderEntity orderObj : orders) {
            orderTraceResponse = new OrderTraceResponse();
            orderTraceResponse.setId(orderObj.getId());
            orderTraceResponse.setOrderStatus(orderObj.getOrderStatus().name());
            orderTraceResponse.setQuantity(orderObj.getQuantity());
            orderTraceResponse.setCreationDate(orderObj.getCreationDate());

            List<OrderStockMovementEntity> orderStockMovements = this.orderStockMovementRepository.findByOrder(orderObj);
            List<OrderStockMovementTraceResponse> stockMovements = new ArrayList<>();
            for (OrderStockMovementEntity objOrderStockMovementEntity : orderStockMovements) {
                orderStockMovementTraceResponse = new OrderStockMovementTraceResponse();
                orderStockMovementTraceResponse.setStockMovementId(objOrderStockMovementEntity.getStockMovement().getId());
                orderStockMovementTraceResponse.setQuantityAttended(objOrderStockMovementEntity.getQuantityAttended());
                stockMovements.add(orderStockMovementTraceResponse);
            }
            orderTraceResponse.setStockMovements(stockMovements);

            orderTraces.add(orderTraceResponse);
        }
        return orderTraces;
    }
}
