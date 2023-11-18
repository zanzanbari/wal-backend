package backend.wal.admin.domain.aggregate;

import backend.wal.support.Role;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 500)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    @CreatedDate
    private LocalDateTime createdAt;

    public Admin(final String email, final String password) {
        this.email = email;
        this.password = password;
        this.role = Role.ADMIN;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role.name();
    }
}
