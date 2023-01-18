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
                    .setPrice(BigDecimal.valueOf(10))
                    .setWeight(100.00)
                    .setImageUrl(new CloudinaryImage().setUrl("https://res.cloudinary.com/dl72c1rli/image/upload/v1649444689/yuwqnsjlbfjcl1k353e3.jpg"))
                    .setName("Pizza")
                    .setCategory("Food")
                    .setIngredients(Set.of("Shunka","Maslo"));

            this.productRepository.save(product);
        }
    }

    public List<Product> findAllByName(String name) {
        return this.productRepository.findAllByName(name);
    }

}
