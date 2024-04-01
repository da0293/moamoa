package com.moamoa.project.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

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
    @Sql("classpath:db/tableInit.sql")
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
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책수정_test() {
        // 현재 1, junit, 나나 들어가있음
        // given
        Long id = 1L;
        String title = "junit5";
        String author = "아리";
        Book book = new Book(id, title, author);

        // when
        bookRepository.findAll().stream()
                .forEach((b) -> {
                    System.out.println("스타트 =======================");
                    System.out.println(b.getId());
                    System.out.println(b.getTitle());
                    System.out.println(b.getAuthor());
                    System.out.println("1번완료 ======================");
                });
        Book bookPS = bookRepository.save(book);
        // bookRepository에서 전체를 꺼내서 스트림
        bookRepository.findAll().stream()
                .forEach((b) -> {
                    System.out.println(b.getId());
                    System.out.println(b.getTitle());
                    System.out.println(b.getAuthor());
                    System.out.println("2번완료 ======================");
                });
        // 책 수정이 실행될 때 BeforeEach가 실해되어서 동일한 아이디가 있다면 update, 없다면 insert
        // 지금 insert되었는지 update되었는지 알 수가 없다.
        System.out.println(bookPS.getId());
        System.out.println(bookPS.getTitle());
        System.out.println(bookPS.getAuthor());
        System.out.println("3번완료 ===============================");
        // then
    }

    // 5. 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책삭제하기_test() {
        System.out.println("책삭제하기");
        // when
        Long id = 1L;

        // get
        bookRepository.deleteById(id);

        // then
        assertFalse(bookRepository.findById(id).isPresent()); // false여야 삭제 성공
    }

}
