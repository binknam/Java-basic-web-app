package vn.kms.fundamentals.basicwebapp.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class ViewModel {
    private final String viewName;
    private final Map<String, Object> model = new LinkedHashMap<>();

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public ViewModel addModelAttribute(String name, Object value) {
        model.put(name, value);
        return this;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
