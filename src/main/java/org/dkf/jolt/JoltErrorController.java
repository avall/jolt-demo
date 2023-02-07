package org.dkf.jolt;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class JoltErrorController implements ErrorController {

    // unused for now
    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }
}