package backend.wal.app.onboarding.domain.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Onboarding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "onboarding_id")
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "onboarding")
    private final List<OnboardingTime> times = new ArrayList<>();

    @OneToMany(mappedBy = "onboarding")
    private final List<OnboardingCategory> categories = new ArrayList<>();
}
