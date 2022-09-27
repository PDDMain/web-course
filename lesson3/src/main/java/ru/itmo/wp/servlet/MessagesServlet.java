package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MessagesServlet extends HttpServlet {
    List<Map<String, String>> list = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        switch (request.getRequestURI()) {
            case "/message/auth":
                doPostAuth(request, response, session);
                break;
            case "/message/findAll":
                doPostFindAll(response);
                break;
            case "/message/add":
                doPostAdd(request, session);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void doPostAuth(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        if (session.getAttribute("user") == null) {
            String user = request.getParameter("user");
            session.setAttribute("user", user);
            printJSON(response, user);
        } else {
            printJSON(response, session.getAttribute("user"));
        }
    }


    private void doPostFindAll(HttpServletResponse response) throws IOException {
        printJSON(response, list);
    }

    private void doPostAdd(HttpServletRequest request, HttpSession session) {
        Map<String, String> map = new HashMap<>();
        map.put("user", (String) session.getAttribute("user"));
        map.put("text", request.getParameter("text"));
        list.add(map);
    }

    private void printJSON(HttpServletResponse response, Object object) throws IOException {
        String json = new Gson().toJson(object);
        response.getOutputStream().write(json.getBytes(UTF_8));
        response.getWriter().flush();
    }
}
