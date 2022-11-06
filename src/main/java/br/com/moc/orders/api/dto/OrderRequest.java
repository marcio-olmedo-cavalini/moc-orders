package br.com.moc.orders.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private Long itemId;

    private Integer quantity;

    private Long userId;
}
