package com.example.user_api.Model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginateResponse<T> {
  private List<T> content;
  private int page;
  private int size;
  private long total;
}
