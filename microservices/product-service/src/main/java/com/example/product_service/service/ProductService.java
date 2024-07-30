package com.example.product_service.service;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.product_service.dto.ProductRequest;
import com.example.product_service.dto.ProductResponse;
import com.example.product_service.model.Product;
import com.example.product_service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
	
	private final ProductRepository productRepository;

	@Autowired
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	public void createProduct(ProductRequest productRequest){
		Product product = Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.skuCode(productRequest.getSkuCode())
				.build();
		
		
		productRepository.save(product);
		Gson gson = new Gson();
		String publish = gson.toJson(product);
		kafkaTemplate.send("new-product", publish);
		log.info("Product {} is saved", product.getId());
	}

	public List<ProductResponse> getAllProducts() {
		List<Product> products = productRepository.findAll();

		return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
	}

	private ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.skuCode(product.getSkuCode())
				.build();
	}
}
