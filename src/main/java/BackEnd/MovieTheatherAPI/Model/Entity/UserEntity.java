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
@Table(name = "Users")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;

    @Column(name = "user_email",nullable = false,unique = true,length = 100)
    private String email;

    @Column(name = "user_password",nullable = false, length = 200)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role",nullable = false,length = 25)
    private Role role = Role.ROLE_CLIENT;

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


    public enum Role{
        ROLE_ADMIN, ROLE_CLIENT
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                '}';
    }
}
