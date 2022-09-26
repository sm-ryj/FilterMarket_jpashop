package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        //엔티티 조회
        Member member=memberRepository.findOne(memberId);
        Item item=itemRepository.findOne(itemId);

        //배송 정보 생성
        Delivery delivery=new Delivery();
        delivery.setAddress(member.getAddress());

        //주문 상품 생성
        OrderItem orderItem=OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order=Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    //영속성 컨텍스트가 자동 변경
   /* @Transactional
    public void updateItembuy(Long orderId, String name, int price, int stockQuantity, String all_ingredient, String ingre_category){

        Order findOrder = orderRepository.findOne(orderId);

        //findItem.change(name, price, stockQuantity);
        findItem.setName(name);
        findItem.setPrice(price);

        findItem.setStockQuantity(stockQuantity);
        findItem.setAll_ingredient(all_ingredient);
        findItem.setIngre_category(ingre_category);
        findItem.setName(name);
    }*/

    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }

}
