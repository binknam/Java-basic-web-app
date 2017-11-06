package vn.kms.fundamentals.basicwebapp.utils;


import vn.kms.fundamentals.basicwebapp.repository.CategoryRepository;
import vn.kms.fundamentals.basicwebapp.repository.ProductRepository;
import vn.kms.fundamentals.basicwebapp.repository.impl.CategoryRepositoryImpl;
import vn.kms.fundamentals.basicwebapp.repository.impl.ProductRepositoryImpl;
import vn.kms.fundamentals.basicwebapp.repository.impl.UserRepositoryImpl;
import vn.kms.fundamentals.basicwebapp.service.ProductService;
import vn.kms.fundamentals.basicwebapp.web.controller.impl.*;

import java.util.HashMap;
import java.util.Map;

public class TinyIoC {
    private Map<Class, Object> managedObjects = new HashMap<>();

    public void initialize() {
        managedObjects.put(ProductRepository.class, new ProductRepositoryImpl());
        managedObjects.put(CategoryRepository.class, new CategoryRepositoryImpl());
        managedObjects.put(UserRepositoryImpl.class, new UserRepositoryImpl());

        managedObjects.put(ProductService.class, new ProductService(get(ProductRepository.class)));

        managedObjects.put(HomeController.class, new HomeController());
        managedObjects.put(ProductListController.class, new ProductListController(get(ProductRepository.class), get(CategoryRepository.class)));
        managedObjects.put(ProductDetailController.class, new ProductDetailController(get(ProductRepository.class), get(CategoryRepository.class)));
        managedObjects.put(ProductUpdateController.class, new ProductUpdateController(get(ProductRepository.class), get(CategoryRepository.class)));
        managedObjects.put(ProductCreateController.class, new ProductCreateController(get(ProductRepository.class), get(CategoryRepository.class)));
        managedObjects.put(ProductDeleteController.class, new ProductDeleteController(get(ProductRepository.class), get(CategoryRepository.class)));
        managedObjects.put(ProductSearchController.class, new ProductSearchController(get(ProductRepository.class), get(CategoryRepository.class)));
        managedObjects.put(UserLoginController.class, new UserLoginController(get(UserRepositoryImpl.class)));
    }

    public <T> T get(Class<T> type) {
        if (managedObjects.containsKey(type)) {
            return (T) managedObjects.get(type);
        }

        // find the first manageObject that instance of input type
        return (T) managedObjects.values().stream()
            .filter(managedObject -> type.isAssignableFrom(managedObject.getClass()))
            .findFirst()
            .orElseThrow(() -> new AppException("Could not found any managed object associated to " + type));
    }
}
