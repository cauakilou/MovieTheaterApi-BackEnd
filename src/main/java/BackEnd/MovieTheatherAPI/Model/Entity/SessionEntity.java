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

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Sessions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;

    @Column(name = "Hour",nullable = false,length = 25)
    private LocalDateTime horario;

    @ManyToOne
    @JoinColumn(name = "Movie_id")
    private MovieEntity filme;

    @ManyToOne
    @JoinColumn(name = "Room_id")
    private RoomEntity sala;

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
        SessionEntity that = (SessionEntity) o;
        return id == that.id;
    }

    @Override
    public String toString() {
        return "SessionEntity{" +
                "id=" + id +
                ", horario=" + horario +
                ", filme=" + filme +
                ", sala=" + sala +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
