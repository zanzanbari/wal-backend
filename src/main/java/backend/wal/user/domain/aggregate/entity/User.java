package backend.wal.user.domain.aggregate.entity;

import backend.wal.user.application.port.in.CreateUserDto;
import backend.wal.user.domain.aggregate.SocialInfo;
import backend.wal.user.domain.aggregate.SocialType;
import backend.wal.user.domain.aggregate.UserRole;
import backend.wal.user.domain.aggregate.UserStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
    private UserRole userRole;

    private User(final String nickname, final SocialInfo socialInfo, final UserStatus status, final UserRole userRole) {
        this.nickname = nickname;
        this.socialInfo = socialInfo;
        this.status = status;
        this.userRole = userRole;
    }

    public static User createGeneral(final CreateUserDto createUserDto) {
        return new User(
                createUserDto.getNickname(),
                SocialInfo.of(createUserDto.getSocialId(), createUserDto.getSocialType()),
                UserStatus.ACTIVE,
                UserRole.USER
        );
    }

    public static User createManager(final String nickname, final String socialId, SocialType socialType) {
        return new User(nickname, SocialInfo.of(socialId, socialType), UserStatus.ACTIVE, UserRole.ADMIN);
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
}
