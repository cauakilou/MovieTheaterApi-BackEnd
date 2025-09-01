package BackEnd.MovieTheatherAPI.Model.Exception;

public class PasswordInvalidException extends RuntimeException {
    public PasswordInvalidException(String s) {
        super(s);
    }
}
