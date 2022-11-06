package br.com.moc.orders.domain.service;

import br.com.moc.orders.domain.model.ItemEntity;
import br.com.moc.orders.domain.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemService {
    private ItemRepository itemRepository;

    public Optional<ItemEntity> findById(Long id) {
        return this.itemRepository.findById(id);
    }

    public List<ItemEntity> list() {
        return this.itemRepository.findAll();
    }

    public ItemEntity save(ItemEntity itemEntity) {
        return itemRepository.saveAndFlush(itemEntity);
    }

    public boolean existsById(Long id) {
        return this.itemRepository.existsById(id);
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
