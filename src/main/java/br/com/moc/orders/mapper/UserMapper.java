package br.com.moc.orders.mapper;

import br.com.moc.orders.api.dto.UserRequest;
import br.com.moc.orders.api.dto.UserResponse;
import br.com.moc.orders.domain.model.UserEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UserMapper {
    private ModelMapper modelMapper;

    public UserResponse toDto(UserEntity item) {
        return modelMapper.map(item, UserResponse.class);
    }

    public List<UserResponse> toDtoCollection(List<UserEntity> items) {
        return items.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UserEntity toEntity(UserRequest UserRequest) {
        return modelMapper.map(UserRequest, UserEntity.class);
    }
}
