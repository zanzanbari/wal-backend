package backend.wal.wal.item.domain.aggregate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String contents;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private double categoryItemNumber; // 카테고리별 유니크한 번호

    @Builder(builderMethodName = "testBuilder")
    private Item(final Category category, final String contents, final String imageUrl, final double currentItemSize) {
        this.category = category;
        this.contents = contents;
        this.imageUrl = imageUrl;
        this.categoryItemNumber = currentItemSize + 1;
    }

    public Long getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public double getCategoryItemNumber() {
        return categoryItemNumber;
    }
}
