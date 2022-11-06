package br.com.moc.orders.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@Table(name = "tb_stock_movement")
public class StockMovementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_stock_movement_generator")
    @SequenceGenerator(name = "tb_stock_movement_generator", sequenceName = "tb_stock_movement_id_seq", allocationSize = 1)
    private Long id;

    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    private Integer quantity;

    @PrePersist
    public void onInsert() {
        this.creationDate = LocalDateTime.now();
    }
}
