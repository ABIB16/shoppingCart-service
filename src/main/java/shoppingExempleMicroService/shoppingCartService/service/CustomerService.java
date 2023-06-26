package shoppingExempleMicroService.shoppingCartService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shoppingExempleMicroService.shoppingCartService.model.Customer;
import shoppingExempleMicroService.shoppingCartService.repository.CustomerRepository;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Optional <Customer>getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

}
