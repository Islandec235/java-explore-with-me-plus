package ru.yandex.practicum.cfg;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Explore With Me | Stats service",
                description = "Сервис статистики EWM", version = "1.0.0",
                contact = @Contact(
                        name = "Danila",
                        email = "demedan.2005@gmail.com"
                )
        )
)
public class SwaggerStatsConfiguration {
}
