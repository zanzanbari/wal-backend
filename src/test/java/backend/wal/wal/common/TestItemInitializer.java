package backend.wal.wal.common;

import backend.wal.config.JpaRepositoryTestConfig;
import backend.wal.wal.item.domain.aggregate.Category;
import backend.wal.wal.item.domain.aggregate.Item;

import backend.wal.wal.item.domain.repository.CategoryRepository;
import backend.wal.wal.item.domain.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static backend.wal.wal.common.domain.WalCategoryType.*;
import static backend.wal.wal.common.domain.WalCategoryType.YELL;

@JpaRepositoryTestConfig
public abstract class TestItemInitializer {

    @Autowired
    protected ItemRepository itemRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    protected Category comedy;
    protected Category fuss;
    protected Category comfort;
    protected Category yell;

    protected Item comedyItem1;
    protected Item comedyItem2;

    protected Item fussItem1;
    protected Item fussItem2;

    protected Item comfortItem1;
    protected Item comfortItem2;

    protected Item yellItem1;
    protected Item yellItem2;

    @BeforeEach
    void setUp() {
        comedy = categoryRepository.findByCategoryType(COMEDY);
        fuss = categoryRepository.findByCategoryType(FUSS);
        comfort = categoryRepository.findByCategoryType(COMFORT);
        yell = categoryRepository.findByCategoryType(YELL);
    }

    protected void setForItemRepositoryTest() {
        comedyItem1 = Item.testBuilder().category(comedy).contents("").imageUrl("").currentItemSize(0).build();
        comedyItem2 = Item.testBuilder().category(comedy).contents("").imageUrl("").currentItemSize(1).build();
        comedy.addItem(comedyItem1);
        comedy.addItem(comedyItem2);

        fussItem1 = Item.testBuilder().category(fuss).contents("").imageUrl("").currentItemSize(0).build();
        fussItem2 = Item.testBuilder().category(fuss).contents("").imageUrl("").currentItemSize(1).build();
        fuss.addItem(fussItem1);
        fuss.addItem(fussItem2);

        comfortItem1 = Item.testBuilder().category(comfort).contents("").imageUrl("").currentItemSize(0).build();
        comfortItem2 = Item.testBuilder().category(comfort).contents("").imageUrl("").currentItemSize(1).build();
        comfort.addItem(comfortItem1);
        comfort.addItem(comfortItem2);

        yellItem1 = Item.testBuilder().category(yell).contents("").imageUrl("").currentItemSize(0).build();
        yellItem2 = Item.testBuilder().category(yell).contents("").imageUrl("").currentItemSize(1).build();
        yell.addItem(yellItem1);
        yell.addItem(yellItem2);

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
        comedyItem1 = Item.testBuilder().category(comedy).contents("").imageUrl("").currentItemSize(0).build();
        fussItem1 = Item.testBuilder().category(fuss).contents("").imageUrl("").currentItemSize(0).build();
        comfortItem1 = Item.testBuilder().category(comfort).contents("").imageUrl("").currentItemSize(0).build();
        yellItem1 = Item.testBuilder().category(yell).contents("").imageUrl("").currentItemSize(0).build();

        itemRepository.saveAll(List.of(comedyItem1, fussItem1, comfortItem1, yellItem1));
    }

    protected Item getComedyItem() {
        return comedyItem1;
    }

    protected Item getFussItem() {
        return fussItem1;
    }

    protected Item getComfortItem() {
        return comfortItem1;
    }

    protected Item getYellItem() {
        return yellItem1;
    }
}
