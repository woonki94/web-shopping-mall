package com.webshoppingmall.repository;

import com.webshoppingmall.constant.ItemSellStatus;
import com.webshoppingmall.entity.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

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
        Item findItem = itemRepository.findByItemName(itemName);
        //then
        Assertions.assertThat(savedItem.getItemDetail()).isEqualTo(findItem.getItemDetail());
    }

}
