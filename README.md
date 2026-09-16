# Smart Expense Converter — Backend

Backend API that takes a free-text description of a foreign expense (e.g. *"Dinner in New York, 45 dollars"*), uses AI to extract the amount and currency, converts it to EUR using real exchange rates, and returns a natural-language sentence with the result.

## Tech stack

- **Java 17** + **Spring Boot 4.1.1**
- **Spring AI 2.0.1** with **Google Gemini** (`gemini-3.6-flash`) — structured extraction via `BeanOutputConverter`
- **Frankfurter API** (v2) — free, no API key, ECB-based exchange rates
- **springdoc-openapi** — Swagger UI at `/swagger-ui.html`
- **Docker** (multi-stage build) + **GitHub Actions** (CI on every push)
- Deployed on **Railway**

## Architecture

Simple MVC, no persistence layer — this is a stateless pipeline, not a CRUD app:

\`\`\`
ExpenseController → ExpenseConversionService → AiService (extraction)
                                             → ExchangeRateService (EUR rate)
\`\`\`

## Endpoint

\`\`\`
POST /api/expenses/convert
Content-Type: application/json

{ "text": "Dinner in New York, 45 dollars" }
\`\`\`

Response:
\`\`\`json
{ "message": "45 USD is equivalent to 38.74 EUR" }
\`\`\`

## Running locally

Requires a free [Google AI Studio](https://aistudio.google.com/app/apikey) API key.

\`\`\`bash
export GOOGLE_API_KEY=your-key-here
mvn spring-boot:run
\`\`\`

The app starts on \`http://localhost:8080\`.

## Environment variables (production)

| Variable | Description |
|---|---|
| \`SPRING_PROFILES_ACTIVE\` | Set to \`prod\` |
| \`GOOGLE_API_KEY\` | Google AI Studio API key (Gemini) |
| \`CORS_ALLOWED_ORIGIN\` | Frontend origin allowed to call this API (e.g. the Vercel URL) |
| \`PORT\` | Injected automatically by Railway |
