package BackEnd.MovieTheatherAPI.Model.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Seats")
public class SeatEntity {

    public enum TipoAssento {
        PADRAO,
        NAMORADEIRA,
        ACESSIBILIDADE, // Para cadeirantes
        PREFERENCIAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ex: "A", "B", "C"...
    @Column(name = "Row_Identifier",  length = 2)
    private char fileira;

    // Ex: 1, 2, 3...
    @Column(name = "Column_Identifier")
    private int numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "Seat_Type", length = 20)
    private TipoAssento tipo = TipoAssento.PADRAO;

    // Relacionamento: Muitos assentos pertencem a uma sala.
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    // Demais métodos equals, hashCode, toString...
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatEntity that = (SeatEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

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
}


