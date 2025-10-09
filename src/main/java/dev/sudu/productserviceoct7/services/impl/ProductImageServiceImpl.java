package dev.sudu.productserviceoct7.services.impl;

import dev.sudu.productserviceoct7.commons.AuthCommons;
import dev.sudu.productserviceoct7.dtos.UserResponseDto;
import dev.sudu.productserviceoct7.exceptions.InvalidTokenException;
import dev.sudu.productserviceoct7.exceptions.ProductImageNotFoundException;
import dev.sudu.productserviceoct7.models.ProductImage;
import dev.sudu.productserviceoct7.repositories.ProductImageRepository;
import dev.sudu.productserviceoct7.services.interfaces.ProductImageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {
    private ProductImageRepository productImageRepository;
    private AuthCommons authCommons;

    @Override
    public ProductImage getImageById(String token, Long imageId) throws InvalidTokenException, ProductImageNotFoundException {
        UserResponseDto userResponseDto = authCommons.validateToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Optional<ProductImage> fetchedProductImage = fetchByImageIdAndUserId(imageId, userId);
        if(fetchedProductImage.isPresent()){
            return fetchedProductImage.get();
        }
        throw new ProductImageNotFoundException("Product image with id "+imageId+" not found");
    }

    @Override
    public Page<ProductImage> getAllProductImages(String token, Integer pageNumber, Integer pageSize) throws InvalidTokenException {
        UserResponseDto userResponseDto = authCommons.validateToken(token);
        if(userResponseDto == null){
            throw new InvalidTokenException("Invalid token, pls login");
        }
        Long userId = userResponseDto.getUserId();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productImageRepository.findAllByUserId(userId, pageable);
    }

    public Optional<ProductImage> fetchByImageIdAndUserId(Long imageId, Long userId) {
        return productImageRepository.findByIdAndUserId(imageId, userId);
    }
}
