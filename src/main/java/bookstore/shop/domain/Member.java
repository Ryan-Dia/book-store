package bookstore.shop.domain;

import bookstore.shop.domain.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    private String name;

    @Embedded // 내장 타입을 포함 했다.
    private Address address;

    @JsonIgnore // 양방이 연관관계가 있으면 둘 중 하나는 josnIgnore 해줘야합니다.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>(); // 이게 best  컬렉션은 필드에서 바로 초기화 하는 것이 안전합니다.





}
