package br.com.moc.orders.api.controller;

import br.com.moc.orders.api.dto.UserRequest;
import br.com.moc.orders.api.dto.UserResponse;
import br.com.moc.orders.domain.exception.BusinessException;
import br.com.moc.orders.domain.model.UserEntity;
import br.com.moc.orders.domain.service.OrderService;
import br.com.moc.orders.domain.service.UserService;
import br.com.moc.orders.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    private OrderService orderService;

    private UserMapper userMapper;

    @GetMapping
    public List<UserResponse> list() {
        return userMapper.toDtoCollection(userService.list());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        return userService.findById(userId)
                .map(user -> ResponseEntity.ok(userMapper.toDto(user)))
                .orElseThrow(() -> new BusinessException("User not found!"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse addItem(@Valid @RequestBody UserRequest userRequest) {
        UserEntity userEntity = userMapper.toEntity(userRequest);
        return userMapper.toDto(userService.save(userEntity));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> update(@PathVariable Long userId, @Valid @RequestBody UserRequest userRequest) {
        if (!userService.existsById(userId)) {
            throw new BusinessException("User not found to update!");
        }

        UserEntity userEntity = userMapper.toEntity(userRequest);
        userEntity.setId(userId);
        userService.save(userEntity);

        return ResponseEntity.ok(userMapper.toDto(userEntity));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponse> delete(@PathVariable Long userId) {
        if (!userService.existsById(userId)) {
            throw new BusinessException("User not found to delete!");
        }

        if (orderService.countByUser(userId) > 0) {
            throw new BusinessException("User can't be deleted. There are dependent records with this user in Order.");
        }

        userService.delete(userId);

        return ResponseEntity.noContent().build();
    }
}
