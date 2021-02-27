package mk.ukim.finki.wp.magacin.service.impl;

import mk.ukim.finki.wp.magacin.models.EachItem;
import mk.ukim.finki.wp.magacin.models.Item;
import mk.ukim.finki.wp.magacin.models.Warehouse;
import mk.ukim.finki.wp.magacin.repository.EachItemRepository;
import mk.ukim.finki.wp.magacin.service.EachItemService;
import mk.ukim.finki.wp.magacin.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EachItemServiceImpl implements EachItemService {
    private final EachItemRepository eachItemRepository;
    private final ItemService itemService;

    public EachItemServiceImpl(EachItemRepository eachItemRepository, ItemService itemService) {
        this.eachItemRepository = eachItemRepository;
        this.itemService = itemService;
    }

    public Optional<EachItem> findById(Long id){
        return this.eachItemRepository.findById(id);
    }

    @Override
    public void addItems(Integer quantity, Warehouse warehouse, Item item) {
        Optional<EachItem> eachItem = this.eachItemRepository.findByItemAndWarehouse(item,warehouse);
        this.itemService.toggleAvailability(item.getId());
        if(eachItem.isPresent()){
            EachItem em = eachItem.get();
            em.setQuantity(em.getQuantity()+quantity);
            this.eachItemRepository.save(em);
        }
        else
            this.eachItemRepository.save(new EachItem(item, warehouse, quantity));
    }

    @Override
    public boolean lowerQuantity(Long id) {
        Optional<EachItem> eachItem = this.eachItemRepository.findById(id);
        if(!eachItem.isPresent()){
            return false;
        }
        EachItem item = eachItem.get();
        int quantity = item.getQuantity();
        quantity--;
        item.setQuantity(quantity);
        this.eachItemRepository.save(item);
        if(quantity==0){
            this.itemService.toggleAvailability(item.getItem().getId());
            this.eachItemRepository.deleteById(item.getId());
        }
        return true;
    }
}
