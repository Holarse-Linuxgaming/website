package de.holarse.web.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.http.HttpMethod.GET;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {

    Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String registerForm() {
        return "register/form";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register() {
        logger.debug("Trying to register");

        return "register/done";
    }

}
