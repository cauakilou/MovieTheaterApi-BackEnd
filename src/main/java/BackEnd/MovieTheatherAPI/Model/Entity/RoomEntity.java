package BackEnd.MovieTheatherAPI.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Rooms")
public class RoomEntity {
    public enum TipoSala {
        PADRAO,
        IMAX,
        TRES_D,
        PRIME,
        VIP
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "Model",nullable = false,length = 10)
    private TipoSala tipo;

    @Column(name = "Capacity",nullable = false,length = 4)
    private int capacidade;

    @Column(name = "Addons",length = 40)
    private List<String> recursosAdicionais;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RoomEntity that = (RoomEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RoomEntity{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", capacidade=" + capacidade +
                ", recursosAdicionais=" + recursosAdicionais +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}