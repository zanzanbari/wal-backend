package backend.wal.wal.repository;

import backend.wal.wal.domain.entity.NextWal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NextWalRepository extends JpaRepository<NextWal, Long> {
}
