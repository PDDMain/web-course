package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.SessionAttribute;
import ru.itmo.wp.form.NoticeCredentials;

import javax.servlet.http.HttpSession;

@Component
public class NoticeCredentialsAddValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return NoticeCredentials.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            NoticeCredentials noticeCredentials = (NoticeCredentials) target;
            if (noticeCredentials.getContent().length() == 0) {
                errors.rejectValue("content", "content.is-empty", "no text");
            }
        }
    }
}
