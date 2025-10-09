package dev.sudu.productserviceoct7.dtos;

import dev.sudu.productserviceoct7.models.Product;
import dev.sudu.productserviceoct7.models.ProductImage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageResponseDto {
    private String imageUrl;
    private Boolean isPrimary;
    private Long userId;
    private Boolean isActive;

    public static ProductImageResponseDto from(ProductImage productImage) {
        if(productImage == null) return null;

        ProductImageResponseDto productImageResponseDto = new ProductImageResponseDto();
        productImageResponseDto.setImageUrl(productImage.getImageUrl());
        productImageResponseDto.setIsPrimary(productImage.getIsPrimary());
        productImageResponseDto.setUserId(productImage.getUserId());
        productImageResponseDto.setIsActive(productImage.getIsActive());
        return productImageResponseDto;
    }
}
