package bookstore.shop.api;

import bookstore.shop.domain.Address;
import bookstore.shop.domain.order.Order;
import bookstore.shop.domain.order.OrderStatus;
import bookstore.shop.repository.OrderRepository;
import bookstore.shop.repository.OrderSearch;
import bookstore.shop.repository.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); // Lazy 강제 초기화
            order.getDelivery().getAddress(); // Lazy 강제 초기화
        }

        // 참고로 이렇게 return 하면 배열이 나가기 때문에 추천하지 않음
        return all;
    }

    /**
     * v1, v2가 가지고 있는 문제점
     *  레이지 로딩으로 인한 데이터베이스 쿼리가 너무 많이 호출되는 문제
     *
     */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        // N + 1 문제 발생 -> 1 + 회원 N + 배송 N
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        return orders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
    }


    // v3와 v2는 결과적으로 같지만 쿼리가 다르다.
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
    }

    // 간단하다고 v4가 더 좋은 것은 아니다. 각각 트레이드 오프가 있다.
    // v3 -> v4보다 재사용성이 높음
    // v4 -> 재사용성이 많이 떨어짐 해당 dto를 사용할 때만 사용가능
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderRepository.findOrderDtos();
    }


    record SimpleOrderDto(
            Long orderId,
            String name,
            LocalDateTime orderDate,
            OrderStatus orderStatus,
            Address address
    ) {
        public SimpleOrderDto(Order order) {
            this(
                    order.getId(),
                    order.getMember().getName(),
                    order.getOrderDate(),
                    order.getStatus(),
                    order.getDelivery().getAddress()
            );
        }
    }
}
