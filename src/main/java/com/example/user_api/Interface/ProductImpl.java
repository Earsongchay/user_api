package com.example.user_api.Interface;

import com.example.user_api.Model.PaginateRequest;
import com.example.user_api.Model.PaginateResponse;
import com.example.user_api.Model.Product;
import java.util.List;

public interface ProductImpl {

  Product getProductById(Long id);

  List<Product> getProducts();

  Product addProduct(Product Product);

  String removeProduct(Long id);

  Product partialUpdateProduct(Long id, Product Product);

  Product updateProduct(Long id, Product Product);

  PaginateResponse<Product> productPaginate(PaginateRequest request);
}
