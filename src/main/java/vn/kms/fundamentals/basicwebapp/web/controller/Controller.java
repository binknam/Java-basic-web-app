package vn.kms.fundamentals.basicwebapp.web.controller;

import vn.kms.fundamentals.basicwebapp.utils.ViewModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Controller {
    boolean isSecured(HttpServletRequest request);

    ViewModel process(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
