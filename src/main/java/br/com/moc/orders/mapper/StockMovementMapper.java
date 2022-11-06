package br.com.moc.orders.mapper;

import br.com.moc.orders.api.dto.StockMovementRequest;
import br.com.moc.orders.api.dto.StockMovementResponse;
import br.com.moc.orders.domain.model.StockMovementEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockMovementMapper {
    private ModelMapper modelMapper;

    public StockMovementMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        TypeMap<StockMovementRequest, StockMovementEntity> propertyMapperRequest = this.modelMapper.createTypeMap(StockMovementRequest.class, StockMovementEntity.class);
        propertyMapperRequest.addMappings(
                mapper -> mapper.map(src -> src.getItemId(), (dest, v) -> dest.getItem().setId((Long) v))
        );

        TypeMap<StockMovementEntity, StockMovementResponse> propertyMapperResponse = this.modelMapper.createTypeMap(StockMovementEntity.class, StockMovementResponse.class);
        propertyMapperResponse.addMappings(
                mapper -> mapper.map(src -> src.getItem().getId(), (dest, v) -> dest.setItemId((Long) v))
        );
    }

    public StockMovementResponse toDto(StockMovementEntity stockMovementEntity) {
        return modelMapper.map(stockMovementEntity, StockMovementResponse.class);
    }

    public List<StockMovementResponse> toDtoCollection(List<StockMovementEntity> stockMovementEntities) {
        return stockMovementEntities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public StockMovementEntity toEntity(StockMovementRequest stockMovementRequest) {
        return modelMapper.map(stockMovementRequest, StockMovementEntity.class);
    }
}
