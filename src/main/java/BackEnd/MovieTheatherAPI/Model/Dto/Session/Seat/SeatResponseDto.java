package BackEnd.MovieTheatherAPI.Model.Dto.Session.Seat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SeatResponseDto {

    private String fileira;
    private int numero;
    private String tipo;

}
