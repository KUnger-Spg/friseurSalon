package at.spengergasse.friseursalon.presentation.web;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.Year;

@ControllerAdvice(basePackageClasses = {GlobalWebControllerAdvice.class})

public class GlobalWebControllerAdvice {

    @Value("${app.copyright.msg:copyrighted}")
    private String  copyrightMessage;

    @ModelAttribute
    public void copyrightMessage(Model model) {
    model.addAttribute("copyrightMsg", copyrightMessage);
}

    @ModelAttribute
    public void copyrightYear (Model model) {
    model.addAttribute("copyrightYear", Year.now().getValue());
}
}
