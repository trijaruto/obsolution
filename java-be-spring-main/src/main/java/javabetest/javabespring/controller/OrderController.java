package javabetest.javabespring.controller;

//import javabetest.javabespring.model.Item;
import javabetest.javabespring.model.Order;
import javabetest.javabespring.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepo;

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAll() {
        try {
            List<Order> orderList = new ArrayList<>();
            orderList.addAll(orderRepo.findAll());

            if (orderList.isEmpty()) {
                return new ResponseEntity<>(orderList, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(orderList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get All with pagination
    @GetMapping
    public Page<Order> getAllPaginate(@RequestParam int page, @RequestParam int size) {
        PageRequest pr = PageRequest.of(page, size);
        return (Page<Order>) orderRepo.findAll(pr);
    }

    private Order queryById(String orderNo) throws Exception {
        try {
            if (orderNo == null || orderNo.isEmpty()) {
                return new Order();
            }

            Optional<Order> order = orderRepo.findById(orderNo);

            if (order.isEmpty()) {
                return new Order();
            }

            return order.get();
        } catch (Exception e) {
            throw new Exception("Internal Server Error");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable String orderNo) {
        try {
            Order order = queryById(orderNo);

            if (order.isNull()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Order> insert(@RequestBody Order order) {
        try {
            Order newItem = orderRepo.save(order);

            return new ResponseEntity<>(newItem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Order> updateById(@PathVariable String orderNo, @RequestBody Order newOrder) {
        try {
            Order order = queryById(orderNo);

            if (order.isNull()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Update
            order.setOrderId(newOrder.getOrderId());
            Order updatedItem = orderRepo.save(order);

            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Order> deleteById(@PathVariable String orderNo) {
        try {
            Order order = queryById(orderNo);

            if (order.isNull()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            orderRepo.delete(order);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
