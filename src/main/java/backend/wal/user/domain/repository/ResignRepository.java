package backend.wal.user.domain.repository;

import backend.wal.user.domain.aggregate.entity.Resign;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResignRepository extends JpaRepository<Resign, Long> {
}
