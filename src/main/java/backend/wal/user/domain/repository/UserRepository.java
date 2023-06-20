package backend.wal.user.domain.repository;

import backend.wal.user.domain.aggregate.SocialType;
import backend.wal.user.domain.aggregate.UserStatus;
import backend.wal.user.domain.aggregate.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u " +
            "WHERE u.socialInfo.socialId = :socialId " +
            "AND u.socialInfo.socialType = :socialType " +
            "AND u.status = :active")
    User findActiveUserBySocialIdAndSocialType(String socialId, SocialType socialType, UserStatus active);
}
