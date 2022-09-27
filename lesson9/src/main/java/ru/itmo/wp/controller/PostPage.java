package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping("post/{stringId}")
    public String commentGet(@PathVariable String stringId, Model model) {
        try {
            Post post = postService.findById(Long.parseLong(stringId));
            model.addAttribute("post", post);
            if (post.getComments() != null) {
                model.addAttribute("comments", post.getComments());
            }
        } catch (NumberFormatException e) {
            // No action
        }
        model.addAttribute("comment", new Comment());
        return "PostPage";
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("post/{stringId}")
    public String commentPost(@PathVariable String stringId, @Valid @ModelAttribute("post") Comment comment,
                              BindingResult bindingResult,
                              HttpSession httpSession, Model model) {
        if (bindingResult.hasErrors()) {
            return "PostPage";
        }
        Post post = postService.findById(Long.parseLong(stringId));
        model.addAttribute("post", post);

        assert post != null;
        postService.writeComment(post, getUser(httpSession), comment);
        putMessage(httpSession, "You published new comment");

        return "redirect:/post/" + stringId;
    }
}
