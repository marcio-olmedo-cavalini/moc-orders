package br.com.moc.orders.api.controller;

import br.com.moc.orders.api.dto.ItemResponse;
import br.com.moc.orders.api.dto.OrderRequest;
import br.com.moc.orders.api.dto.OrderResponse;
import br.com.moc.orders.api.dto.OrderTraceResponse;
import br.com.moc.orders.domain.exception.BusinessException;
import br.com.moc.orders.domain.model.OrderEntity;
import br.com.moc.orders.domain.model.OrderStatus;
import br.com.moc.orders.domain.service.OrderService;
import br.com.moc.orders.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    private OrderMapper orderMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse add(@RequestBody OrderRequest orderRequest) {
        OrderEntity orderEntity = orderMapper.toEntity(orderRequest);
        return orderMapper.toDto(orderService.save(orderEntity));
    }

    @GetMapping
    public List<OrderResponse> list() {
        return orderMapper.toDtoCollection(orderService.list());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        return orderService.findById(orderId)
                .map(order -> ResponseEntity.ok(orderMapper.toDto(order)))
                .orElseThrow(() -> new BusinessException("Order not found!"));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> update(@PathVariable Long orderId, @Valid @RequestBody OrderRequest orderRequest) {
        Optional<OrderEntity> orderEntityVerify = orderService.findById(orderId);
        if (!orderEntityVerify.isPresent()) {
            throw new BusinessException("order not found to update!");
        }

        if (!orderEntityVerify.get().getOrderStatus().equals(OrderStatus.NEW)) {
            throw new BusinessException("order can't be updated! Order status is not NEW");
        }

        OrderEntity orderEntity = orderMapper.toEntity(orderRequest);
        orderEntity.setId(orderEntityVerify.get().getId());
        orderEntity.setCreationDate(orderEntityVerify.get().getCreationDate());
        orderEntity.setOrderStatus(orderEntityVerify.get().getOrderStatus());
        orderService.save(orderEntity);

        return ResponseEntity.ok(orderMapper.toDto(orderEntity));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ItemResponse> delete(@PathVariable Long orderId) {
        Optional<OrderEntity> orderEntityVerify = orderService.findById(orderId);
        if (!orderEntityVerify.isPresent()) {
            throw new BusinessException("order not found to delete!");
        }

        if (!orderEntityVerify.get().getOrderStatus().equals(OrderStatus.NEW)) {
            throw new BusinessException("order can't be deleted! Order status is not NEW");
        }

        orderService.delete(orderId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/trace")
    public ResponseEntity<List<OrderTraceResponse>> traceOrder() {
        List<OrderTraceResponse> orderTraceResponseList = this.orderService.traceOrders();
        return ResponseEntity.ok(orderTraceResponseList);
    }
}
