package BackEnd.MovieTheatherAPI.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Movies")
public class MovieEntity {

    public enum Genero {
        ACAO,
        AVENTURA,
        COMEDIA,
        DRAMA,
        FICCAO_CIENTIFICA,
        TERROR,
        SUSPENSE,
        ROMANCE,
        ANIMACAO,
        FANTASIA,
        DOCUMENTARIO,
        MUSICAL
    }

    public enum Classificacao {
        LIVRE,
        DEZ_ANOS,
        DOZE_ANOS,
        QUATORZE_ANOS,
        DEZESSEIS_ANOS,
        DEZOITO_ANOS
    }

    // Novo Enum para o status do filme
    public enum Status {
        EM_CARTAZ,
        EM_BREVE,
        FORA_DE_CARTAZ
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable = false, unique = true, length = 100)
    private String nome;

    @Column(name = "Sinopse", nullable = false, length = 350)
    private String sinopse;

    @Enumerated(EnumType.STRING)
    @Column(name = "Rating", nullable = false, length = 20)
    private Classificacao classificacao;

    @Column(name = "Duration", nullable = false, length = 10)
    private Duration duracao;

    @Enumerated(EnumType.STRING)
    @Column(name = "Gender", nullable = false, length = 25)
    private Genero genero;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 20)
    private Status status = Status.EM_BREVE;

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
        MovieEntity that = (MovieEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", classificacao=" + classificacao +
                ", duracao=" + duracao +
                ", genero=" + genero +
                ", status=" + status + // Adicionado na saída do toString
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}