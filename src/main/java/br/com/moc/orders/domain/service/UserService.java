package br.com.moc.orders.domain.service;

import br.com.moc.orders.domain.model.UserEntity;
import br.com.moc.orders.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public Optional<UserEntity> findById(Long id) {
        return this.userRepository.findById(id);
    }

    public List<UserEntity> list() {
        return this.userRepository.findAll();
    }

    public UserEntity save(UserEntity itemEntity) {
        return userRepository.saveAndFlush(itemEntity);
    }

    public boolean existsById(Long id) {
        return this.userRepository.existsById(id);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
