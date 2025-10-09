package dev.sudu.productserviceoct7.services.interfaces;

import dev.sudu.productserviceoct7.exceptions.CategoryNotFoundException;
import dev.sudu.productserviceoct7.exceptions.InvalidTokenException;
import dev.sudu.productserviceoct7.exceptions.ProductAlreadyPresentException;
import dev.sudu.productserviceoct7.exceptions.ProductNotFoundException;
import dev.sudu.productserviceoct7.models.Category;
import dev.sudu.productserviceoct7.models.Product;
import dev.sudu.productserviceoct7.models.ProductImage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product createProduct(String token, String name, String description, Double mrp, Double sellingPrice, List<ProductImage> images, Category category) throws ProductAlreadyPresentException, InvalidTokenException;

    Product getProduct(String token, Long productId) throws ProductNotFoundException, InvalidTokenException;

    Product updateProduct(String token, Long productId, String name, String description, Double mrp, Double sellingPrice, List<ProductImage> images, Category category, Boolean isActive) throws ProductNotFoundException, InvalidTokenException, CategoryNotFoundException;

    Product updateProductName(String token, Long productId, String name) throws ProductNotFoundException, InvalidTokenException;

    Product updateProductDescription(String token, Long productId, String description) throws ProductNotFoundException, InvalidTokenException;

    Product updateProductMrp(String token, Long productId, Double mrp) throws ProductNotFoundException, InvalidTokenException;

    Product updateProductSellingPrice(String token, Long productId, Double sellingPrice) throws ProductNotFoundException, InvalidTokenException;

    Product updateProductMrpAndSellingPrice(String token, Long productId, Double mrp, Double sellingPrice) throws ProductNotFoundException, InvalidTokenException;

    Product updateProductImages(String token, Long productId, List<ProductImage> images) throws ProductNotFoundException, InvalidTokenException;

    Product replaceProductImage(String token, Long productId, Long imageId, ProductImage image) throws ProductNotFoundException, InvalidTokenException;

    Product updateProductCategory(String token, Long productId, Long categoryId) throws ProductNotFoundException, CategoryNotFoundException, InvalidTokenException;

    Product activateProduct(String token, Long productId, Boolean isActive) throws ProductNotFoundException, InvalidTokenException; // activate or deactivate

    Page<Product> getAllProductsByName(String token, String title, Integer PageNumber, Integer PageSize) throws InvalidTokenException;

    Page<Product> getAllProductsByNameAndCategory(String token, String title, Long categoryId, Integer PageNumber, Integer PageSize) throws InvalidTokenException;

    Page<Product> getAllProductsBySellingPrice(String token, Double sellingPrice, Integer PageNumber, Integer PageSize) throws InvalidTokenException;

    Page<Product> getAllInactiveProducts(String token, Boolean isActive, Integer PageNumber, Integer PageSize) throws InvalidTokenException;
}
