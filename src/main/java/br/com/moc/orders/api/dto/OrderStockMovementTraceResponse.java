package br.com.moc.orders.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStockMovementTraceResponse {
    private Long stockMovementId;

    private Integer quantityAttended;
}
