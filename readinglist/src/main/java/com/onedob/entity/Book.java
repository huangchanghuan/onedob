package com.onedob.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author 黄昌焕
 * @Date 2017-06-29  20:56
 */
@Entity
public class Book {
    private final static Logger logger = LoggerFactory.getLogger(Book.class);

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String reader;
    private String name;
    private String title;
    private String author;

    public void setReader(String reader) {
        this.reader = reader;
    }

    public String getReader() {
        return reader;

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public static Logger getLogger() {
        return logger;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
