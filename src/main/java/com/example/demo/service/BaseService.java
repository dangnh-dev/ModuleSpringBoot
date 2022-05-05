package com.example.demo.service;

import java.util.List;

public interface BaseService<T> {
    List<T> getAll();
    T findById(Long id);
    boolean save(T t);
    boolean edit(T t);
    boolean delete(Long id);
}
