package BackEnd.MovieTheatherAPI.Model.Service;

import BackEnd.MovieTheatherAPI.Model.Repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    @Transactional
    public void amigo(){}

}
