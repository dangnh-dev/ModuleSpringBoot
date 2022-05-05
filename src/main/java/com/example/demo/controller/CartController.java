package com.example.demo.controller;

import com.example.demo.dto.CartProduct;
import com.example.demo.dto.ProductDTO;
import com.example.demo.model.ProductModel;
import com.example.demo.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    IProductService iProductService;

    @GetMapping
    public String showCart(Model model, @SessionAttribute("cart")Map<Long, CartProduct> cart){
        int totalProduct = cart.size();
        AtomicReference<Double> totalPrice = new AtomicReference<>((double) 0);
        cart.forEach((k, v) -> {
            totalPrice.updateAndGet(price -> price + v.getProduct().getPrice());
        });

        model.addAttribute("totalProduct", totalProduct);
        model.addAttribute("totalPrice", totalPrice);
        return "cart-view";
    }

    @GetMapping("/add")
    public ResponseEntity<?> addCart(@RequestParam("id")Long id, HttpSession session, @SessionAttribute("cart")Map<Long, CartProduct> cart){
        CartProduct cartProduct = cart.get(id);
        if (cartProduct != null){
            cartProduct.setQuantity(cartProduct.getQuantity() + 1);
            cart.put(id, cartProduct);
        } else {
            ProductModel productModel = iProductService.findById(id);
            ProductDTO productDTO = new ProductDTO().toDTO(productModel);
            productDTO.setPrice(productDTO.getPrice() - (productDTO.getPrice() * productDTO.getSale() / 100));
            cart.put(id, new CartProduct(1, productDTO));
        }
        session.setAttribute("cart", cart);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/delete/{id}")
    public String deleteProductInCart(HttpSession session, @PathVariable Long id, @SessionAttribute("cart") Map<Long, CartProduct> cart){
        cart.remove(id);
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }
}
