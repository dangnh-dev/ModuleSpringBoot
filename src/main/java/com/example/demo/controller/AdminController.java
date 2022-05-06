package com.example.demo.controller;

import com.example.demo.config.ResourcesConfig;
import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.model.CategoryModel;
import com.example.demo.model.ProductModel;
import com.example.demo.service.ICategoryService;
import com.example.demo.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RolesAllowed(value = "ROLE_ADMIN")
public class AdminController {
    @Autowired
    ICategoryService iCategoryService;
    @Autowired
    IProductService iProductService;
    @Autowired
    ResourcesConfig resourcesConfig;

    public List<CategoryDTO> getCategoryList(){
        List<CategoryModel> categoryModelList = iCategoryService.getAllCategory();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categoryModelList.forEach(c -> {
            CategoryDTO categoryDTO = new CategoryDTO().toDTO(c);
            categoryDTOList.add(categoryDTO);
        });
        return categoryDTOList;
    }

    public List<ProductDTO> getProductList(){
        List<ProductModel> productModelList = iProductService.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        productModelList.forEach(p -> {
            ProductDTO productDTO = new ProductDTO().toDTO(p);
            productDTOList.add(productDTO);
        });
        return productDTOList;
    }

    @GetMapping
    public String adminPage(){
        return "admin/admin-view";
    }

    @GetMapping("/bar-chart")
    public ResponseEntity<?> adminBarChartData(){
        return ResponseEntity.ok().body(getProductList());
    }

    @GetMapping("/tables")
    public String chartsPage(){
        return "admin/tables";
    }

//    ############
//    Category
//    ############

    @GetMapping("/category")
    public String categoryPage(Model model){
        model.addAttribute("categoryList", getCategoryList());
        return "admin/category/category-detail";
    }

    @GetMapping("/category/add")
    public String addCategoryPage(Model model){
        model.addAttribute("categoryList", getCategoryList());
        model.addAttribute("category", new CategoryDTO());
        return "admin/category/add-category";
    }

    @PostMapping("/category/add")
    public String addNewCategory(CategoryDTO categoryDTO){
        CategoryModel categoryModel = new CategoryModel().fromDTOToModel(categoryDTO);
        boolean result = iCategoryService.save(categoryModel);
        return "redirect:/admin/category/add?addResult="+result;
    }

    @GetMapping("/category/edit")
    public String editCategoryPage(@RequestParam("id")Long id, Model model){
        CategoryModel categoryModel = iCategoryService.findById(id);
        CategoryDTO categoryDTO = new CategoryDTO().toDTO(categoryModel);
        model.addAttribute("category", categoryDTO);
        model.addAttribute("categoryList", getCategoryList());
        return "admin/category/edit-category";
    }

    @PostMapping("/category/edit")
    public String editCategory(CategoryDTO categoryDTO){
        CategoryModel categoryModel = new CategoryModel().fromDTOToModel(categoryDTO);
        boolean result = iCategoryService.edit(categoryModel);
        return "redirect:/admin/category?editResult="+result;
    }

    @GetMapping("/category/delete")
    public String deleteCategory(@RequestParam("id") Long id){
        boolean result = iCategoryService.delete(id);
        return "redirect:/admin/category?deleteResult="+result;
    }

//    ############
//    Product
//    ############

    @GetMapping("/product")
    public String productPage(Model model){
        model.addAttribute("productList", getProductList());
        model.addAttribute("categoryList", getCategoryList());
        return "admin/product/product-detail";
    }

    @GetMapping("/product/add")
    public String addProductPage(Model model){
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("categoryList", getCategoryList());
        return "admin/product/add-product";
    }

    @PostMapping("/product/add")
    public String addNewProduct(ProductDTO productDTO, @RequestParam("productImage")MultipartFile file){
        String imageName = resourcesConfig.uploadImage(file);
        productDTO.setImage(imageName);
        ProductModel productModel = new ProductModel().fromDTOToModel(productDTO);
        boolean result = iProductService.save(productModel);
        return "redirect:/admin/product/add?addResult="+result;
    }

    @GetMapping("/product/edit")
    public String editProductPage(@RequestParam("id")Long id, Model model){
        ProductModel productModel = iProductService.findById(id);
        ProductDTO productDTO = new ProductDTO().toDTO(productModel);
        model.addAttribute("product", productDTO);
        model.addAttribute("categoryList", getCategoryList());
        return "admin/product/edit-product";
    }

    @PostMapping("/product/edit")
    public String editProduct(ProductDTO productDTO, @RequestParam("productImage") MultipartFile file){
        if (!file.isEmpty()){
            String imageName = resourcesConfig.uploadImage(file);
            productDTO.setImage(imageName);
        }
        ProductModel productModel = new ProductModel().fromDTOToModel(productDTO);
        boolean result = iProductService.edit(productModel);
        return "redirect:/admin/product?editResult="+result;
    }

    @GetMapping("/product/delete")
    public String deleteProduct(@RequestParam("id") Long id){
        boolean result = iProductService.delete(id);
        return "redirect:/admin/product?deleteResult="+result;
    }
}
