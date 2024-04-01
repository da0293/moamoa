package com.moamoa.project.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void 테스트시작전() {
        String title = "junit";
        String author = "나나";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);
    }

    // 1. 책 등록
    @Test
    public void 책등록_test() {
        System.out.println("책등록_test 실행");

        // given(데이터 준비)
        // 컨트롤러와 서비스가 Ioc컨테이너에 등록되지 않으므로 내가 직접 등록
        String title = "junit";
        String author = "나나";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when(테스트 실행)
        Book bookPS = bookRepository.save(book);
        // then(검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }

    // 2. 책 목록 보기
    @Test
    public void 책목록보기_test() {
        // given
        System.out.println("책목록보기_test 실행");
        String title = "junit";
        String author = "나나";

        // when
        List<Book> booksPS = bookRepository.findAll();

        // then
        assertEquals(title, booksPS.get(0).getTitle());
        assertEquals(author, booksPS.get(0).getAuthor());
    }

    // 3. 책 한 건 보기
    @Test
    public void 책한건보기_test() {
        // given
        System.out.println("책한건보기_test 실행");
        String title = "junit";
        String author = "나나";

        // when
        Book bookPS = bookRepository.findById(1L).get();

        // then
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }

    // 4.책 수정

    // 5. 책 삭제
}
