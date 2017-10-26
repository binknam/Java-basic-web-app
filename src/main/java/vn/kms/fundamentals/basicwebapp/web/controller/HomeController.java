package vn.kms.fundamentals.basicwebapp.web.controller;

import vn.kms.fundamentals.basicwebapp.utils.ViewModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

public class HomeController implements Controller {
    @Override
    public boolean isSecured() {
        return false;
    }

    @Override
    public ViewModel process(HttpServletRequest request, HttpServletResponse response) {
        return new ViewModel("home")
            .addModelAttribute("today", Calendar.getInstance());
    }
}
