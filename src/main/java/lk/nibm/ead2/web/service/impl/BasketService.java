package lk.nibm.ead2.web.service.impl;

import lk.nibm.ead2.web.model.Basket;
import lk.nibm.ead2.web.model.BasketItem;
import lk.nibm.ead2.web.model.BasketItemDTO;
import lk.nibm.ead2.web.repository.BasketRepository;
import lk.nibm.ead2.web.service.IBasketService;
import lk.nibm.ead2.web.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BasketService implements IBasketService {

    @Autowired
    BasketRepository basketRepository;
    @Autowired
    IProductService productservice;

    @Override
    public Basket save(Basket basket) {
        Basket dto = null;
        if (basket.getId() == null) {
            dto = new Basket();
        } else {
            Optional<Basket> optionalBasket = basketRepository.findById(basket.getId());
            if (optionalBasket.isPresent()) {
                dto = optionalBasket.get();
            }
        }
        BeanUtils.copyProperties(basket, dto);
        dto = basketRepository.save(dto);
        basket.setId(dto.getId());
        return basket;
    }

    @Override
    public List<BasketItemDTO> saveAll(List<BasketItemDTO> basketItemDTOS) {

        List<BasketItem> cartItemList = new ArrayList<>();
        Basket basket = Basket.builder()
                .basketItems(cartItemList)
                .build();

        basketItemDTOS.forEach(cartItem -> {
            if (cartItem.getProduct() != null) {
                BasketItem item = BasketItem.builder()
                        .product(productservice.find(cartItem.getProduct()))
                        .quantity(cartItem.getQuantity())
                        .basket(basket)
                        .build();
                cartItemList.add(item);
            }
        });


        if (!cartItemList.isEmpty()) {
            basketRepository.save(basket);
        }
        return basketItemDTOS;
    }
}
