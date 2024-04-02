package bookstore.shop.domain;

import bookstore.shop.domain.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    @Embedded // 내장 타입을 포함 했다.
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>(); // 이게 best  컬렉션은 필드에서 바로 초기화 하는 것이 안전합니다.





}
