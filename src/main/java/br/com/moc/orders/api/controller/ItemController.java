package br.com.moc.orders.api.controller;

import br.com.moc.orders.api.dto.ItemRequest;
import br.com.moc.orders.api.dto.ItemResponse;
import br.com.moc.orders.domain.exception.BusinessException;
import br.com.moc.orders.domain.model.ItemEntity;
import br.com.moc.orders.domain.service.ItemService;
import br.com.moc.orders.domain.service.OrderService;
import br.com.moc.orders.domain.service.StockMovementService;
import br.com.moc.orders.mapper.ItemMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private ItemService itemService;

    private StockMovementService stockMovementService;

    private OrderService orderService;

    private ItemMapper itemMapper;

    @GetMapping
    public List<ItemResponse> list() {
        return itemMapper.toDtoCollection(itemService.list());
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long itemId) {
        return itemService.findById(itemId)
                .map(item -> ResponseEntity.ok(itemMapper.toDto(item)))
                .orElseThrow(() -> new BusinessException("item not found!"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse addItem(@Valid @RequestBody ItemRequest itemRequest) {
        ItemEntity itemEntity = itemMapper.toEntity(itemRequest);
        return itemMapper.toDto(itemService.save(itemEntity));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemResponse> update(@PathVariable Long itemId, @Valid @RequestBody ItemRequest itemRequest) {
        if (!itemService.existsById(itemId)) {
            throw new BusinessException("item not found to update!");
        }

        ItemEntity itemEntity = itemMapper.toEntity(itemRequest);
        itemEntity.setId(itemId);
        itemService.save(itemEntity);

        return ResponseEntity.ok(itemMapper.toDto(itemEntity));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ItemResponse> delete(@PathVariable Long itemId) {
        if (!itemService.existsById(itemId)) {
            throw new BusinessException("item not found to delete!");
        }

        if (stockMovementService.countByItem(itemId) > 0) {
            throw new BusinessException("Item can't be deleted. There are dependent records with this item in Stock.");
        }

        if (orderService.countByItem(itemId) > 0) {
            throw new BusinessException("Item can't be deleted. There are dependent records with this item in Order.");
        }

        itemService.delete(itemId);

        return ResponseEntity.noContent().build();
    }
}
