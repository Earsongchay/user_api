package com.example.user_api.Specification;

import com.example.user_api.Model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
  public static Specification<Product> search(String search) {
    return (root, query, criteriaBuilder) -> {
      if (search == null || search.isEmpty()) {
        return criteriaBuilder.conjunction(); // Return all if no search term
      }

      String lowerSearch = "%" + search.toLowerCase() + "%";

      return criteriaBuilder.like(root.get("name"), lowerSearch);
    };
  }
}
