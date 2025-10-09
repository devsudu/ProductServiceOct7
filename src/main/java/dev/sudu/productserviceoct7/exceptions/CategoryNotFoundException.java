package dev.sudu.productserviceoct7.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
