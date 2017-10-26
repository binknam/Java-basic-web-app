package vn.kms.fundamentals.basicwebapp.utils;

import vn.kms.fundamentals.basicwebapp.repository.ProductRepository;
import vn.kms.fundamentals.basicwebapp.repository.impl.ProductRepositoryImpl;
import vn.kms.fundamentals.basicwebapp.service.ProductService;
import vn.kms.fundamentals.basicwebapp.web.controller.HomeController;
import vn.kms.fundamentals.basicwebapp.web.controller.ProductDetailController;
import vn.kms.fundamentals.basicwebapp.web.controller.ProductListController;

import java.util.HashMap;
import java.util.Map;

public class TinyIoC {
    private Map<Class, Object> managedObjects = new HashMap<>();

    public void initialize() {
        managedObjects.put(ProductRepository.class, new ProductRepositoryImpl());

        managedObjects.put(ProductService.class, new ProductService(get(ProductRepository.class)));

        managedObjects.put(HomeController.class, new HomeController());
        managedObjects.put(ProductListController.class, new ProductListController(get(ProductRepository.class)));
        managedObjects.put(ProductDetailController.class, new ProductDetailController(get(ProductRepository.class)));
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
