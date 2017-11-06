package vn.kms.fundamentals.basicwebapp.repository.impl;

import vn.kms.fundamentals.basicwebapp.model.Category;
import vn.kms.fundamentals.basicwebapp.model.Product;
import vn.kms.fundamentals.basicwebapp.repository.CategoryRepository;
import vn.kms.fundamentals.basicwebapp.repository.GenericRepository;

import java.util.List;

public class CategoryRepositoryImpl extends GenericRepositoryImpl<Category, Integer> implements CategoryRepository {

    @Override
    public Category findById(Integer id) {
        return super.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return super.findAll();
    }

    @Override
    public void create(Category entity) {
        super.create(entity);
    }

    @Override
    public void update(Category entity) {
        super.update(entity);
    }

    @Override
    public boolean delete(Integer id) {
        return super.delete(id);
    }

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }
}
