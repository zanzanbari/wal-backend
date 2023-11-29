package backend.wal.wal.item.adapter.out.persistence;

import backend.wal.wal.common.domain.WalCategoryType;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalCategoryType categoryType;

    protected CategoryEntity() {
    }

    public WalCategoryType getCategoryType() {
        return categoryType;
    }
}
