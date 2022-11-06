package br.com.moc.orders.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_order_stock_movement")
public class OrderStockMovementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_order_stock_movement_generator")
    @SequenceGenerator(name = "tb_order_stock_movement_generator", sequenceName = "tb_order_stock_movement_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "stock_movement_id")
    private StockMovementEntity stockMovement;

    private LocalDateTime creationDate;

    private Integer quantityAttended;

    @PrePersist
    public void onInsert() {
        this.creationDate = LocalDateTime.now();
    }
}
