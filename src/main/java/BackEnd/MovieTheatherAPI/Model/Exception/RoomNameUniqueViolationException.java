package BackEnd.MovieTheatherAPI.Model.Exception;

public class RoomNameUniqueViolationException extends RuntimeException {
    public RoomNameUniqueViolationException(String format) {
        super(format);
    }
}
