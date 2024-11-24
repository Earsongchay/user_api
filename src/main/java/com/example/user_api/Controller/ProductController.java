package com.example.user_api.Controller;

import com.example.user_api.Model.PaginateRequest;
import com.example.user_api.Model.PaginateResponse;
import com.example.user_api.Model.Product;
import com.example.user_api.Service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @Operation(summary = "Get product by ID", description = "Fetch a product by their unique ID")
  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    Product product = productService.getProductById(id);
    return ResponseEntity.ok(product);
  }

  //  @Operation(summary = "Get all products", description = "Fetch all products in the system")
  //  @GetMapping("/all")
  //  public ResponseEntity<List<Product>> getAllProducts() {
  //    List<Product> products = productService.getProducts();
  //    return ResponseEntity.ok(products);
  //  }

  @Operation(summary = "Add a new product", description = "Add a new product to the system")
  @PostMapping
  public ResponseEntity<Product> addProduct(@RequestBody Product product) {
    return ResponseEntity.status(201).body(productService.addProduct(product));
  }

  @Operation(summary = "Remove a product", description = "Remove a product from the system by ID")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> removeProduct(@PathVariable Long id) {
    String msg = productService.removeProduct(id);
    return ResponseEntity.ok(msg);
  }

  @Operation(
      summary = "Partially update a product",
      description = "Update certain fields of a product")
  @PatchMapping("/{id}")
  public ResponseEntity<Product> partialUpdateProduct(
      @PathVariable Long id, @RequestBody Product product) {
    return ResponseEntity.ok(productService.partialUpdateProduct(id, product));
  }

  @Operation(
      summary = "Update a product",
      description = "Update a product's information completely")
  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(
      @PathVariable Long id, @RequestBody Product product) {
    return ResponseEntity.ok(this.productService.updateProduct(id, product));
  }

  @Operation(
      summary = "Get paginated products",
      description = "Fetch a paginated list of products with pagination parameters")
  @GetMapping
  public PaginateResponse<Product> productPaginate(
      @RequestParam(defaultValue = "1") @Parameter(description = "Page number") int page,
      @RequestParam(defaultValue = "10") @Parameter(description = "Number of products per page")
          int size,
      @RequestParam(defaultValue = "") @Parameter(description = "Search") String search) {
    var request = PaginateRequest.builder().page(page).size(size).search(search).build();
    return productService.productPaginate(request);
  }
}
