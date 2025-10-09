package dev.sudu.productserviceoct7.controllers;

import dev.sudu.productserviceoct7.commons.AuthCommons;
import dev.sudu.productserviceoct7.dtos.CategoryResponseDto;
import dev.sudu.productserviceoct7.exceptions.CategoryAlreadyPresentException;
import dev.sudu.productserviceoct7.exceptions.CategoryNotFoundException;
import dev.sudu.productserviceoct7.exceptions.InvalidTokenException;
import dev.sudu.productserviceoct7.models.Category;
import dev.sudu.productserviceoct7.services.interfaces.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    private CategoryService categoryService;
    private AuthCommons authCommons;

    @PostMapping("/name/{name}/{token}")
    public ResponseEntity<CategoryResponseDto> createCategory(@PathVariable("token") String token, @PathVariable("name") String name) throws InvalidTokenException, CategoryAlreadyPresentException {
        Category category = categoryService.createCategory(token, name);
        return new ResponseEntity<>(
                CategoryResponseDto.from(category),
                null,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/categoryId/{categoryId}/{token}")
    public ResponseEntity<CategoryResponseDto>  getCategory(@PathVariable("token") String token, @PathVariable("categoryId") Long categoryId) throws InvalidTokenException, CategoryNotFoundException {
        Category category = categoryService.getCategory(token, categoryId);
        return new ResponseEntity<>(
                CategoryResponseDto.from(category),
                null,
                HttpStatus.ACCEPTED
        );
    }

    @PatchMapping("/categoryId/{categoryId}/name/{name}/{token}")
    public ResponseEntity<CategoryResponseDto>  updateCategory(@PathVariable("token") String token, @PathVariable("categoryId") Long categoryId, @PathVariable("name") String name) throws InvalidTokenException, CategoryAlreadyPresentException, CategoryNotFoundException {
        Category category = categoryService.updateCategory(token, categoryId, name);
        return new ResponseEntity<>(
                CategoryResponseDto.from(category),
                null,
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping("/categoryId/{categoryId}/{token}")
    public ResponseEntity<CategoryResponseDto>  deleteCategory(@PathVariable("token") String token, @PathVariable("categoryId") Long categoryId) throws InvalidTokenException, CategoryNotFoundException {
        Category category = categoryService.deleteCategory(token, categoryId);
        return new ResponseEntity<>(
                CategoryResponseDto.from(category),
                null,
                HttpStatus.ACCEPTED
        );
    }

    @PatchMapping("/activate/categoryId/{categoryId}/{token}")
    public ResponseEntity<CategoryResponseDto>  activateCategory(@PathVariable("token") String token, @PathVariable("categoryId") Long categoryId) throws InvalidTokenException, CategoryNotFoundException {
        Category category = categoryService.activateCategory(token, categoryId);
        return new ResponseEntity<>(
                CategoryResponseDto.from(category),
                null,
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}/{token}")
    public ResponseEntity<Page<Category>>  getCategories(@PathVariable("token") String token, @PathVariable("pageNumber") Integer pageNumber, @PathVariable("pageSize") Integer pageSize) throws InvalidTokenException {
        Page<Category> categories = categoryService.getCategories(token, pageNumber, pageSize);
        return new ResponseEntity<>(
                categories,
                null,
                HttpStatus.OK
        );
    }
}
