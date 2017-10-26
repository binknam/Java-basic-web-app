//
// Copyright (c) 2015 KMS Technology.
//
package vn.kms.fundamentals.basicwebapp.repository;

import java.util.List;

public interface GenericRepository<E, I> {
    E findById(I id);

    List<E> findAll();

    void create(E entity);

    void update(E entity);

    boolean delete(I id);
}
