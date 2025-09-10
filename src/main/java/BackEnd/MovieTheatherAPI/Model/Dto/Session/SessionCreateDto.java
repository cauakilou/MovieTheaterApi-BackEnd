package BackEnd.MovieTheatherAPI.Model.Dto.Session;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SessionCreateDto {

    @NotBlank(message = "O campo 'data' é obrigatório.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "A data deve estar no formato AAAA-MM-DD (ex: 2025-12-31).")
    private String data;

    @NotBlank(message = "O campo 'inicio' é obrigatório.")
    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "O horário de início deve estar no formato HH:mm (ex: 21:30).")
    private String inicio;

    @NotNull(message = "O ID do filme não pode ser nulo.")
    @Positive(message = "O ID do filme deve ser um número positivo.")
    private Long idFilme;

    @NotNull(message = "O ID da sala não pode ser nulo.")
    @Positive(message = "O ID da sala deve ser um número positivo.")
    private Long idSala;
}