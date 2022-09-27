package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;

    public NoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/notice")
    public String saveNoticeGet(Model model) {
        model.addAttribute("noticeForm", new NoticeCredentials());
        return "NoticePage";
    }

    @PostMapping("/notice")
    public String saveNoticePost(@Valid @ModelAttribute("noticeForm") NoticeCredentials noticeForm,
                                 BindingResult bindingResult,
                                 HttpSession httpSession, Model model) {
        if (!bindingResult.hasErrors() && model.getAttribute("user") == null) {
            bindingResult.addError(new FieldError("content", "content.no-user", "you must be login"));
        }
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }
        noticeService.save(noticeForm);
        putMessage(httpSession, "Congrats, notice have been saved!");

        return "redirect:";
    }
}
