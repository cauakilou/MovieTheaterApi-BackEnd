package BackEnd.MovieTheatherAPI.Model.Dto.User;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponseDto {

    private long id;
    private String email;
    private String role;

}
