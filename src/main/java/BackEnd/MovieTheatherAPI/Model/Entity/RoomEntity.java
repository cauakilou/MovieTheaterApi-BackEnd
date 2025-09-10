package BackEnd.MovieTheatherAPI.Model.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

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

    @OneToMany(fetch = FetchType.LAZY)
    @Column(name = "Session")
    private List<SessionEntity> sessions = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatEntity> seats = new ArrayList<>();

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

    public void addSeat(SeatEntity seat) {
        this.seats.add(seat);
        seat.setRoom(this);
    }

    public int getCapacidade() {
        return this.seats.size();
    }
}
