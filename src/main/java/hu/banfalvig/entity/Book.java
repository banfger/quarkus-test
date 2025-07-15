package hu.banfalvig.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BOOKS")
@Setter
@Getter
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String author;

    @Column(name = "ON_SALE")
    private Boolean onSale;
}