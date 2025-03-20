package com.example.ecom_project.Service;

import com.example.ecom_project.Model.Product;
import com.example.ecom_project.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> getallproducts(){
       return productRepo.findAll();
    }

    public Product getProduct(int id) {
        return productRepo.findById(id).orElse(null);
    }

    public Product addproduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return productRepo.save(product);

    }
}
