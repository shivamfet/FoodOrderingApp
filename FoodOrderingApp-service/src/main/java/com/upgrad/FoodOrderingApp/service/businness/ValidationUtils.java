package com.upgrad.FoodOrderingApp.service.businness;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.*;
import java.util.function.*;
import java.util.regex.Pattern;
import java.util.stream.*;

@Component
public class ValidationUtils {

    public boolean isEmailValid (String email) {
        //Code reference : https://www.geeksforgeeks.org/check-email-address-valid-not-java/
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    public boolean isPasswordValid(String password) {

        Character[] chars = {'#' , '@' , '$' , '%' , '&' , '*' , '!' , '^'};
        List<Character> specialChars = Arrays.asList(chars);

        boolean hasSpecialCharacter = specialChars.stream().anyMatch((character) -> password.indexOf(character) != -1);
        boolean hasEightChars = password.length() >= 8 ? true : false;
        boolean hasDigit = password.chars().mapToObj(c -> (char)c).anyMatch(ch -> Character.isDigit(ch));
        boolean hasUpperCaseLetter = password.chars().mapToObj(c -> (char)c).anyMatch(ch -> Character.isUpperCase(ch));

        return (hasSpecialCharacter && hasEightChars && hasDigit && hasUpperCaseLetter);
    }

    public boolean isContactNumberValid(String contactNumber) {
        boolean hasLengthEqualToTen = contactNumber.length() == 10 ? true : false;
        boolean hasAllDigits = contactNumber.chars().mapToObj(c -> (char)c).allMatch(ch -> Character.isDigit(ch));
        return (hasAllDigits && hasLengthEqualToTen);
    }

    public boolean isPincodeValid(String pincode) {
        boolean hasLengthEqualToSix = pincode.length() == 6 ? true : false;
        boolean hasAllDigits = pincode.chars().mapToObj(c -> (char)c).allMatch(ch -> Character.isDigit(ch));
        return (hasLengthEqualToSix && hasAllDigits);


    }
}
