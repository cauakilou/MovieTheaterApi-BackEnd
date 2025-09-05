package BackEnd.MovieTheatherAPI.Model.Dto.Session.Room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomCreateDto {
    @NotBlank(message = "O nome da sala é obrigatória.")
    private String nome;

    @NotBlank(message = "O tipo de Sala é obrigatório.")
    @Pattern(regexp = "PADRAO|IMAX|TRES_D|PRIME|VIP", message = "Tipo de sala inválida.")
    private String tipo;

    @NotNull
    private int linha;
    @NotNull
    private int coluna;
}
