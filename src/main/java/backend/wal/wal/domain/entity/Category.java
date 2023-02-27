package backend.wal.app.wal.domain.entity;

import backend.wal.app.onboarding.domain.entity.WalCategoryType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalCategoryType categoryType;

    @OneToMany(mappedBy = "category")
    private List<Item> items = new ArrayList<>();
}
