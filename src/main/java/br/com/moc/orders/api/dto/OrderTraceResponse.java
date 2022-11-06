package br.com.moc.orders.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderTraceResponse {
    private Long id;

    private LocalDateTime creationDate;

    private Integer quantity;

    private String orderStatus;

    private List<OrderStockMovementTraceResponse> stockMovements;
}
