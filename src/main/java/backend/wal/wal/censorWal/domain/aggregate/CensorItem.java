package backend.wal.wal.censorWal.domain.aggregate;

import backend.wal.wal.common.domain.WalCategoryType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CensorItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalCategoryType categoryType;

    private String contents;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CheckStatus checkStatus;

    public CensorItem(final WalCategoryType categoryType, final String contents, final String imageUrl) {
        this.categoryType = categoryType;
        this.contents = contents;
        this.imageUrl = imageUrl;
        this.checkStatus = CheckStatus.UNCHECKED;
    }

    public Long getId() {
        return id;
    }

    public String getCategoryTypeName() {
        return categoryType.name();
    }

    public String getContents() {
        return contents;
    }
}
