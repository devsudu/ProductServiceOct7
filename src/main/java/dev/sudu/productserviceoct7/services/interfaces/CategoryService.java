package dev.sudu.productserviceoct7.services.interfaces;

import dev.sudu.productserviceoct7.exceptions.CategoryAlreadyPresentException;
import dev.sudu.productserviceoct7.exceptions.CategoryNotFoundException;
import dev.sudu.productserviceoct7.exceptions.InvalidTokenException;
import dev.sudu.productserviceoct7.models.Category;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Category createCategory(String token, String name) throws InvalidTokenException, CategoryAlreadyPresentException;

    Category getCategory(String token, Long categoryId) throws InvalidTokenException, CategoryNotFoundException;

    Category updateCategory(String token, Long categoryId, String name) throws InvalidTokenException, CategoryAlreadyPresentException, CategoryNotFoundException;

    Category deleteCategory(String token, Long categoryId) throws InvalidTokenException, CategoryNotFoundException;

    Category activateCategory(String token, Long categoryId) throws InvalidTokenException, CategoryNotFoundException;

    Page<Category> getCategories(String token, Integer pageNumber, Integer pageSize) throws InvalidTokenException;
}
