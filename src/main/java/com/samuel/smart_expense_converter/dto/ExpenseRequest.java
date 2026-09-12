package com.samuel.smart_expense_converter.dto;

import jakarta.validation.Valid;

public record ExpenseRequest(@Valid String text) {

}
