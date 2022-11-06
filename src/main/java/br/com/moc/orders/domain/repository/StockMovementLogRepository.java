package br.com.moc.orders.domain.repository;

import br.com.moc.orders.domain.model.StockMovementLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMovementLogRepository extends JpaRepository<StockMovementLogEntity, Long> {

}
