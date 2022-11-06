package br.com.moc.orders.domain.repository;

import br.com.moc.orders.domain.model.OrderEntity;
import br.com.moc.orders.domain.model.OrderStockMovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderStockMovementRepository extends JpaRepository<OrderStockMovementEntity, Long> {
    List<OrderStockMovementEntity> findByOrder(OrderEntity order);
}
