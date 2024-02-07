package javabetest.javabespring.controller;

import javabetest.javabespring.model.OrderDetail;
import javabetest.javabespring.model.OrderDetailId;
import javabetest.javabespring.repository.OrderDetailRepository;
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
@RequestMapping("/order-detail")
public class OrderDetailController {

    @Autowired
    private OrderDetailRepository orderDetailRepo;

    @GetMapping("/all")
    public ResponseEntity<List<OrderDetail>> getAll() {
        try {
            List<OrderDetail> orderDetailList = new ArrayList<>();
            orderDetailList.addAll(orderDetailRepo.findAll());

            if (orderDetailList.isEmpty()) {
                return new ResponseEntity<>(orderDetailList, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(orderDetailList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get All with pagination
    @GetMapping
    public Page<OrderDetail> getAllPaginate(@RequestParam int page, @RequestParam int size) {
        PageRequest pr = PageRequest.of(page, size);
        return (Page<OrderDetail>) orderDetailRepo.findAll(pr);
    }

    private OrderDetail queryById(OrderDetailId orderDetailId) throws Exception {
        try {
            if (orderDetailId == null) {
                return new OrderDetail();
            }

            Optional<OrderDetail> orderDetail = orderDetailRepo.findById(orderDetailId);

            if (orderDetail.isEmpty()) {
                return new OrderDetail();
            }

            return orderDetail.get();
        } catch (Exception e) {
            throw new Exception("Internal Server Error");
        }
    }

    @GetMapping("/{orderDetailId}")
    public ResponseEntity<OrderDetail> getById(@PathVariable OrderDetailId orderDetailId) {
        try {
            OrderDetail orderDetail = queryById(orderDetailId);

            if (orderDetail.isNull()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(orderDetail, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<OrderDetail> insert(@RequestBody OrderDetail orderDetail) {
        try {
            OrderDetail newOrderDetail = orderDetailRepo.save(orderDetail);

            return new ResponseEntity<>(newOrderDetail, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{orderDetailId}")
    public ResponseEntity<OrderDetail> updateById(@PathVariable OrderDetailId orderDetailId,
            @RequestBody OrderDetail newOrderDetail) {
        try {
            OrderDetail orderDetail = queryById(orderDetailId);

            if (orderDetail.isNull()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Update
            // orderDetail.setOrderNo(newOrderDetail.getOrderNo());
            // orderDetail.setItemId(newOrderDetail.getItemId());
            orderDetail.setQty(newOrderDetail.getQty());
            OrderDetail updatedItem = orderDetailRepo.save(orderDetail);

            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{orderDetailId}")
    public ResponseEntity<OrderDetail> deleteById(@PathVariable OrderDetailId orderDetailId) {
        try {
            OrderDetail orderDetail = queryById(orderDetailId);

            if (orderDetail.isNull()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            orderDetailRepo.delete(orderDetail);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
