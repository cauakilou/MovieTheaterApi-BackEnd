package BackEnd.MovieTheatherAPI.Model.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
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
}


