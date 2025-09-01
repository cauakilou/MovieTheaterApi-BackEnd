package BackEnd.MovieTheatherAPI.Model.Exception;

public class CpfUniqueViolationException extends RuntimeException{
    public CpfUniqueViolationException(String message) {
        super(message);
    }
}
