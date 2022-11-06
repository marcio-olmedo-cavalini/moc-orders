package br.com.moc.orders.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_user_generator")
    @SequenceGenerator(name = "tb_user_generator", sequenceName = "tb_user_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String email;
}
