package backend.wal.resign.domain.repository;

import backend.wal.resign.domain.Resign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResignRepository extends JpaRepository<Resign, Long> {
}
