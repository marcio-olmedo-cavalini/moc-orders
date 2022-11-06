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
@Table(name = "tb_order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_order_generator")
    @SequenceGenerator(name = "tb_order_generator", sequenceName = "tb_order_id_seq", allocationSize = 1)
    private Long id;

    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    private Integer quantity;

    private Integer quantityToComplete;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @PrePersist
    public void onInsert() {
        this.creationDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.NEW;
        this.quantityToComplete = this.quantity;
    }
}
