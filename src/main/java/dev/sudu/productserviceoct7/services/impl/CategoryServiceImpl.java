package dev.sudu.productserviceoct7.services.impl;

import dev.sudu.productserviceoct7.commons.AuthCommons;
import dev.sudu.productserviceoct7.dtos.UserResponseDto;
import dev.sudu.productserviceoct7.exceptions.CategoryAlreadyPresentException;
import dev.sudu.productserviceoct7.exceptions.CategoryNotFoundException;
import dev.sudu.productserviceoct7.exceptions.InvalidTokenException;
import dev.sudu.productserviceoct7.models.Category;
import dev.sudu.productserviceoct7.repositories.CategoryRepository;
import dev.sudu.productserviceoct7.services.interfaces.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private AuthCommons authCommons;

    @Override
    public Category createCategory(String token, String name) throws InvalidTokenException, CategoryAlreadyPresentException {
        UserResponseDto userResponseDto = authCommons.validateToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        List<Category> categories = getCategoriesByName(name, userId);
        if(!categories.isEmpty()){
            throw new CategoryAlreadyPresentException("Category with id "+categories.get(0).getName()+" already present");
        }
        return categoryRepository.save(Category.builder().name(name).userId(userId).build());
    }

    @Override
    public Category getCategory(String token, Long categoryId) throws InvalidTokenException, CategoryNotFoundException {
        UserResponseDto userResponseDto = authCommons.validateToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Optional<Category> fetchedCategory = categoryRepository.findById(categoryId);
        if(fetchedCategory.isPresent()){
            return fetchedCategory.get();
        }
        throw new CategoryNotFoundException("Category with id "+categoryId+" not found");
    }

    @Override
    public Category updateCategory(String token, Long categoryId, String name) throws InvalidTokenException, CategoryAlreadyPresentException, CategoryNotFoundException {
        UserResponseDto userResponseDto = authCommons.validateToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        List<Category> categories = getCategoriesByName(name, userId);
        if(!categories.isEmpty()){
            throw new CategoryAlreadyPresentException("Category with name "+name+" already present");
        }

        Optional<Category> fetchedCategory = getCategoryById(categoryId, userId);
        if(fetchedCategory.isEmpty()){
            throw new CategoryNotFoundException("Category with id "+categoryId+" does not exists");
        }
        return categoryRepository.save(Category.builder().id(categoryId).name(name).userId(userId).build());
    }

    @Override
    public Category deleteCategory(String token, Long categoryId) throws InvalidTokenException, CategoryNotFoundException {
        UserResponseDto userResponseDto = authCommons.validateToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Optional<Category> fetchedCategory = getCategoryById(categoryId, userId);
        if(fetchedCategory.isEmpty()){
            throw new CategoryNotFoundException("Category with id "+categoryId+" does not exists");
        }
        return categoryRepository.deleteByIdAndUserId(categoryId, userId);
    }

    @Override
    public Category activateCategory(String token, Long categoryId) throws InvalidTokenException, CategoryNotFoundException {
        UserResponseDto userResponseDto = authCommons.validateToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Optional<Category> fetchedCategory = getCategoryById(categoryId, userId);
        if(fetchedCategory.isEmpty()){
            throw new CategoryNotFoundException("Category with id "+categoryId+" does not exists");
        }
        return categoryRepository.save(Category.builder().id(categoryId).userId(userId).setActive(true).build());
    }

    @Override
    public Page<Category> getCategories(String token, Integer pageNumber, Integer pageSize) throws InvalidTokenException {
        UserResponseDto userResponseDto = authCommons.validateToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return categoryRepository.findAllByUserId(userId, pageable);
    }


    public List<Category> getCategoriesByName(String name, Long userId) {
        List<Category> categories = categoryRepository.findALlByNameAndUserId(name, userId);
        return categories;
    }

    public Boolean isCategoryPresent(String name, Long userId) {
        return !getCategoriesByName(name, userId).isEmpty();
    }

    public Optional<Category> getCategoryById(Long categoryId, Long userId) {
        return categoryRepository.findByIdAndUserId(categoryId, userId);
    }
}
