package dev.sudu.productserviceoct7.services.interfaces;

import dev.sudu.productserviceoct7.exceptions.InvalidTokenException;
import dev.sudu.productserviceoct7.exceptions.ProductImageNotFoundException;
import dev.sudu.productserviceoct7.models.ProductImage;
import org.springframework.data.domain.Page;

public interface ProductImageService {

    ProductImage getImageById(String token, Long imageId) throws InvalidTokenException, ProductImageNotFoundException;

//    ProductImage updatePrimaryImgStatusOfProduct(String token, Long imageId) throws InvalidTokenException, ProductImageNotFoundException;

//    ProductImage activateProductImage(String token, Long imageId) throws InvalidTokenException, ProductImageNotFoundException;

    Page<ProductImage> getAllProductImages(String token, Integer pageNumber, Integer pageSize) throws InvalidTokenException;

//    Page<ProductImage> getAllPrimaryProductImages(String token, Pageable pageable);
}
