package bookstore.shop.service;

import bookstore.shop.domain.Item.Item;
import bookstore.shop.domain.Member;
import bookstore.shop.domain.delivery.Delivery;
import bookstore.shop.domain.order.Order;
import bookstore.shop.domain.order.OrderItem;
import bookstore.shop.repository.ItemRepository;
import bookstore.shop.repository.MemberRepository;
import bookstore.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
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

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //1 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //2 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //3 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //4 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //5 주문 저장
        // 원래라면 (2) deliveryRepository.save(delivery) 해주고 4번에 세팅해야하고/ (3) orderItem도 jpa에 넣어주고 값을(4) 세팅해야한다.
        // 하지만 이렇게 하나만 세팅되어있습니다. 왜그럴까요?
        // Order클래스에서  orderItem와 Delivery를 보면  CascadeType.ALL 로 세팅이 되어있어서 따로 해주지 않아도 됩니다.
        // 즉 Order를 persist될 때  OrderItem과 Delivery 가 같이 persist 됩니다.
        orderRepository.save(order);


        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    /**
     * 검색
     */
//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAll(orderSearch);
//    }
}
