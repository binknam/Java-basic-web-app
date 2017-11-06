package vn.kms.fundamentals.basicwebapp.repository;

import vn.kms.fundamentals.basicwebapp.model.User;

public interface UserRepository extends GenericRepository<User, String> {
    boolean loginValidate(String username, String password);
}
