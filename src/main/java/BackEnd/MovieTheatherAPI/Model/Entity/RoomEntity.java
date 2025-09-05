package BackEnd.MovieTheatherAPI.Model.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
@Entity@Table(name = "Rooms")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    public enum TipoSala {
        PADRAO,
        IMAX,
        TRES_D,
        PRIME,
        VIP
    }

    @Column(name = "Room_Name", unique = true, length = 20)
    private String nome; // Ex: "Sala 1", "Sala VIP Bradesco"

    @Enumerated(EnumType.STRING)
    @Column(name = "Model", length = 10)
    private TipoSala tipo;

    @JsonManagedReference
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatEntity> seats = new ArrayList<>();

    @CreatedDate
    @Column(name = "Creation_Date")
    private LocalDateTime creationDate;


    public int getCapacidade() {
        return this.seats.size();
    }
}
