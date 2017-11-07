package vn.kms.fundamentals.basicwebapp.web.controller.impl;

import vn.kms.fundamentals.basicwebapp.utils.ViewModel;
import vn.kms.fundamentals.basicwebapp.web.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

public class HomeController implements Controller {
    @Override
    public boolean isSecured(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null ;
    }

    @Override
    public ViewModel process(HttpServletRequest request, HttpServletResponse response) {
        return new ViewModel("home")
            .addModelAttribute("today", Calendar.getInstance());
    }
}
