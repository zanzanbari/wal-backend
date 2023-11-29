package backend.wal.wal.common;

import backend.wal.config.JpaRepositoryTestConfig;
import backend.wal.wal.item.adapter.out.persistence.CategoryEntity;
import backend.wal.wal.item.adapter.out.persistence.CategoryRepository;
import backend.wal.wal.item.adapter.out.persistence.ItemEntity;
import backend.wal.wal.item.adapter.out.persistence.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static backend.wal.wal.common.domain.WalCategoryType.*;

@JpaRepositoryTestConfig
public abstract class TestItemInitializer {

    @Autowired
    protected ItemRepository itemRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    protected CategoryEntity comedy;
    protected CategoryEntity fuss;
    protected CategoryEntity comfort;
    protected CategoryEntity yell;

    protected ItemEntity comedyItem1;
    protected ItemEntity comedyItem2;

    protected ItemEntity fussItem1;
    protected ItemEntity fussItem2;

    protected ItemEntity comfortItem1;
    protected ItemEntity comfortItem2;

    protected ItemEntity yellItem1;
    protected ItemEntity yellItem2;

    @BeforeEach
    void setUp() {
        comedy = categoryRepository.findByCategoryType(COMEDY);
        fuss = categoryRepository.findByCategoryType(FUSS);
        comfort = categoryRepository.findByCategoryType(COMFORT);
        yell = categoryRepository.findByCategoryType(YELL);
    }

    protected void setForItemRepositoryTest() {
        comedyItem1 = ItemEntity.testBuilder().category(comedy).contents("").imageUrl("").categoryItemNumber(1).build();
        comedyItem2 = ItemEntity.testBuilder().category(comedy).contents("").imageUrl("").categoryItemNumber(2).build();

        fussItem1 = ItemEntity.testBuilder().category(fuss).contents("").imageUrl("").categoryItemNumber(1).build();
        fussItem2 = ItemEntity.testBuilder().category(fuss).contents("").imageUrl("").categoryItemNumber(2).build();

        comfortItem1 = ItemEntity.testBuilder().category(comfort).contents("").imageUrl("").categoryItemNumber(1).build();
        comfortItem2 = ItemEntity.testBuilder().category(comfort).contents("").imageUrl("").categoryItemNumber(2).build();

        yellItem1 = ItemEntity.testBuilder().category(yell).contents("").imageUrl("").categoryItemNumber(1).build();
        yellItem2 = ItemEntity.testBuilder().category(yell).contents("").imageUrl("").categoryItemNumber(2).build();

        itemRepository.save(comedyItem1);
        itemRepository.save(comedyItem2);

        itemRepository.save(fussItem1);
        itemRepository.save(fussItem2);

        itemRepository.save(comfortItem1);
        itemRepository.save(comfortItem2);

        itemRepository.save(yellItem1);
        itemRepository.save(yellItem2);
    }

    protected void setForNexWalRepositoryTest() {
        comedyItem1 = ItemEntity.testBuilder().category(comedy).contents("").imageUrl("").categoryItemNumber(1).build();
        fussItem1 = ItemEntity.testBuilder().category(fuss).contents("").imageUrl("").categoryItemNumber(1).build();
        comfortItem1 = ItemEntity.testBuilder().category(comfort).contents("").imageUrl("").categoryItemNumber(1).build();
        yellItem1 = ItemEntity.testBuilder().category(yell).contents("").imageUrl("").categoryItemNumber(1).build();

        itemRepository.saveAll(List.of(comedyItem1, fussItem1, comfortItem1, yellItem1));
    }

    protected ItemEntity getComedyItem() {
        return comedyItem1;
    }

    protected ItemEntity getFussItem() {
        return fussItem1;
    }

    protected ItemEntity getComfortItem() {
        return comfortItem1;
    }

    protected ItemEntity getYellItem() {
        return yellItem1;
    }

    protected Long getComedyItemId() {
        return comedyItem1.getId();
    }

    protected Long getFussItemId() {
        return fussItem1.getId();
    }

    protected Long getComfortItemId() {
        return comfortItem1.getId();
    }

    protected Long getYellItemId() {
        return yellItem1.getId();
    }
}
