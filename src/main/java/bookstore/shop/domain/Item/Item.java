package bookstore.shop.domain.Item;

import bookstore.shop.domain.Category;
import bookstore.shop.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categoryies = new ArrayList<>();

    //==비즈니스 로직==//
    // 객체지향적으로 생각해보면 데이터를 가지고 있는 쪽에 비즈니스 메서드가 있는게 응집력이 있어서 좋다.
    // stock을 변경할 때 setter를 가지고 바깥에서 계산하는게 아니라 이 안에서 비즈니스 로직으로 변경한다.
    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0) {
            throw new NotEnoughStockException("need more sotck");
        }
        this.stockQuantity = restStock;
    }
}
