package dev.sudu.productserviceoct7.services.impl;

import dev.sudu.productserviceoct7.commons.AuthCommons;
import dev.sudu.productserviceoct7.dtos.UserResponseDto;
import dev.sudu.productserviceoct7.exceptions.CategoryNotFoundException;
import dev.sudu.productserviceoct7.exceptions.InvalidTokenException;
import dev.sudu.productserviceoct7.exceptions.ProductAlreadyPresentException;
import dev.sudu.productserviceoct7.exceptions.ProductNotFoundException;
import dev.sudu.productserviceoct7.models.Category;
import dev.sudu.productserviceoct7.models.Product;
import dev.sudu.productserviceoct7.models.ProductImage;
import dev.sudu.productserviceoct7.repositories.CategoryRepository;
import dev.sudu.productserviceoct7.repositories.ProductRepository;
import dev.sudu.productserviceoct7.services.interfaces.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private AuthCommons authCommons;

    @Override
    public Product createProduct(String token, String name, String description, Double mrp, Double sellingPrice, List<ProductImage> images, Category category) throws ProductAlreadyPresentException, InvalidTokenException {
        // Todo authenticate user
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();
        if(userId == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Boolean isProductExists = isProductExistsByName(userId, name);
        if(isProductExists) throw new ProductAlreadyPresentException("Product with name "+name+" already present");

        List<Category> fetchedCategories = getCategoriesByName(userId, category.getName());
        Category savedCategory = null;
        if(fetchedCategories.isEmpty()){
            savedCategory = categoryRepository.save(Category.builder().name(category.getName()).setActive(false).userId(userId).build());
        }else{
            savedCategory = fetchedCategories.get(0);
        }
        return productRepository.save(Product.builder().name(name).description(description).mrp(mrp).sellingPrice(sellingPrice).images(images).category(savedCategory).isActive(false).userId(userId).build());
    }

    @Override
    public Product getProduct(String token, Long productId) throws ProductNotFoundException, InvalidTokenException {
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();
        Optional<Product> fetchedProduct = getProductById(userId, productId);
        if(fetchedProduct.isPresent()) return fetchedProduct.get();
        throw new ProductNotFoundException("Product with id "+productId+" not found");
    }

    @Override
    public Product updateProduct(String token, Long productId, String name, String description, Double mrp, Double sellingPrice, List<ProductImage> images, Category category, Boolean isActive) throws ProductNotFoundException, InvalidTokenException, CategoryNotFoundException {
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Optional<Product> fetchedProduct = getProductById(userId, productId);
        if(fetchedProduct.isEmpty()) throw new ProductNotFoundException("Product with id "+productId+" not found");
        Optional<Category> fetchedCategory = categoryRepository.findByIdAndUserId(category.getId(), userId);
        if(fetchedCategory.isEmpty()){
            throw new CategoryNotFoundException("Category with id +"+category.getId()+" does not exists");
        }
        Product productToSave = fetchedProduct.get();
        productToSave.setName(name);
        productToSave.setDescription(description);
        productToSave.setMrp(mrp);
        productToSave.setSellingPrice(sellingPrice);
        productToSave.setImages(images);
        productToSave.setIsActive(isActive);
        productToSave.setCategory(category);
        return productRepository.save(productToSave);
    }

    @Override
    public Product updateProductName(String token, Long productId, String name) throws ProductNotFoundException, InvalidTokenException {
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Optional<Product> fetchedProduct = getProductById(userId, productId);
        if(fetchedProduct.isEmpty()) throw new ProductNotFoundException("Product with id "+productId+" not found");
        Product productToSave = fetchedProduct.get();
        productToSave.setName(name);
        return productRepository.save(productToSave);
    }

    @Override
    public Product updateProductDescription(String token, Long productId, String description) throws ProductNotFoundException, InvalidTokenException {
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Optional<Product> fetchedProduct = getProductById(userId, productId);
        if(fetchedProduct.isEmpty()) throw new ProductNotFoundException("Product with id "+productId+" not found");
        Product productToSave = fetchedProduct.get();
        productToSave.setDescription(description);
        return productRepository.save(productToSave);
    }

    @Override
    public Product updateProductMrp(String token, Long productId, Double mrp) throws ProductNotFoundException, InvalidTokenException {
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Optional<Product> fetchedProduct = getProductById(userId, productId);
        if(fetchedProduct.isEmpty()) throw new ProductNotFoundException("Product with id "+productId+" not found");
        Product productToSave = fetchedProduct.get();
        if(productToSave.getSellingPrice() > mrp){
            throw new IllegalArgumentException("Selling price must be greater than or equal to MRP");
        }
        productToSave.setMrp(mrp);
        return productRepository.save(productToSave);
    }

    @Override
    public Product updateProductSellingPrice(String token, Long productId, Double sellingPrice) throws ProductNotFoundException, InvalidTokenException {
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Optional<Product> fetchedProduct = getProductById(userId, productId);
        if(fetchedProduct.isEmpty()) throw new ProductNotFoundException("Product with id "+productId+" not found");
        Product productToSave = fetchedProduct.get();
        if(sellingPrice > productToSave.getMrp()){
            throw new IllegalArgumentException("Selling price must be greater than or equal to MRP");
        }
        productToSave.setSellingPrice(sellingPrice);
        return productRepository.save(productToSave);
    }

    @Override
    public Product updateProductMrpAndSellingPrice(String token, Long productId, Double mrp, Double sellingPrice) throws ProductNotFoundException, InvalidTokenException {
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Optional<Product> fetchedProduct = getProductById(userId, productId);
        if(fetchedProduct.isEmpty()) throw new ProductNotFoundException("Product with id "+productId+" not found");
        Product productToSave = fetchedProduct.get();
        if(sellingPrice > mrp){
            throw new IllegalArgumentException("Selling price must be greater than or equal to MRP");
        }
        productToSave.setMrp(mrp);
        productToSave.setSellingPrice(sellingPrice);
        return productRepository.save(productToSave);
    }

    @Override
    public Product updateProductImages(String token, Long productId, List<ProductImage> images) throws ProductNotFoundException, InvalidTokenException {
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Optional<Product> fetchedProduct = getProductById(userId, productId);
        if(fetchedProduct.isEmpty()) throw new ProductNotFoundException("Product with id "+productId+" not found");
        Product productToSave = fetchedProduct.get();
        if(images.size() <= 0){
            throw new IllegalArgumentException("No Image received");
        }
        productToSave.setImages(images);
        return productRepository.save(productToSave);
    }

    @Override
    public Product replaceProductImage(String token, Long productId, Long imageId, ProductImage image) throws ProductNotFoundException, InvalidTokenException {
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

//        Optional<Product> fetchedProduct = getProductById(userId, productId);
//        if(fetchedProduct.isEmpty()) throw new ProductNotFoundException("Product with id "+productId+" not found");
//        Product productToSave = fetchedProduct.get();
//        return productRepository.save(productToSave);
        return null;
    }

    @Override
    public Product updateProductCategory(String token, Long productId, Long categoryId) throws ProductNotFoundException, CategoryNotFoundException, InvalidTokenException {
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Optional<Product> fetchedProduct = getProductById(userId, productId);
        if(fetchedProduct.isEmpty()) throw new ProductNotFoundException("Product with id "+productId+" not found");
        Product productToSave = fetchedProduct.get();
        Optional<Category> fetchedCategory = getCategoryById(userId, categoryId);
        if(fetchedCategory.isEmpty()) throw new CategoryNotFoundException("Category with id "+categoryId+" not found");
        productToSave.setCategory(fetchedCategory.get());
        return productRepository.save(productToSave);
    }

    @Override
    public Product activateProduct(String token, Long productId, Boolean isActive) throws ProductNotFoundException, InvalidTokenException {
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Optional<Product> fetchedProduct = getProductById(userId, productId);
        if(fetchedProduct.isEmpty()) throw new ProductNotFoundException("Product with id "+productId+" not found");
        Product productToSave = fetchedProduct.get();
        productToSave.setIsActive(isActive);
        return productRepository.save(productToSave);
    }

    @Override
    public Page<Product> getAllProductsByName(String token, String name, Integer PageNumber, Integer PageSize) throws InvalidTokenException {
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Pageable pageable = PageRequest.of(PageNumber, PageSize);
        if(name.isEmpty()){
            return productRepository.findAllProductsByUserId(userId, pageable);
        }
        return productRepository.findAllProductsByNameAndUserId(name, userId, pageable);
    }

    @Override
    public Page<Product> getAllProductsByNameAndCategory(String token, String productName, Long categoryId, Integer PageNumber, Integer PageSize) throws InvalidTokenException {
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Pageable pageable = PageRequest.of(PageNumber, PageSize);
        if(productName.isEmpty()){
            return productRepository.findAllProductsByUserIdAndCategoryId(userId, categoryId, pageable);
        }
        return productRepository.findAllProductsByUserIdAndNameAndCategoryId(userId, productName, categoryId, pageable);
    }

    @Override
    public Page<Product> getAllProductsBySellingPrice(String token, Double sellingPrice, Integer PageNumber, Integer PageSize) throws InvalidTokenException {
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Pageable pageable = PageRequest.of(PageNumber, PageSize);
        if(sellingPrice == null || sellingPrice <= 0){
            return productRepository.findAllProductsByUserId(userId, pageable);
        }
        return productRepository.findAllProductsByUserIdAndSellingPriceLessThanEqual(userId, sellingPrice, pageable);
    }

    @Override
    public Page<Product> getAllInactiveProducts(String token, Boolean isActive, Integer PageNumber, Integer PageSize) throws InvalidTokenException {
        UserResponseDto userResponseDto = verifyToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Pageable pageable = PageRequest.of(PageNumber, PageSize);
        return productRepository.findAllByUserIdAndIsActive(userId, isActive, pageable);
    }

    Optional<Product> getProductById(Long userId, Long productId) {
        return productRepository.findByIdAndUserId(productId, userId);
    }

    List<Product> getProductsByName(Long userId, String name) {
        return productRepository.findAllByNameAndUserId(name, userId);
    }

    Boolean isProductExistsById(Long userId, Long productId) {
        return getProductById(userId, productId).isPresent();
    }

    Boolean isProductExistsByName(Long userId, String name) {
        return !getProductsByName(userId, name).isEmpty();
    }

    Boolean isCategoryExistsById(Long userId, Long categoryId) {
        return getCategoryById(userId, categoryId).isPresent();
    }

    Optional<Category> getCategoryById(Long userId, Long categoryId) {
        return productRepository.findByCategoryIdAndUserId(categoryId, userId);
    }

    public UserResponseDto verifyToken(String token) throws InvalidTokenException {
        return authCommons.validateToken(token);
    }

    List<Category> getCategoriesByName(Long userId, String name) {
        return categoryRepository.findALlByNameAndUserId(name, userId);
    }
}
