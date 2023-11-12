package backend.wal.user.domain.aggregate.entity;

import backend.wal.support.Role;
import backend.wal.user.application.port.in.CreateUserDto;
import backend.wal.user.domain.aggregate.SocialInfo;
import backend.wal.user.domain.aggregate.UserStatus;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Embedded
    private SocialInfo socialInfo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    @CreatedDate
    private LocalDateTime createdAt;

    private User(final String nickname, final SocialInfo socialInfo, final UserStatus status, final Role role) {
        this.nickname = nickname;
        this.socialInfo = socialInfo;
        this.status = status;
        this.role = role;
    }

    public static User createGeneral(final CreateUserDto createUserDto) {
        return new User(
                createUserDto.getNickname(),
                SocialInfo.of(createUserDto.getSocialId(), createUserDto.getSocialType()),
                UserStatus.ACTIVE,
                Role.USER
        );
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isDeleted() {
        return this.status == UserStatus.DELETED;
    }

    public void resign() {
        this.status = UserStatus.DELETED;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getRole() {
        return role.name();
    }
}
