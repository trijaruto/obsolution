package javabetest.javabespring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="items")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer price;

    @OneToMany
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private List<OrderDetail> orderDetailsItem;

    public boolean isNull() {
        return this.id == null && this.name == null && this.price == null;
    }
}
