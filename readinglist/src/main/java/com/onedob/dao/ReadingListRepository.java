package com.onedob.dao;

import com.onedob.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @Author 黄昌焕
 * @Date 2017-06-29  21:06
 */
public interface ReadingListRepository extends JpaRepository<Book,Long> {
    List<Book> findByReader(String reader);
}
