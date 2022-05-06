package com.example.demo.controller;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.NavbarDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.RatingDTO;
import com.example.demo.model.CategoryModel;
import com.example.demo.model.ProductModel;
import com.example.demo.model.RatingModel;
import com.example.demo.service.ICategoryService;
import com.example.demo.service.IProductService;
import com.example.demo.service.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    ICategoryService iCategoryService;
    @Autowired
    IProductService iProductService;
    @Autowired
    IRateService iRateService;

    public List<CategoryDTO> getCategoryList(){
        List<CategoryModel> categoryModelList = iCategoryService.getAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categoryModelList.forEach(c -> {
            CategoryDTO categoryDTO = new CategoryDTO().toDTO(c);
            categoryDTOList.add(categoryDTO);
        });
        return categoryDTOList;
    }

    public List<ProductDTO> getProductList(){
        List<ProductModel> productModelList = iProductService.getAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        productModelList.forEach(p -> {
            ProductDTO productDTO = new ProductDTO().toDTO(p);
            productDTOList.add(productDTO);
        });
        return productDTOList;
    }

    public List<NavbarDTO> getNavbar(){
        List<CategoryDTO> categoryDTOList = getCategoryList();
        List<NavbarDTO> navbarDTOList = new ArrayList<>();
        List<Long> childId = new ArrayList<>();
        for (CategoryDTO c: categoryDTOList){
            List<CategoryDTO> childDTOList = categoryDTOList.stream()
                    .filter(item -> item.getCategoryParentId() == c.getId())
                    .collect(Collectors.toList());

            childDTOList.forEach(item -> {
                childId.add(item.getId());
            });

            NavbarDTO navbarDTO = NavbarDTO.builder()
                    .id(c.getId())
                    .name(c.getName())
                    .categoryDTOList(childDTOList)
                    .build();
            navbarDTOList.add(navbarDTO);
        }

        childId.forEach(id -> {
            navbarDTOList.removeIf(n -> n.getId() == id);
        });

        return navbarDTOList;
    }

    @GetMapping(value = {"/", "/home"})
    public String homePage(Model model){
        model.addAttribute("productList", getProductList());
        model.addAttribute("categoryList", getNavbar());
        return "index";
    }

    @GetMapping("/detail/{id}")
    public String productDetail(Model model, @PathVariable Long id){
        ProductModel productModel = iProductService.findById(id);
        ProductDTO productDTO = new ProductDTO().toDTO(productModel);
        List<RatingModel> ratingModelList = iRateService.findAllByProductId(id);
        List<RatingDTO> ratingDTOList = new ArrayList<>();
        ratingModelList.forEach(r -> {
            RatingDTO ratingDTO = new RatingDTO().toDTO(r);
            ratingDTOList.add(ratingDTO);
        });
        model.addAttribute("product", productDTO);
        model.addAttribute("rating", new RatingDTO());
        model.addAttribute("categoryList", getNavbar());
        model.addAttribute("ratingList", ratingDTOList);
        return "item-detail";
    }

    @PostMapping("/detail/{id}")
    public String newComment(RatingDTO ratingDTO, @PathVariable Long id){
        ratingDTO.setDate(LocalDateTime.now());
        ratingDTO.setProductEntityId(id);
        RatingModel ratingModel = new RatingModel().fromDTOToModel(ratingDTO);
        iRateService.save(ratingModel);
        return "redirect:/detail/"+id;
    }
}
