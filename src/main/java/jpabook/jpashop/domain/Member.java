package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    private String myAllergen1;
    private String myAllergen2;
    private String myAllergen3;

    //private String IngreCategory;


    @OneToMany(mappedBy = "member")
    private List<Order> orders=new ArrayList<>();
}