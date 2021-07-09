package pl.fopor.serwis.View;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewMessages {

    @GetMapping("/access_denied")
    public String showMessageAccessDanied() {
        return "messages/access_denied";
    }

    @GetMapping("/success")
    public String showMessageSuccess() {
        return "messages/success";
    }

    @GetMapping("/deleted")
    public String showMessageDeleted() {
        return "messages/deleted";
    }

    @GetMapping("/not_found")
    public String showMessageNotFound() {
        return "messages/not_found";
    }

    @GetMapping("/bad_request")
    public String showMessageBadRequest() {
        return "messages/bad_request";
    }
}
