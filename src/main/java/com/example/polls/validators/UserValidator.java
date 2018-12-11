package com.example.polls.validators;

import com.example.polls.payload.user.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    public static final int USERNAME_MAX_LIMIT = 50;

    private static final String USER_EMAIL_EMPTY = "user.email.empty";
    private static final String USER_USERNAME_EMPTY = "user.username.empty";
    private static final String USER_PASSWORD_EMPTY = "user.password.empty";
    private static final String USER_USERNAME_MAX_LIMIT = "user.username.max.length";
    private static final String USER_ROLES_EMPTY = "user.role.empty";
    private static final String USER_EMAIL_INVALID = "user.email.invalid";
    private static final String USER_PASSWORD_MAX_LENGTH = "user.password.size";


    @Override
    public boolean supports(Class<?> aClass) {

        return UserDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        UserDto user = (UserDto)o;

        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();
        Long id = user.getId();
        boolean isNew = id == null;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", USER_EMAIL_EMPTY, USER_EMAIL_EMPTY);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", USER_USERNAME_EMPTY, USER_USERNAME_EMPTY);
        if(isNew) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", USER_PASSWORD_EMPTY, USER_PASSWORD_EMPTY);
        }

        if(!StringUtils.isEmpty(username) && username.length() > USERNAME_MAX_LIMIT) {
            errors.rejectValue("username", USER_USERNAME_MAX_LIMIT, new Object[]{}, USER_USERNAME_MAX_LIMIT);
        }

        if(user.getRoles().size() < 0) {
            errors.rejectValue("username", USER_ROLES_EMPTY, new Object[]{}, USER_ROLES_EMPTY);
        }

        if(!StringUtils.isEmpty(email) && !email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
            errors.rejectValue("email",USER_EMAIL_INVALID, new Object[]{}, USER_EMAIL_INVALID);
        }

        if(isNew) {
            if (!StringUtils.isEmpty(password) && password.length() < 8) {
                errors.rejectValue("password", USER_PASSWORD_MAX_LENGTH, new Object[]{}, USER_PASSWORD_MAX_LENGTH);
            }
        }
    }
}
