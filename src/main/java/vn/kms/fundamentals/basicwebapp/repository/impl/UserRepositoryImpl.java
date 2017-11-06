package vn.kms.fundamentals.basicwebapp.repository.impl;

import vn.kms.fundamentals.basicwebapp.model.Category;
import vn.kms.fundamentals.basicwebapp.model.User;
import vn.kms.fundamentals.basicwebapp.repository.UserRepository;

import java.util.List;

public class UserRepositoryImpl extends GenericRepositoryImpl<User, String> implements UserRepository {

    @Override
    public User findById(String id) {
        return super.findById(id);
    }

    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    @Override
    public void create(User entity) {
        super.create(entity);
    }

    @Override
    public void update(User entity) {
        super.update(entity);
    }

    @Override
    public boolean delete(String id) {
        return super.delete(id);
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public boolean loginValidate(String username, String password) {
        User user = findById(username);
        if (user.getUsername().equals(username) && user.getPassword().equals(password))
            return true;
        return false;
    }
}
