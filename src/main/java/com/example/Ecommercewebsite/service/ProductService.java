package com.example.Ecommercewebsite.service;


import com.example.Ecommercewebsite.model.Product;
import com.example.Ecommercewebsite.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getAllProducts(){
           return  repository.findAll();
    }

    public Product getproductbyid(int id){

         Optional<Product> prod=  repository.findById(id);

         return prod.orElse(null);

    }

    public Product addproduct(Product product, MultipartFile imageFile) throws IOException {

        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        return repository.save(product);
    }

    public Product updateproduct(int id, Product prod, MultipartFile imageFile) throws IOException {


        if (prod == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        prod.setImageData(imageFile.getBytes());
        prod.setImageName(imageFile.getOriginalFilename());
        prod.setImageType(imageFile.getContentType());
        return repository.save(prod);

    }

    public void deleteproduct(int id) {

        repository.deleteById(id);
    }

    public List<Product> searchproduct(String keyword) {

        return repository.searchproduct(keyword);
    }
}
