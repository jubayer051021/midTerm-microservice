package com.BjitMidTerm.Bookservice.repository;

import com.BjitMidTerm.Bookservice.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,Long> {
    BookEntity findByBookId(Long bookId);
}
