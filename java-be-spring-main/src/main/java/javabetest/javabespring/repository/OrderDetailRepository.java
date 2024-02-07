package javabetest.javabespring.repository;

import javabetest.javabespring.model.OrderDetail;
import javabetest.javabespring.model.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

//@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
}
