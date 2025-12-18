package de.holarse.web.services;

import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.web.controller.commands.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class RegisterFormValidationService {
    
    @Autowired
    private UserRepository userRepository;
    
    public void validate(final RegisterForm registerForm, final BindingResult result) {

        if (!registerForm.getPassword().equals(registerForm.getPasswordConfirmation())) {
            result.rejectValue("passwordConfirmation", "Die beiden Passwörter stimmen nicht überein.");
        }

        if (userRepository.findByLogin(registerForm.getUsername()) != null) {
            result.rejectValue("username", "", "Dieser Benutzername kann nicht verwendet werden.");
        }

        if (userRepository.findByEmail(registerForm.getEmail()) != null) {
            result.rejectValue("email", "", "Diese Email-Adresse kann nicht verwendet werden.");
        } 
    }
    
}
