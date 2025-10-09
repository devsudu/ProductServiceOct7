package dev.sudu.productserviceoct7.dtos;

import dev.sudu.productserviceoct7.models.Category;
import dev.sudu.productserviceoct7.models.Product;
import dev.sudu.productserviceoct7.models.ProductImage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private Double mrp;
    private Double sellingPrice;
    private List<ProductImage> images;
    private Category category;
    private Long userId;
    private Boolean isActive;

    public static ProductResponseDto from(Product product) {
        if(product == null) return null;

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setName(product.getName());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setMrp(product.getMrp());
        productResponseDto.setSellingPrice(product.getSellingPrice());
        productResponseDto.setImages(product.getImages());
        productResponseDto.setCategory(product.getCategory());
        productResponseDto.setUserId(product.getUserId());
        productResponseDto.setIsActive(product.getIsActive());
        return productResponseDto;
    }
}
