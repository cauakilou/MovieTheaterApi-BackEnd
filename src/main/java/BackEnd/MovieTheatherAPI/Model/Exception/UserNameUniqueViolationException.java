package BackEnd.MovieTheatherAPI.Model.Exception;

public class UserNameUniqueViolationException extends RuntimeException {
    public UserNameUniqueViolationException(String s) {
        super(s);
    }
}
