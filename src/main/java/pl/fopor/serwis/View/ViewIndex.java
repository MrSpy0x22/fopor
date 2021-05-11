package pl.fopor.serwis.View;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewIndex {

    @RequestMapping("/")
    public String redirectToIndex() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }
}
