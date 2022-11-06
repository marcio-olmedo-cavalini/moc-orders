package br.com.moc.orders.mapper;

import br.com.moc.orders.api.dto.OrderRequest;
import br.com.moc.orders.api.dto.OrderResponse;
import br.com.moc.orders.domain.model.OrderEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        TypeMap<OrderRequest, OrderEntity> propertyMapperRequest = this.modelMapper.createTypeMap(OrderRequest.class, OrderEntity.class);
        propertyMapperRequest.addMappings(
                mapper -> mapper.map(src -> src.getItemId(), (dest, v) -> dest.getItem().setId((Long) v))
        );

        propertyMapperRequest.addMappings(
                mapper -> mapper.map(src -> src.getUserId(), (dest, v) -> dest.getUser().setId((Long) v))
        );

        TypeMap<OrderEntity, OrderResponse> propertyMapperResponse = this.modelMapper.createTypeMap(OrderEntity.class, OrderResponse.class);
        propertyMapperResponse.addMappings(
                mapper -> mapper.map(src -> src.getItem().getId(), (dest, v) -> dest.setItemId((Long) v))
        );

        propertyMapperResponse.addMappings(
                mapper -> mapper.map(src -> src.getUser().getId(), (dest, v) -> dest.setUserId((Long) v))
        );
    }

    public OrderResponse toDto(OrderEntity order) {
        return modelMapper.map(order, OrderResponse.class);
    }

    public List<OrderResponse> toDtoCollection(List<OrderEntity> orders) {
        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public OrderEntity toEntity(OrderRequest orderRequest) {

        return modelMapper.map(orderRequest, OrderEntity.class);
    }
}
