package BackEnd.MovieTheatherAPI.Model.Dto.User;


import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserPasswordDto {
    private String senhaAtual;
    private String novaSenha;
    private String confirmarSenha;
}
