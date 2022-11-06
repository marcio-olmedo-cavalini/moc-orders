package br.com.moc.orders.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@Table(name = "tb_item")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_item_generator")
    @SequenceGenerator(name = "tb_item_generator", sequenceName = "tb_item_id_seq", allocationSize = 1)
    private Long id;

    private String name;
}
