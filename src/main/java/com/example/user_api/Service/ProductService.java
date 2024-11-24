package com.example.user_api.Service;

import com.example.user_api.Exception.BadRequestException;
import com.example.user_api.Interface.ProductImpl;
import com.example.user_api.Model.PaginateRequest;
import com.example.user_api.Model.PaginateResponse;
import com.example.user_api.Model.Product;
import com.example.user_api.Repository.ProductRepository;
import com.example.user_api.Specification.ProductSpecification;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class ProductService implements ProductImpl {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Product getProductById(Long id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new BadRequestException("Product not found: " + id));
  }

  @Override
  public List<Product> getProducts() {
    log.warn(productRepository.findAll().toString());
    return productRepository.findAll();
  }

  @Override
  public Product addProduct(Product user) {
    return productRepository.save(user);
  }

  @Override
  public String removeProduct(Long id) {
    Product user =
        productRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestException("Product not found: " + id));
    productRepository.delete(user);
    return "Product with ID " + id + " has been successfully removed.";
  }

  @Override
  public Product partialUpdateProduct(Long id, Product updatedProduct) {
    Product existingProduct =
        productRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestException("Product not found: " + id));

    if (updatedProduct.getName() != null) {
      existingProduct.setName(updatedProduct.getName());
    }
    if (updatedProduct.getQty() != null) {
      existingProduct.setQty(updatedProduct.getQty());
    }
    if (updatedProduct.getAmount() != null) {
      existingProduct.setAmount(updatedProduct.getAmount());
    }
    if (updatedProduct.getPrice() != null) {
      existingProduct.setPrice(updatedProduct.getPrice());
    }
    return productRepository.save(existingProduct);
  }

  @Override
  public Product updateProduct(Long id, Product updatedProduct) {
    Product existingProduct =
        productRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestException("Product not found: " + id));

    existingProduct.setPrice(updatedProduct.getPrice());
    existingProduct.setAmount(updatedProduct.getAmount());
    existingProduct.setQty(updatedProduct.getQty());
    existingProduct.setName(updatedProduct.getName());

    return productRepository.save(existingProduct);
  }

  @Override
  public PaginateResponse<Product> productPaginate(PaginateRequest request) {
    Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());

    Specification<Product> spec = null;

    if (StringUtils.hasText(request.getSearch())) {
      spec = ProductSpecification.search(request.getSearch());
    }

    Page<Product> page = productRepository.findAll(spec, pageable);

    // Construct and return the PaginateResponse
    return PaginateResponse.<Product>builder()
        .content(page.getContent())
        .page(page.getNumber() + 1)
        .size(page.getSize())
        .total(page.getTotalElements())
        .build();
  }
}
