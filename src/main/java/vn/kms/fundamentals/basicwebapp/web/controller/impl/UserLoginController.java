package vn.kms.fundamentals.basicwebapp.web.controller.impl;

import vn.kms.fundamentals.basicwebapp.repository.UserRepository;
import vn.kms.fundamentals.basicwebapp.utils.ViewModel;
import vn.kms.fundamentals.basicwebapp.web.controller.Controller;
import vn.kms.fundamentals.basicwebapp.web.listener.SessionListener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserLoginController implements Controller {

    private final UserRepository userRepo;

    public static boolean secured = false;

    public UserLoginController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean isSecured() {
        return secured;
    }

    @Override
    public ViewModel process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SessionListener sessionListener = new SessionListener();
        if (request.getMethod().equalsIgnoreCase("POST")) {
            String username = request.getParameter("USERNAME");
            String password = request.getParameter("PASSWORD");
            if (userRepo.loginValidate(username, password)) {
                response.sendRedirect("/products");
                secured = true;
                return new ViewModel("product/list");
            }
        }
        return new ViewModel("user/signin");
    }
}
