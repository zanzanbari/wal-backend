package backend.wal.wal.item.adapter.out.persistence;

import backend.wal.wal.common.domain.WalCategoryType;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(nullable = false)
    private String contents;

    private String imageUrl;

    @Column(nullable = false)
    private Integer categoryItemNumber; // 카테고리별 유니크한 번호

    protected ItemEntity() {
    }

    @Builder(builderMethodName = "testBuilder")
    public ItemEntity(CategoryEntity category, String contents, String imageUrl, Integer categoryItemNumber) {
        this.category = category;
        this.contents = contents;
        this.imageUrl = imageUrl;
        this.categoryItemNumber = categoryItemNumber;
    }

    public Long getId() {
        return id;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public WalCategoryType getCategoryType() {
        return category.getCategoryType();
    }

    public String getContents() {
        return contents;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getCategoryItemNumber() {
        return categoryItemNumber;
    }
}
