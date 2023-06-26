package shoppingExempleMicroService.shoppingCartService.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import shoppingExempleMicroService.shoppingCartService.dto.Product;
import shoppingExempleMicroService.shoppingCartService.exceptions.EntityNotFoundExecption;
import shoppingExempleMicroService.shoppingCartService.model.Customer;
import shoppingExempleMicroService.shoppingCartService.model.ProductItem;
import shoppingExempleMicroService.shoppingCartService.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private  CustomerService customerService ;

    private final WebClient webClient;

    public ShoppingCartService(WebClient webClient) {
        this.webClient = webClient;
    }


    Logger log = LoggerFactory.getLogger(ShoppingCartService.class);

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void addItemToShoppingCart(Long customerId, Long prdId, Integer quantity) {
        // Appel à l'API du microservice product-service pour récupérer un produit
        Product product = webClient.get()
                .uri("/api/products/{id}", prdId)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response -> Mono.error(new EntityNotFoundExecption("Produit non trouvé avec l'ID : " + prdId)))
                .bodyToMono(Product.class).block();

        Optional<Customer> customer = customerService.getCustomerById(customerId);

         if (customer.isPresent()) {

            // si le produit existe encore dans le stock
           if (product.getQuantityInStock() > quantity) {

                Optional<ProductItem> itemExist = customer.get().getProductItemList().stream().filter(productItem -> productItem.getProductId().equals(product.getId())).findFirst();

                // si le produit existe deja dans la liste
                if (itemExist.isPresent()) {
                    ProductItem p = itemExist.get();
                    itemExist.get().setQuantity(p.getQuantity() + quantity);
                    itemExist.get().setTotalPrice(p.getQuantity() * product.getPrice());
                } else {
                    // ajouter le nouveau produit dans la liste
                    ProductItem newItem = new ProductItem();
                    newItem.setCustomer(customer.get());
                    newItem.setQuantity(quantity);
                    newItem.setTotalPrice(quantity * product.getPrice());
                    newItem.setProductId(product.getId());
                    customer.get().getProductItemList().add(newItem);
                }

                // mettre à jour la quantité restante
                product.setQuantityInStock(product.getQuantityInStock() - quantity);

               // Appel à l'API du microservice product-service pour modifier la quantite restante du produit
               webClient.put()
                       .uri("api/products/{productId}", prdId)
                       .bodyValue(product)
                       .retrieve()
                       .toBodilessEntity()
                       .block();

               // ajouter le nouveau produit dans le panier du client concerné
                saveCustomer(customer.get());
            } else {
                log.error("Rupture de stock");
            }
        } else {
            throw new EntityNotFoundExecption("customer is null");
        }
    }


}
