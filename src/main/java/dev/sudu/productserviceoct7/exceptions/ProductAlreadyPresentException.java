package dev.sudu.productserviceoct7.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAlreadyPresentException extends Exception {
    public ProductAlreadyPresentException(String message) {
        super(message);
    }
}
