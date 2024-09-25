package com.example.Ecommercewebsite.controller;


import com.example.Ecommercewebsite.model.Product;
import com.example.Ecommercewebsite.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api") //make sure the api is the base
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService service;

    @RequestMapping("/")
    public String Greet(){
        return "Hellow World";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllproducts(){
        return  ResponseEntity.status(200).body( service.getAllProducts());
    }

    @GetMapping("/product/{id}")
    public Product getproductbyid(@PathVariable int id){

        return service.getproductbyid(id);
    }


    @PostMapping("/product")
    public ResponseEntity<?> addproduct(@RequestPart Product product , @RequestPart MultipartFile imageFile){

        try {
            Product product1 = service.addproduct(product, imageFile); // now service please take care of storeing stuff
          return new ResponseEntity<>(product1, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){

        Product prod = service.getproductbyid(productId);

        byte[] imageFile = prod.getImageData();

        return ResponseEntity.ok().contentType(MediaType.valueOf(prod.getImageType())).body(imageFile);


    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateproduct(@PathVariable int id, @RequestPart Product product,@RequestPart MultipartFile imageFile){

        Product prod1=null;
        try {
            System.out.println("Product"+product);
            prod1 = service.updateproduct(id, product, imageFile);
            return new  ResponseEntity<> ("Updated",HttpStatus.OK);
        }catch (Exception e){
  return new ResponseEntity<>("Not Updated",HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }


    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteproduct(@PathVariable int id){

        Product prod1= service.getproductbyid(id);
        if(prod1!=null){
            service.deleteproduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Not deleted",HttpStatus.INTERNAL_SERVER_ERROR);
        }


        }


        @GetMapping("/products/search")
       public ResponseEntity<List<Product>> searchproduct(@RequestParam String keyword){
    System.out.println("Search with " +keyword);
        List<Product> products = service.searchproduct(keyword);

        return new ResponseEntity<>(products,HttpStatus.OK);

        }



    }





