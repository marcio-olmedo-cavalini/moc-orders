package br.com.moc.orders.domain.repository;

import br.com.moc.orders.domain.model.ItemEntity;
import br.com.moc.orders.domain.model.OrderEntity;
import br.com.moc.orders.domain.model.OrderStatus;
import br.com.moc.orders.domain.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    long countByItem(ItemEntity item);

    long countByUser(UserEntity user);

    List<OrderEntity> findByOrderStatusIsNot(OrderStatus status);

    List<OrderEntity> findByOrderStatusIn(Collection<OrderStatus> statuses);
}
