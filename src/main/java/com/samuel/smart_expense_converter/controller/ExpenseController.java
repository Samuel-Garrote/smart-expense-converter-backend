package com.samuel.smart_expense_converter.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samuel.smart_expense_converter.dto.ExpenseRequest;
import com.samuel.smart_expense_converter.dto.ExpenseResponse;
import com.samuel.smart_expense_converter.service.ExpenseConversionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "${app.cors.allowed-origin}")
@Tag(name = "Expenses", description = "Convert a free-text foreign expense into EUR")
public class ExpenseController {

  private final ExpenseConversionService expenseConversionService;

  public ExpenseController(ExpenseConversionService expenseConversionService) {
    this.expenseConversionService = expenseConversionService;
  }

  @Operation(summary = "Convert a free-text expense to EUR")
  @PostMapping("/convert")
  public ExpenseResponse convert(@Valid @RequestBody ExpenseRequest request) {
    return expenseConversionService.convert(request.text());
  }
}