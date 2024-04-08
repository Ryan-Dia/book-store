package bookstore.shop.controller;

import lombok.Getter;

@Getter
public class BookForm {
        private Long id;

        private String name;
        private int price;
        private int stockQuantity;

        private String author;
        private String isbn;

}
