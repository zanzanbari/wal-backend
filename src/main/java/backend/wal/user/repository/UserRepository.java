package backend.wal.user.repository;

import backend.wal.user.domain.entity.SocialType;
import backend.wal.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long userId);
    boolean existsUserBySocialInfoSocialIdAndSocialInfoSocialType(String socialId, SocialType socialType);
}
