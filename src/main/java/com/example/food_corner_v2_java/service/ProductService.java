package com.example.food_corner_v2_java.service;

import com.example.food_corner_v2_java.model.Product;
import com.example.food_corner_v2_java.repository.ProductRepository;
import com.example.food_corner_v2_java.utils.CloudinaryImage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void initProductDB() {
        if (this.productRepository.count() == 0) {
            Product product = new Product()
                    .setName("Pizza")
                    .setPrice(BigDecimal.valueOf(15))
                    .setWeight(300)
                    .setImageUrl(new CloudinaryImage("https://res.cloudinary.com/dl72c1rli/image/upload/v1649601451/p5i2hrgtl9a5drkopvmn.jpg","p5i2hrgtl9a5drkopvmn"))
                    .setIngredients(Set.of("Brashno","Sirene"));
            this.productRepository.save(product);
        }
    }

    public List<Product> findAllByName(String name) {
        return this.productRepository.findAllByName(name);
    }

    public Product findByName(String name) {
        return this.productRepository.findByName(name);
    }
}
