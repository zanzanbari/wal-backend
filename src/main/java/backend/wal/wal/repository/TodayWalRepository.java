package backend.wal.wal.repository;

import backend.wal.wal.domain.entity.TodayWal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodayWalRepository extends JpaRepository<TodayWal, Long> {
    List<TodayWal> findAllByUserId(Long userId);
}
