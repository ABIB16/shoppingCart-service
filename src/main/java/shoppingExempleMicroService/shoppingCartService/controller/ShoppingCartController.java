package shoppingExempleMicroService.shoppingCartService.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shoppingExempleMicroService.shoppingCartService.model.Customer;
import shoppingExempleMicroService.shoppingCartService.model.ProductItem;
import shoppingExempleMicroService.shoppingCartService.service.ShoppingCartService;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService ;

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return shoppingCartService.saveCustomer(customer);
    }

    @PostMapping("/{customerId}/{productId}")
    public void addItemToShoppingCart(@PathVariable Long customerId, @PathVariable Long productId, @RequestBody ProductItem item, @RequestParam Integer quantity) {
        shoppingCartService.addItemToShoppingCart(customerId, productId,quantity);
    }
}
