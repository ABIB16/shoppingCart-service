package shoppingExempleMicroService.shoppingCartService.config;


import lombok.AllArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import shoppingExempleMicroService.shoppingCartService.dto.Product;
import shoppingExempleMicroService.shoppingCartService.model.Customer;
import shoppingExempleMicroService.shoppingCartService.model.ProductItem;
import shoppingExempleMicroService.shoppingCartService.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class StartData implements CommandLineRunner {

    @Autowired
    private ShoppingCartService customerService;
    private final WebClient webClient;

    // Appel à l'API du microservice product-service pour récupérer un produit
    public Product findProduct(Long id){
        return webClient.get()
                .uri("/api/products/{id}", id)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    @Override
    public void run(String... args) throws Exception {
        if (customerService.findAll().isEmpty()) {

            Customer customer1 = new Customer();
            customer1.setFirstName("ABIB");
            customer1.setLastName("Billel");
            customer1.setAddress("ALGER");

            // add first product item to customer
            ProductItem item1 = new ProductItem();
            item1.setCustomer(customer1);
            item1.setProductId(findProduct(1L).getId());
            item1.setQuantity(2);
            item1.setTotalPrice(findProduct(1L).getPrice() * item1.getQuantity());

            // add second product item to customer
            ProductItem item2 = new ProductItem();
            item2.setCustomer(customer1);
            item2.setProductId(findProduct(2L).getId());
            item2.setQuantity(3);
            item2.setTotalPrice(findProduct(2L).getPrice() * item2.getQuantity());

            customer1.getProductItemList().addAll(Arrays.asList(item1,item2));
            customerService.saveCustomer(customer1);
        }
    }

}
