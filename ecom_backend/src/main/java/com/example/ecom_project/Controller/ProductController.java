package com.example.ecom_project.Controller;

import com.example.ecom_project.Model.Product;
import com.example.ecom_project.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService service;

    // Get all products
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = service.getallproducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Get a single product by ID
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Product product = service.getProduct(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Add a new product
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile) {
        try {
            Product savedProduct = service.addproduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get product image by ID
    @GetMapping("/products/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id) {
        Product product = service.getProduct(id);
        if (product != null && product.getImageData() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(product.getImageType()))
                    .body(product.getImageData());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,
                                                @RequestPart Product product,
                                                @RequestPart MultipartFile imageFile) {
        Product product1 = null;
        try {
            product1 = service.updateProduct(id, product, imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(product1 != null){
            return new ResponseEntity<>("Updated",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Failed to Upadate",HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product= service.getProduct(id);
        if(product != null){
            service.deleteProduct(id);
            return new ResponseEntity<>("Product Deleted Successfully",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
        }
    }



}
