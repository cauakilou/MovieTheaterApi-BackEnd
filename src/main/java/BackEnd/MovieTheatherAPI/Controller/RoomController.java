package BackEnd.MovieTheatherAPI.Controller;

import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.RoomMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Room.RoomCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Room.RoomResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import BackEnd.MovieTheatherAPI.Model.Service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/salas")
@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PreAuthorize("hasRole ('ADMIN')")
    @PostMapping
    public ResponseEntity<RoomResponseDto> adicionarRoom(@RequestBody @Valid RoomCreateDto dto){
        RoomEntity room = RoomMapper.ToRoomEntity(dto);
        roomService.criarSala(room);
        return ResponseEntity.status(201).body(RoomMapper.toDto(room));
    }
}
