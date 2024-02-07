package javabetest.javabespring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @Column(name = "order_no")
    private String orderId;

//    @Column(name = "order_detail_id")
//    private Integer orderDetailId;
//
    @OneToMany
    @JoinColumn(name = "order_no", referencedColumnName = "order_no")
    private List<OrderDetail> orderDetails;

    public boolean isNull() {
        return this.orderId == null;
    }
}
