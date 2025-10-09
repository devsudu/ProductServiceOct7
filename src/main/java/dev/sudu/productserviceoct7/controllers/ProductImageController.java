package dev.sudu.productserviceoct7.controllers;

import dev.sudu.productserviceoct7.dtos.ProductImageResponseDto;
import dev.sudu.productserviceoct7.exceptions.InvalidTokenException;
import dev.sudu.productserviceoct7.exceptions.ProductImageNotFoundException;
import dev.sudu.productserviceoct7.models.ProductImage;
import dev.sudu.productserviceoct7.services.interfaces.ProductImageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product_image")
@AllArgsConstructor
public class ProductImageController {
    private ProductImageService productImageService;

    @GetMapping("/imageId/{imageId}/token/{token}")
    public ResponseEntity<ProductImageResponseDto> getImageById(@PathVariable("token") String token, @PathVariable("imageId") Long imageId) throws InvalidTokenException, ProductImageNotFoundException {
        ProductImage productImage = productImageService.getImageById(token, imageId);
        return new ResponseEntity<>(
                ProductImageResponseDto.from(productImage),
                null,
                HttpStatus.OK
        );
    }

    @GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}/token/{token}")
    public ResponseEntity<Page<ProductImage>> getAllProductImages(@PathVariable("token") String token, @PathVariable("pageNumber") Integer pageNumber, @PathVariable("pageSize") Integer pageSize) throws InvalidTokenException {
        Page<ProductImage> productImages = productImageService.getAllProductImages(token, pageNumber, pageSize);
        return new ResponseEntity<>(
                productImages,
                null,
                HttpStatus.OK
        );
    }


}
