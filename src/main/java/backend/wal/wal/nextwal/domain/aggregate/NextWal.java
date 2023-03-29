package backend.wal.onboard.nextwal.domain.aggregate;

import backend.wal.onboard.common.WalCategoryType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NextWal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter
    private WalCategoryType categoryType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private NextWal(final Long userId, final WalCategoryType categoryType, final Item item) {
        this.userId = userId;
        this.categoryType = categoryType;
        this.item = item;
    }

    public static NextWal newInstance(Long userId, WalCategoryType categoryType, Item item) {
        return new NextWal(userId, categoryType, item);
    }

    public String getItemContent() {
        return item.getContents();
    }

    public double getCurrentCategoryItemNumber() {
        return item.getCategoryItemNumber();
    }

    public void updateItemToNextItem(Item item) {
        this.item = item;
    }
}
