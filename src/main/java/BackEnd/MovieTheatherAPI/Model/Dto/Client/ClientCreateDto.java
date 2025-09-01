package BackEnd.MovieTheatherAPI.Model.Dto.Client;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClientCreateDto {

    @NotNull
    @Size(min = 3,max = 100)
    private String name;
    @Size(min = 11,max = 11)
    @CPF
    private String cpf;
    @NotNull
    @Pattern(regexp = "^(\\([1-9]{2}\\)\\s?|([1-9]{2}))(9[0-9]{4}|[2-9][0-9]{3})\\-?[0-9]{4}$")
    private String phoneNumber;

    }

