package com.example.user_api.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "tbl_product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Long id;

  @Column(name = "name", nullable = false)
  String name;

  @Column(name = "price", nullable = false)
  Double price;

  @Column(name = "qty", nullable = false)
  Long qty;

  @Column(name = "amount", nullable = false)
  Long amount;
}
