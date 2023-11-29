package backend.wal.wal.nextwal.adapter.out.persistence;

import backend.wal.wal.common.domain.WalCategoryType;

import javax.persistence.*;

@Entity
@Table(name = "next_wal")
public class NextWalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalCategoryType categoryType;

    @Column(nullable = false)
    private Long itemId;

    protected NextWalEntity() {
    }

    public NextWalEntity(Long userId, WalCategoryType categoryType, Long itemId) {
        this.userId = userId;
        this.categoryType = categoryType;
        this.itemId = itemId;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public WalCategoryType getCategoryType() {
        return categoryType;
    }

    public Long getItemId() {
        return itemId;
    }
}
