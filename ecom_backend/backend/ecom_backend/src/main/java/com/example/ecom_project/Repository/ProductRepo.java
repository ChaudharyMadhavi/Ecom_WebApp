package com.example.ecom_project.Repository;

import com.example.ecom_project.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
}
