package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmo.wp.exceptions.UserIdException;
import ru.itmo.wp.service.UserService;


@RequestMapping("user")
@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{stringId}")
    public String user(@PathVariable String stringId, Model model) throws UserIdException {
        try {
            Long id = Long.parseLong(stringId);
            Object userId = userService.findById(id);
            if (userId != null) {
                model.addAttribute("userId", userService.findById(id));
            }
        } catch (NumberFormatException e) {
            // No action
        }
        return "UserPage";
    }
}
