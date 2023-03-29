package backend.wal.user.domain.repository;

import backend.wal.user.domain.aggregate.SocialType;
import backend.wal.user.domain.aggregate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserById(Long userId);

    User findUserBySocialInfoSocialIdAndSocialInfoSocialType(String socialId, SocialType socialType);
}
