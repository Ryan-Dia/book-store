package bookstore.shop.repository;

import bookstore.shop.domain.Address;
import bookstore.shop.domain.order.OrderStatus;

import java.time.LocalDateTime;

public record OrderSimpleQueryDto(
        Long orderId,
        String name,
        LocalDateTime orderDate,
        OrderStatus orderStatus,
        Address address
) {
}