package bookstore.shop.domain.Item;

import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("M") //싱글테이블이니까 구분할 수 있는 무언가가 있어야함
public class Movie extends Item{

    private String director;
    private String actor;
}
