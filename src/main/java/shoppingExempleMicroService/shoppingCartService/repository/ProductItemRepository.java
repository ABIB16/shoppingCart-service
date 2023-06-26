package shoppingExempleMicroService.shoppingCartService.repository;

import shoppingExempleMicroService.shoppingCartService.model.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository  extends JpaRepository<ProductItem,Long> {
}
