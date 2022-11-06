package br.com.moc.orders.mapper;

import br.com.moc.orders.api.dto.ItemRequest;
import br.com.moc.orders.api.dto.ItemResponse;
import br.com.moc.orders.domain.model.ItemEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ItemMapper {
    private ModelMapper modelMapper;

    public ItemResponse toDto(ItemEntity item) {
        return modelMapper.map(item, ItemResponse.class);
    }

    public List<ItemResponse> toDtoCollection(List<ItemEntity> items) {
        return items.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ItemEntity toEntity(ItemRequest itemRequest) {
        return modelMapper.map(itemRequest, ItemEntity.class);
    }
}
