package vn.kms.fundamentals.basicwebapp.utils;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import vn.kms.fundamentals.basicwebapp.web.controller.Controller;
import vn.kms.fundamentals.basicwebapp.web.controller.HomeController;
import vn.kms.fundamentals.basicwebapp.web.controller.ProductDetailController;
import vn.kms.fundamentals.basicwebapp.web.controller.ProductListController;
import vn.kms.fundamentals.basicwebapp.web.listener.AppInitializer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ControllerManager {
    public static final String PATH_PARAMS_KEY = ControllerManager.class + "::PathParams";

    private final PathResolver pathResolver = new PathResolver();
    private Map<String, Controller> controllers;
    private TemplateEngine templateEngine;

    public void initialize(ServletContext servletContext) {
        registerControllers(servletContext);
        initTemplateEngine(servletContext);
    }

    public Controller resolveController(HttpServletRequest request) {
        String uriPath = getRequestPath(request);

        return controllers.keySet().stream()
            .filter(uriTemplate -> pathResolver.matchUri(uriTemplate, uriPath))
            .peek(uriTemplate -> {
                Map<String, String> pathParams = pathResolver.getParams(uriTemplate, uriPath);
                request.setAttribute(PATH_PARAMS_KEY, pathParams);
            })
            .map(controllers::get)
            .findFirst()
            .orElse(null);
    }

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    private void registerControllers(ServletContext servletContext) {
        TinyIoC ioc = (TinyIoC) servletContext.getAttribute(AppInitializer.IOC_ATTR_KEY);

        // TODO: should apply Annotation and Reflection to scan and register controllers
        controllers = new HashMap<>();
        controllers.put("/", ioc.get(HomeController.class));
        controllers.put("/products", ioc.get(ProductListController.class));
        controllers.put("/products/{productId}", ioc.get(ProductDetailController.class));
    }

    private void initTemplateEngine(ServletContext servletContext) {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");

        templateEngine = new TemplateEngine();
        templateEngine.addDialect(new LayoutDialect());
        templateEngine.setTemplateResolver(templateResolver);
    }

    private static String getRequestPath(final HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        final String contextPath = request.getContextPath();

        final int fragmentIndex = requestURI.indexOf(';');
        if (fragmentIndex != -1) {
            requestURI = requestURI.substring(0, fragmentIndex);
        }

        if (requestURI.startsWith(contextPath)) {
            return requestURI.substring(contextPath.length());
        }

        return requestURI;

    }
}
