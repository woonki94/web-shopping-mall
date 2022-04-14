package com.webshoppingmall.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.webshoppingmall.constant.ItemSellStatus;
import com.webshoppingmall.entity.Item;
import com.webshoppingmall.entity.QItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;
    @PersistenceContext
    EntityManager em;

    public void createItemList(){
        for(int i =1; i<=10; i++){
            Item item = new Item();
            item.setItemName("테스트 상품명"+i);
            item.setPrice(1000 + (i*100));
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    public void 상품_저장_테스트(){
        //given
        Item item = new Item();
        String itemName= "상품명";
        item.setItemName(itemName);
        item.setPrice(1000);
        item.setItemDetail("테스트 상품");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        //when
        Item savedItem = itemRepository.save(item);
        //then
        Assertions.assertThat(savedItem.getItemName()).isEqualTo(itemName);
    }

    @Test
    public void 상품명으로_찾기_테스트(){
        //given
        this.createItemList();
        //when
        List<Item> itemList = itemRepository.findByItemName("테스트 상품명1");
        //then
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    public void 쿼리를_이용한_조회_테스트(){
        //given
        this.createItemList();
        //when
        List<Item> itemList = itemRepository.findItemDetail("테스트 상품 상세 설명");
        //then
        for(Item i : itemList){
            System.out.println(i.toString());
        }
    }

    @Test
    public void Querydsl_조회_테스트(){
        //given
        this.createItemList();
        //when
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        JPAQuery<Item> query = jpaQueryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%"+"테스트 상품 상세 설명"+ "%"))
                .orderBy(qItem.price.desc());
        List<Item> itemList = query.fetch();

        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }
}
