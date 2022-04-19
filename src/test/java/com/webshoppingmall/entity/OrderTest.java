package com.webshoppingmall.entity;

import com.webshoppingmall.constant.ItemSellStatus;
import com.webshoppingmall.repository.ItemRepository;
import com.webshoppingmall.repository.MemberRepository;
import com.webshoppingmall.repository.OrderItemRepository;
import com.webshoppingmall.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class OrderTest {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    public Item createItem(){
        Item item = new Item();
        item.setItemName("테스트 상품");
        item.setItemDetail("테스트");
        item.setPrice(10000);
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return item;
    }

    public Order createOrder(){
        Order order = new Order();

        for(int i=0; i<3; i++){
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }
        Member member = new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }


    @Test
    public void 영속성_전이_테스트(){
        Order order = this.createOrder();
        orderRepository.saveAndFlush(order);
        em.clear();

        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        Assertions.assertEquals(3,savedOrder.getOrderItems().size());

    }

    @Test
    public void 고아객체_제거_테스트(){
        Order order = this.createOrder();
        order.getOrderItems().remove(0);
        em.flush();
    }
    @Test
    public void 지연로딩_테스트(){
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();

        OrderItem orderItem;
        orderItem = orderItemRepository.findById(orderItemId).orElseThrow(EntityNotFoundException::new);
        System.out.println("OrderClass = " + orderItem.getOrder().getClass());
        System.out.println("==================");
        orderItem.getOrder().getOrderDate();
        System.out.println("==================");

    }
}
