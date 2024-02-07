package javabetest.javabespring.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(OrderDetailId.class)
public class OrderDetail extends OrderDetailId {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "order_detail_id")
//    private Integer orderDetailId;

    @Id
    private String orderNo;

    @Id
    private Integer itemId;

    private Integer qty;

    public boolean isNull() {
        return this.orderNo == null && this.itemId == null && this.qty == null;
    }
}
