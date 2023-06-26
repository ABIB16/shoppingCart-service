package shoppingExempleMicroService.shoppingCartService.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product_item")
@Getter
@Setter
@NoArgsConstructor
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ManyToOne( fetch = FetchType.LAZY)
    private Customer customer;
    private Integer quantity;
    private double totalPrice;

    public ProductItem(Long productId, Integer quantity, double totalPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
