package vn.kms.fundamentals.basicwebapp.web.controller;

import vn.kms.fundamentals.basicwebapp.utils.ViewModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    boolean isSecured();

    ViewModel process(HttpServletRequest request, HttpServletResponse response);
}
