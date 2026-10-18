package com.techie.microservices.product.service;

import com.techie.microservices.product.dto.ProductRequest;
import com.techie.microservices.product.dto.ProductResponse;
import com.techie.microservices.product.model.Product;
import com.techie.microservices.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    public final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
     Product product = Product.builder()
            .id(productRequest.id())
            .name(productRequest.name())
            .description(productRequest.description())
            .price(productRequest.price())
            .build();

     System.out.println(product.toString());

         productRepository.save(product);

        log.info("Product {} is saved", product.getId());
         return new ProductResponse(
                 product.getId(),
                 product.getName(),
                 product.getDescription(),
                 product.getPrice()
         );
}

    public ProductResponse getProductById(String id) {
        log.info("Product {} is retrieved", id);
        return productRepository.findById(id)
                .stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()
                )).findFirst()
                .orElse(null);


    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()
                )).toList();
    }
}
