package vn.kms.fundamentals.basicwebapp.web.filter;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import vn.kms.fundamentals.basicwebapp.utils.AppException;
import vn.kms.fundamentals.basicwebapp.utils.ControllerManager;
import vn.kms.fundamentals.basicwebapp.utils.ViewModel;
import vn.kms.fundamentals.basicwebapp.web.controller.Controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static vn.kms.fundamentals.basicwebapp.web.listener.AppInitializer.CONTROLLER_MNG_ATTR_KEY;

@WebFilter(urlPatterns = "/*")
public class AppFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // no nothing
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // ignore static files
        if (isStaticFileRequest(request.getRequestURI())) {
            chain.doFilter(req, res);
            return;
        }

        ControllerManager controllerManager = getControllerManager(request);

        Controller controller = controllerManager.resolveController(request);
        if (controller == null) {
            chain.doFilter(req, res);
            return;
        }

        if (controller.isSecured()) {
            // TODO: Check authentication here. Don't forget to redirect to login page if unauthorized
        }
        handleController(controller, controllerManager.getTemplateEngine(), request, response);
    }

    @Override
    public void destroy() {
        // do nothing
    }

    private ControllerManager getControllerManager(HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();

        return (ControllerManager) servletContext.getAttribute(CONTROLLER_MNG_ATTR_KEY);
    }

    private void handleController(Controller controller, TemplateEngine templateEngine,
                                  HttpServletRequest request, HttpServletResponse response) {
        try {
            ViewModel viewModel = controller.process(request, response);

            WebContext ctx = new WebContext(request, response, request.getServletContext());
            ctx.setVariables(viewModel.getModel());
            templateEngine.process(viewModel.getViewName(), ctx, response.getWriter());
        } catch (Exception ex) {
            throw new AppException("Could not process the request " + request.getRequestURI(), ex);
        }
    }

    private boolean isStaticFileRequest(String requestUri) {
        return requestUri.startsWith("/css") ||
            requestUri.startsWith("/images") ||
            requestUri.startsWith("/js") ||
            requestUri.startsWith("/vendor") ||
            requestUri.startsWith("/favicon");
    }
}
