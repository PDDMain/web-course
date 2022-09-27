package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

public class CaptchaFilter extends HttpFilter {
    private static final String CAPTCHA_HTML = "/captcha.html";
    private static final String CAPTCHA_IMG = "/img/captcha.png";
    private static final String ABSOLUTE_CAPTCHA_IMG_PATH = "C:\\Users\\polzi\\Documents\\ИТМО\\2 курс\\Веб\\lesson3\\target\\wp31\\static\\img\\captcha.png";

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        super.doFilter(request, response, chain);

        HttpSession session = request.getSession();
        if (isCaptchaRequest(request) || session.getAttribute("captcha") != null) {
            super.doFilter(request, response, chain);
            return;
        }

        if (session.getAttribute("number") == null) {
            session.setAttribute("number", generateCaptchaImage());
        } else if (request.getParameter("number") != null) {
            if (session.getAttribute("number").equals(request.getParameter("number"))) {
                session.setAttribute("captcha", Boolean.TRUE);
                super.doFilter(request, response, chain);
                return;
            } else {
                session.setAttribute("number", generateCaptchaImage());
            }
        }
        response.setContentType("text/html");
        response.getOutputStream().write(new FileInputStream(getServletContext().getRealPath("/static" + CAPTCHA_HTML)).readAllBytes());
        response.getOutputStream().flush();
    }

    private boolean isCaptchaRequest(HttpServletRequest request) {
        switch (request.getServletPath()) {
            case CAPTCHA_HTML:
            case CAPTCHA_IMG:
                return true;
            default:
                return false;
        }
    }

    private String generateCaptchaImage() throws IOException {
        int rand = ((int) (Math.random() * 900)) + 100;
        OutputStream outputStream = new FileOutputStream(ABSOLUTE_CAPTCHA_IMG_PATH);
        outputStream.write(ImageUtils.toPng(Integer.toString(rand)));
        outputStream.close();
        return String.valueOf(rand);
    }
}
