package com.vikings.GStock.controller;


import com.vikings.GStock.model.User;
import com.vikings.GStock.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    //Metodo para retornar o frontend do signup o html
    @GetMapping
    public String signupView() {return "signup";}

    //Metodo para gravar novo usuario
    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
        String signupError = null;

        //A condicao para verificar se o username existe ou nao
        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "O username nao esta disponivel.";
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupError = "Ocorreu um erro, por favor tente novamente.";
            } else {
                redirectAttributes.addFlashAttribute("signupSuccess", true);
                return "redirect:/login"; // Redirect to the login page with the success message.
            }
        } else {
            model.addAttribute("signupError", signupError);
        }

        return "signup"; // Return to the signup page with an error message if signup fails.
    }

}
