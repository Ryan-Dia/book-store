package bookstore.shop.domain.delivery;

import bookstore.shop.domain.Address;
import bookstore.shop.domain.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name= "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.ORDINAL) // 기본값이 ORDINAL 이다. // 기본값으로하면  컬럼이 1,2,3,4 숫자로 들어가는데 만약 중간에 상태가 추가되면  숫자가 꼬인다
    private DeliveryStatus status; // READY, COMP
}
