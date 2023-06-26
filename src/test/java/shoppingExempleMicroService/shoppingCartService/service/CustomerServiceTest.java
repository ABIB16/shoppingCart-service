package shoppingExempleMicroService.shoppingCartService.service;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import shoppingExempleMicroService.shoppingCartService.dto.Product;
import shoppingExempleMicroService.shoppingCartService.model.Customer;
import shoppingExempleMicroService.shoppingCartService.model.ProductItem;
import shoppingExempleMicroService.shoppingCartService.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerServiceTest {

    @InjectMocks
    ShoppingCartService shoppingCartService;

    @Mock
    private WebClient webClient;


    @Test
    public void testAppelServiceProduct(){
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:9090").build();
        Product product = webClient.get()
                .uri("/api/products/{id}", 1L)
                .retrieve()
                .bodyToMono(Product.class)
                .blockOptional()
                .orElse(null);

        Integer quantityExist =5;
        Integer quantityToAdd = 3;
        double totalPrice = 15000;
        ProductItem item = new ProductItem(product.getId(), quantityExist,totalPrice);

        // vérifier l'existance du produit
        assertEquals(product.getId(), item.getProductId());




    }



}
