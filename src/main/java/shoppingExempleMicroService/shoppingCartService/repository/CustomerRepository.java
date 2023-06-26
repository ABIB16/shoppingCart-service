package shoppingExempleMicroService.shoppingCartService.repository;

import shoppingExempleMicroService.shoppingCartService.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
