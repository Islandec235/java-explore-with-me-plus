package ru.yandex.practicum.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {
    @Email
    @NotNull
    private String email;
    @NotNull
    @Size(min = 2, max = 250)
    private String name;
}
