package BackEnd.MovieTheatherAPI.Model.Utils;

import java.time.Duration;
import java.time.LocalTime;

public class TimeConverter {

    public static LocalTime termino(Duration duration, LocalTime tempo){
        return tempo.plus(duration);
    }
}
