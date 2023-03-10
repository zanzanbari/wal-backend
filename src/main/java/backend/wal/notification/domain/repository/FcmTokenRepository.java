package backend.wal.notification.domain.repository;

import backend.wal.notification.domain.entity.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {

    boolean existsFcmTokenByUserId(Long userId);

    FcmToken findFcmTokenByUserId(Long userId);
}
