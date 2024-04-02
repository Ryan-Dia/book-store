package bookstore.shop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() { // JPA 스펙상 엔티티나 임베디드 타입(@Embeddable)은 자바 기본 생성자를 public 또는 protected로 설정해야한다.
    } // public으로 두는 것보다는 protected로 설정하는 것이 그나마 더 안전하다.


    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
