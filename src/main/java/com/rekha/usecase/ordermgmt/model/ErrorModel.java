package com.rekha.usecase.ordermgmt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorModel {
  private String message;
  private String code;
  private List<ErrorDetails> errors;
}
