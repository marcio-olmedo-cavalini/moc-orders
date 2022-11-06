package br.com.moc.orders.domain.repository;

import br.com.moc.orders.domain.model.ItemEntity;
import br.com.moc.orders.domain.model.StockMovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovementEntity, Long> {
    long countByItem(ItemEntity item);

    List<StockMovementEntity> findByQuantityGreaterThan(Integer quantity);
}
