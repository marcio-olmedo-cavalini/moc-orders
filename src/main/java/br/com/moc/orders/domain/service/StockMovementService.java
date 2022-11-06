package br.com.moc.orders.domain.service;

import br.com.moc.orders.domain.model.ItemEntity;
import br.com.moc.orders.domain.model.StockMovementEntity;
import br.com.moc.orders.domain.model.StockMovementLogEntity;
import br.com.moc.orders.domain.model.StockOperation;
import br.com.moc.orders.domain.repository.StockMovementLogRepository;
import br.com.moc.orders.domain.repository.StockMovementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j(topic = "StockMovementService")
public class StockMovementService {
    private StockMovementRepository stockMovementRepository;

    private StockMovementLogRepository stockMovementLogRepository;

    private ProcessOrdersService processOrdersService;

    public StockMovementEntity save(StockMovementEntity stockMovementEntity) {
        log.debug("Saving Stock Movement {}", stockMovementEntity);
        stockMovementRepository.saveAndFlush(stockMovementEntity);
        saveStockMovementLog(stockMovementEntity, 0, StockOperation.ADD_STOCK);
        processOrdersService.processOrders(stockMovementEntity);
        return stockMovementEntity;
    }

    public List<StockMovementEntity> list() {
        return this.stockMovementRepository.findAll();
    }

    public Optional<StockMovementEntity> findById(Long id) {
        return this.stockMovementRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return this.stockMovementRepository.existsById(id);
    }

    public void delete(Long id) {
        stockMovementRepository.deleteById(id);
    }

    public long countByItem(Long itemId) {
        ItemEntity item = new ItemEntity();
        item.setId(itemId);
        return stockMovementRepository.countByItem(item);
    }

    public List<StockMovementEntity> findByQuantityGreaterThan(Integer quantity) {
        return this.stockMovementRepository.findByQuantityGreaterThan(quantity);
    }

    public void saveStockMovementLog(StockMovementEntity stockMovementEntity, Integer quantity, StockOperation operation) {
        log.debug("Logging Stock Movement {} {} {}", stockMovementEntity.toString(), quantity, operation);
        StockMovementLogEntity stockMovementLog = new StockMovementLogEntity();
        stockMovementLog.setStockMovement(stockMovementEntity);
        stockMovementLog.setQuantity(quantity == 0 ? stockMovementEntity.getQuantity() : quantity);
        stockMovementLog.setOperation(operation);
        stockMovementLogRepository.save(stockMovementLog);
    }
}
