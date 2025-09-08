package BackEnd.MovieTheatherAPI.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Sessions")
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;

    @Column(name = "Room",nullable = false)
    private RoomEntity room;

    @Column(name = "Seats")
    private List<SeatEntity> disponiveis;

    @Column(name = "Movie",nullable = false)
    private MovieEntity movie;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status",nullable = false,length = 25)
    private SessionEntity.Status status = Status.A_EXIBIR;

    @Column(name = "Data")
    private LocalDate data;

    @Column(name = "Horario")
    private LocalTime horario;


    @CreatedDate
    @Column(name = "Creation_Date")
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "Modification_Date")
    private LocalDateTime modificationDate;

    @CreatedBy
    @Column(name = "Created_By")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "Modified_By")
    private String modifiedBy;


    public enum Status{
        OCORRENDO, PASSADA, A_EXIBIR
    }
}
