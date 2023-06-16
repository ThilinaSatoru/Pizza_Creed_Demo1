package lk.nibm.ead2.web.controller;

import lk.nibm.ead2.web.model.BasketItemDTO;
import lk.nibm.ead2.web.service.IBasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BasketRestController {

    @Autowired
    IBasketService basketService;

    @PostMapping("/basket")
    public List<BasketItemDTO> createBasket(@RequestBody List<BasketItemDTO> cartItems) {
        return basketService.saveAll(cartItems);
    }
}