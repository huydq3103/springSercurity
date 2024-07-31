package com.managedrink.controller;

import com.managedrink.until.constants.CommonConstant;
import com.managedrink.until.constants.MessageConstant;
import com.managedrink.until.constants.MessageSourceConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;
import java.util.Objects;

/**
 * controller quản lí việc thay đổi ngôn ngu
 */
@RestController
public class ChangeLanguageController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    /**
     * Thay đổi ngôn ngữ của ứng dụng dựa trên tham số 'lang' được gửi lên từ yêu cầu.
     * Nếu tham số 'lang' không được cung cấp, sẽ sử dụng ngôn ngữ hiện tại.
     *
     * @param request Đối tượng HttpServletRequest để lấy thông tin yêu cầu.
     * @param lang    Mã ngôn ngữ muốn thay đổi (ví dụ: 'en' cho tiếng Anh, 'vi' cho tiếng Việt).
     * @return Chuỗi thông báo chào hỏi tương ứng với ngôn ngữ đã được thay đổi.
     */
    @PostMapping("/changeLanguage")
    public String changeLanguage(HttpServletRequest request
            , @RequestParam(name = MessageSourceConstant.PARAM_NAME_LANG, required = false) String lang) {

        if (Objects.isNull(lang)) {
            Locale locale = Locale.forLanguageTag(lang);
            localeResolver.setLocale(request, null, locale);
        }
        Locale currentLocale = localeResolver.resolveLocale(request);
        return messageSource.getMessage(MessageConstant.LANGUAGE_UPDATED_SUCCESSFULLY, null, currentLocale);
    }
}
