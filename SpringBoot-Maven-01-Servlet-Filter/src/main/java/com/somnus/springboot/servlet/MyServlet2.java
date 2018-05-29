package com.somnus.springboot.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 在SpringBoot中通过注解注册的方式来注册Servlet
 */
@WebServlet(urlPatterns = "/servlet2")
public class MyServlet2 extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("-----------doGet----------------");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("-----------doPost----------------");
        resp.getWriter().print("<h1>Hello MyServlet2 Response return you this</h1>");
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}