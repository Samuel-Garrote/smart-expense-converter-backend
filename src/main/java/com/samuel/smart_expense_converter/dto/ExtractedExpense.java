package com.samuel.smart_expense_converter;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record ExtractedExpense(@JsonPropertyDescription("The numeric amount of the expense") BigDecimal amount,
    @JsonPropertyDescription("The ISO 4217 currency code of the expense, e.g. USD, EUR, GBP") String currency) {
}
