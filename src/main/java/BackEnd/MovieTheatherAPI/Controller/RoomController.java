package BackEnd.MovieTheatherAPI.Controller;

import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.PageableMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.Mapper.RoomMapper;
import BackEnd.MovieTheatherAPI.Model.Dto.PageableDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Room.RoomCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Room.RoomResponseDto;
import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.MovieProjection;
import BackEnd.MovieTheatherAPI.Model.Repository.Projection.RoomProjection;
import BackEnd.MovieTheatherAPI.Model.Service.RoomService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/salas")
@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PreAuthorize("hasRole ('ADMIN')")
    @PostMapping
    public ResponseEntity<RoomResponseDto> adicionarRoom(@RequestBody @Valid RoomCreateDto dto) {
        RoomEntity room = RoomMapper.ToRoomEntity(dto);
        roomService.criarSala(room);
        return ResponseEntity.status(201).body(RoomMapper.toDto(room));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDto> buscarRoom(@PathVariable long id){
        return ResponseEntity.ok().body(RoomMapper.toDto(roomService.buscarPorId(id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PageableDto> buscarRooms(@Parameter(hidden = true)@PageableDefault(size = 5, sort = {"nome"}) Pageable pageable){
        Page<RoomProjection> listaDeFilmes = roomService.buscarTodos(pageable);
        return ResponseEntity.ok((PageableMapper.pageableToDTO(listaDeFilmes)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRooms(@PathVariable long id){
        roomService.deletar(id);
        return ResponseEntity.noContent().build();
    }




}
