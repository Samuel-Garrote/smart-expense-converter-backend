package com.samuel.smart_expense_converter.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.samuel.smart_expense_converter.dto.ExpenseResponse;
import com.samuel.smart_expense_converter.dto.ExtractedExpense;

@Service
public class ExpenseConversionService {
  private final AiService aiService;
  private final ExchangeRateService exchangeRateService;

  // Guardamos los servicios que necesitamos para extraer y convertir el gasto.
  public ExpenseConversionService(
      AiService aiService,
      ExchangeRateService exchangeRateService) {
    this.aiService = aiService;
    this.exchangeRateService = exchangeRateService;
  }

  public ExpenseResponse convert(String text) {
    try {
      // Extraemos el importe y la moneda del texto usando la IA.
      ExtractedExpense extractedExpense = aiService.extractExpense(text);

      // Obtenemos el cambio de esa moneda a EUR.
      BigDecimal rate = exchangeRateService
          .getRateToEur(extractedExpense.currency());

      // Convertimos el importe a EUR.
      BigDecimal convertedAmount = extractedExpense.amount().multiply(rate);

      // Creamos el mensaje final para el usuario.
      String message = String.format(
          "%s %s is equivalent to %.2f EUR",
          extractedExpense.amount(),
          extractedExpense.currency(),
          convertedAmount);

      // Devolvemos la respuesta final.
      return new ExpenseResponse(message);
    } catch (Exception e) {
      // Si algo falla (IA, API de cambio, red...), lo envolvemos en una excepción
      // clara.
      throw new RuntimeException("No se pudo convertir el gasto: " + e.getMessage(), e);
    }
  }
}