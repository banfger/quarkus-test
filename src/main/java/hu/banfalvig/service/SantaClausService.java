package hu.banfalvig.service;

import hu.banfalvig.entity.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class SantaClausService {

    private final EntityManager em;

    @Transactional
    public void createBook(String name, String author) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setOnSale(false);
        em.persist(book);
    }
}