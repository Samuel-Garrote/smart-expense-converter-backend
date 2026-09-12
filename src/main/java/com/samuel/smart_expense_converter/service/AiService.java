package com.samuel.smart_expense_converter.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import com.samuel.smart_expense_converter.dto.ExtractedExpense;

@Service
public class AiService {

  private final ChatClient chatClient;

  // Construimos el cliente que utilizaremos para hablar con la IA.
  public AiService(ChatClient.Builder builder) {
    this.chatClient = builder.build();
  }

  public ExtractedExpense extractExpense(String text) {

    // Convertirá la respuesta de la IA en un ExtractedExpense.
    BeanOutputConverter<ExtractedExpense> outputConverter = new BeanOutputConverter<>(ExtractedExpense.class);

    // Construimos el prompt que enviamos a la IA.
    String prompt = """
        Extract the expense amount and currency from the text below.
        %s

        Text: "%s"
        """.formatted(outputConverter.getFormat(), text);

    // Enviamos el prompt a la IA y obtenemos su respuesta.
    String aiResponse = chatClient.prompt()
        .user(prompt)
        .call()
        .content();

    // Convertimos la respuesta de la IA en un ExtractedExpense.
    return outputConverter.convert(aiResponse);
  }
}