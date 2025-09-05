package BackEnd.MovieTheatherAPI.Model.Service;

import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import BackEnd.MovieTheatherAPI.Model.Repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public RoomEntity criarSala(RoomEntity room){
        return roomRepository.save(room);
    }
}
