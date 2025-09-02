package BackEnd.MovieTheatherAPI.Model.Exception;

public class MovieAlreadyExist extends RuntimeException {
    public MovieAlreadyExist(String message) {
        super(message);
    }
}
