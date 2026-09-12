package com.samuel.smart_expense_converter;

import jakarta.validation.Valid;

public record ExpenseRequest(@Valid String text) {

}
