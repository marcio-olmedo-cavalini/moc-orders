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
@Table(name = "tb_log_stock_movement")
public class StockMovementLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_log_stock_movement_generator")
    @SequenceGenerator(name = "tb_log_stock_movement_generator", sequenceName = "tb_log_stock_movement_id_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StockOperation operation;

    private LocalDateTime creationDate;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "stock_movement_id")
    private StockMovementEntity stockMovement;

    @PrePersist
    public void onInsert() {
        this.creationDate = LocalDateTime.now();
    }
}
