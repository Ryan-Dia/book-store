package bookstore.shop.domain.Item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B") //싱글테이블이니까 구분할 수 있는 무언가가 있어야함
@Getter
@Setter
public class Book extends Item{

    private String author;
    private String isbn;


}
