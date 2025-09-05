package BackEnd.MovieTheatherAPI.Model.Dto.Session.Movie;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class MovieCreateDto {

    @NotBlank
    private String nome;

    @NotBlank
    private String sinopse;

    @NotBlank
    private String poster;

    @NotBlank(message = "A classificação é obrigatória.")
    @Pattern(regexp = "LIVRE|DEZ_ANOS|DOZE_ANOS|QUATORZE_ANOS|DEZESSEIS_ANOS|DEZOITO_ANOS", message = "Classificação inválida.")
    private String classificacao;

    @NotBlank(message = "O gênero é obrigatório.")
    @Pattern(regexp = "ACAO|AVENTURA|COMEDIA|DRAMA|FICCAO_CIENTIFICA|TERROR|SUSPENSE|ROMANCE|ANIMACAO|FANTASIA|DOCUMENTARIO|MUSICAL", message = "Gênero inválido.")
    private String genero;

    @NotNull(message = "A duração é obrigatória.")
    @Min(value = 1, message = "A duração mínima deve ser de 1 minuto.")
    @Max(value = 1439, message = "A duração máxima deve ser menor que 1 dia (1440 minutos).")
    private Integer duracao;

}