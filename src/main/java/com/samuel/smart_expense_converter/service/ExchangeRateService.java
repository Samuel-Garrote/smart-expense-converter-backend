package com.samuel.smart_expense_converter.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ExchangeRateService {

  private static final String TARGET_CURRENCY = "EUR";

  private final RestClient restClient;

  // Construimos el cliente que utilizaremos para llamar a la API.
  public ExchangeRateService(RestClient.Builder builder) {
    this.restClient = builder
        .baseUrl("https://api.frankfurter.dev/v2")
        .build();
  }

  public BigDecimal getRateToEur(String fromCurrency) {

    // Si ya estamos en EUR, el cambio es 1.
    if (TARGET_CURRENCY.equalsIgnoreCase(fromCurrency)) {
      return BigDecimal.ONE;
    }

    // Pedimos a la API el cambio de la moneda a EUR.
    FrankfurterRateResponse response = restClient.get()
        .uri("/rate/{base}/{quote}", fromCurrency, TARGET_CURRENCY)
        .retrieve()
        .body(FrankfurterRateResponse.class);

    // Comprobamos que la API haya devuelto un cambio válido.
    if (response == null || response.rate() == null) {
      throw new IllegalStateException(
          "No exchange rate found for currency: " + fromCurrency);
    }

    // Devolvemos el tipo de cambio.
    return response.rate();
  }

  // Representa la respuesta que devuelve la API.
  private record FrankfurterRateResponse(
      String date,
      String base,
      String quote,
      BigDecimal rate) {
  }
}