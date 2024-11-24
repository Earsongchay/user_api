package com.example.user_api.Specification;

import com.example.user_api.Model.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
  public static Specification<User> search(String search) {
    return (root, query, criteriaBuilder) -> {
      if (search == null || search.isEmpty()) {
        return criteriaBuilder.conjunction(); // Return all if no search term
      }

      String lowerSearch = "%" + search.toLowerCase() + "%";

      return criteriaBuilder.or(
          criteriaBuilder.like(root.get("firstName"), lowerSearch),
          criteriaBuilder.like(root.get("lastName"), lowerSearch),
          criteriaBuilder.like(root.get("email"), lowerSearch));
    };
  }
}
