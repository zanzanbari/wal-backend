package backend.wal.user.domain.aggregate.entity;

import backend.wal.onboarding.domain.entity.Onboarding;
import backend.wal.reservation.domain.entity.Reservation;
import backend.wal.user.domain.aggregate.vo.SocialInfo;
import backend.wal.user.app.dto.request.CreateUserDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onboarding_id")
    private Onboarding onboarding;

    @OneToMany(mappedBy = "user")
    private final List<Reservation> reservations = new ArrayList<>();

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

    public void setOnboardInfo(Onboarding onboarding) {
        this.onboarding = onboarding;
    }
}
