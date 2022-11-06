package br.com.moc.orders.api.controller;

import br.com.moc.orders.api.dto.StockMovementRequest;
import br.com.moc.orders.api.dto.StockMovementResponse;
import br.com.moc.orders.domain.exception.BusinessException;
import br.com.moc.orders.domain.model.StockMovementEntity;
import br.com.moc.orders.domain.service.StockMovementService;
import br.com.moc.orders.mapper.StockMovementMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/stockmovement")
public class StockMovementController {
    private StockMovementService stockMovementService;

    private StockMovementMapper stockMovementMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockMovementResponse addItem(@Valid @RequestBody StockMovementRequest stockMovementRequest) {
        StockMovementEntity stockMovementEntity = stockMovementMapper.toEntity(stockMovementRequest);
        stockMovementEntity.setId(null);
        return stockMovementMapper.toDto(stockMovementService.save(stockMovementEntity));
    }

    @GetMapping
    public List<StockMovementResponse> list() {
        return stockMovementMapper.toDtoCollection(stockMovementService.list());
    }

    @GetMapping("/{stockMovementId}")
    public ResponseEntity<StockMovementResponse> getItemById(@PathVariable Long stockMovementId) {
        return stockMovementService.findById(stockMovementId)
                .map(stockMovement -> ResponseEntity.ok(stockMovementMapper.toDto(stockMovement)))
                .orElseThrow(() -> new BusinessException("stock Movement not found!"));
    }

    @DeleteMapping("/{stockMovementId}")
    public ResponseEntity<StockMovementResponse> delete(@PathVariable Long stockMovementId) {
        if (!stockMovementService.existsById(stockMovementId)) {
            throw new BusinessException("stock Movement not found to delete!");
        }

        stockMovementService.delete(stockMovementId);

        return ResponseEntity.noContent().build();
    }
}
