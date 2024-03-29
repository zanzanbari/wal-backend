package backend.wal.auth.domain.repository;

import backend.wal.auth.domain.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findRefreshTokenByUserId(Long userId);

    Optional<RefreshToken> findRefreshTokenByValue(String refreshToken);

    void deleteAllByUserId(Long userId);
}
