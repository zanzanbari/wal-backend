package backend.wal.user.domain.repository;

import backend.wal.user.domain.aggregate.SocialType;
import backend.wal.user.domain.aggregate.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserBySocialInfoSocialIdAndSocialInfoSocialType(String socialId, SocialType socialType);
}
