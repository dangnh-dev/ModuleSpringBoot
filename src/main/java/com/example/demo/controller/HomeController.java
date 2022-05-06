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
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    ICategoryService iCategoryService;
    @Autowired
    IProductService iProductService;
    @Autowired
    IRateService iRateService;

    public List<CategoryDTO> getCategoryList(){
        List<CategoryModel> categoryModelList = iCategoryService.getAllCategory();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categoryModelList.forEach(c -> {
            CategoryDTO categoryDTO = new CategoryDTO().toDTO(c);
            categoryDTOList.add(categoryDTO);
        });
        return categoryDTOList;
    }

    public Page<ProductDTO> getProductList(Pageable pageable){
        Page<ProductModel> productModelPage = iProductService.getAll(pageable);
        return productModelPage.map(productModel -> {
            List<RatingModel> ratingModelList = iRateService.findAllByProductId(productModel.getId());
            if (!ratingModelList.isEmpty()){
                int total = ratingModelList.size();
                List<Float> rates = ratingModelList.stream().map(RatingModel::getStar).collect(Collectors.toList());
                float avgRate = 0;
                for (Float star: rates) {
                    avgRate += star;
                }
                avgRate /= total;
                productModel.setAvgRate(avgRate);
                iProductService.edit(productModel);
            }
            return new ProductDTO().toDTO(productModel);
        });
    }

    public Page<ProductDTO> getProductListByCategory(Long id, Pageable pageable){
        Page<ProductModel> productModelPage = iProductService.findByCategoryId(id, pageable);
        return productModelPage.map(productModel -> {
            List<RatingModel> ratingModelList = iRateService.findAllByProductId(productModel.getId());
            if (!ratingModelList.isEmpty()){
                int total = ratingModelList.size();
                List<Float> rates = ratingModelList.stream().map(RatingModel::getStar).collect(Collectors.toList());
                float avgRate = 0;
                for (Float star: rates) {
                    avgRate += star;
                }
                avgRate /= total;
                productModel.setAvgRate(avgRate);
                iProductService.edit(productModel);
            }
            return new ProductDTO().toDTO(productModel);
        });
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

    @GetMapping
    public String homePage(Model model){
        Pageable pageable = PageRequest.of(0, 8, Sort.by(Sort.Direction.ASC, "id"));
        Page<ProductDTO> productDTOPage = getProductList(pageable);
        model.addAttribute("productList", productDTOPage);
        model.addAttribute("pageNumber", productDTOPage.getTotalPages());
        model.addAttribute("categoryList", getNavbar());
        return "index";
    }

    @GetMapping("/paging")
    public ResponseEntity<?> pagination(Model model, @RequestParam("page")int page, @RequestParam("size")int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<ProductDTO> productEntityPage = getProductList(pageable);
        return ResponseEntity.ok().body(productEntityPage);
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
        model.addAttribute("rateDto", new RatingDTO());
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
        return "redirect:/home/detail/"+id;
    }

    @GetMapping("/category/{id}")
    public String getProductByCategory(@PathVariable Long id, Model model){
        Pageable pageable = PageRequest.of(0, 8, Sort.by(Sort.Direction.ASC, "id"));
        Page<ProductDTO> productDTOPage = getProductListByCategory(id, pageable);
        model.addAttribute("productList", productDTOPage);
        model.addAttribute("pageNumber", productDTOPage.getTotalPages());
        model.addAttribute("categoryList", getNavbar());
        return "index";
    }
}
