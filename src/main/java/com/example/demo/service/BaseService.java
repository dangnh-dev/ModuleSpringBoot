package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService<T> {
    Page<T> getAll(Pageable pageable);
    T findById(Long id);
    boolean save(T t);
    boolean edit(T t);
    boolean delete(Long id);
}
