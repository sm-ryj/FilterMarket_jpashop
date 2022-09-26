package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String all_ingredient;
    //private String ingre_category;
    //private String page;

    //private String goodImage;
}