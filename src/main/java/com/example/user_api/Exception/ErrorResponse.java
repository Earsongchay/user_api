package com.example.user_api.Exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
  private int status;
  private String error;
  private String message;

  public ErrorResponse(int status, String error, String message) {
    this.status = status;
    this.error = error;
    this.message = message;
  }
}
