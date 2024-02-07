package javabetest.javabespring.controller;

import javabetest.javabespring.model.Item;
import javabetest.javabespring.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemRepository itemRepo;


    @GetMapping("/all")
    public ResponseEntity<List<Item>> getAll() {
        try {
            List<Item> itemList = new ArrayList<>();
            itemList.addAll(itemRepo.findAll());

            if (itemList.isEmpty()) {
                return new ResponseEntity<>(itemList, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(itemList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get All with pagination
    @GetMapping
    public Page<Item> getAllPaginate(@RequestParam int page, @RequestParam int size) {
        PageRequest pr = PageRequest.of(page, size);
        return (Page<Item>) itemRepo.findAll(pr);
    }

    private Item queryById(Integer id) throws Exception {
        try {
            if (id == null) {
                return new Item();
            }

            Optional<Item> item = itemRepo.findById(id);

            if (item.isEmpty()) {
                return new Item();
            }

            return item.get();
        } catch (Exception e) {
            throw new Exception("Internal Server Error");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getById(@PathVariable Integer id) {
        try {
            Item item = queryById(id);

            if (item.isNull()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/insert")
    public ResponseEntity<Item> insert(@RequestBody Item item) {
        try {
            Item newItem = itemRepo.save(item);

            return new ResponseEntity<>(newItem, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Item> updateById(@PathVariable Integer id, @RequestBody Item newItem) {
        try {
            Item item = queryById(id);

            if (item.isNull()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Update
            item.setName(newItem.getName());
            item.setPrice(newItem.getPrice());
            Item updatedItem = itemRepo.save(item);

            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Item> deleteById(@PathVariable Integer id) {
        try {
            Item item = queryById(id);

            if (item.isNull()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            itemRepo.delete(item);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
