package com.moamoa.project.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    // 인터페이스 내용을 추가하세요 (메서드 등)
}
