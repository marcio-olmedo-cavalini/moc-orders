package br.com.moc.orders.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockMovementRequest {
    private Long itemId;

    private Integer quantity;
}
