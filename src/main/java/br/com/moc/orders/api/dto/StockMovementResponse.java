package br.com.moc.orders.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StockMovementResponse {
    private LocalDateTime creationDate;

    private Long itemId;

    private Integer quantity;
}
