package backend.wal.notification.domain.repository;

import backend.wal.notification.domain.FcmToken;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {

    boolean existsFcmTokenByUserId(Long userId);

    Optional<FcmToken> findFcmTokenByUserId(Long userId);

    List<FcmToken> findFcmTokensByUserIdIn(List<Long> userIds);

    void deleteByUserId(Long userId);
}
