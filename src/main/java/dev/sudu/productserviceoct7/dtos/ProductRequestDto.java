package dev.sudu.productserviceoct7.dtos;

import dev.sudu.productserviceoct7.models.Category;
import dev.sudu.productserviceoct7.models.ProductImage;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRequestDto {
    private Long id;
    private String name;
    private String description;
    private Double mrp;
    private Double sellingPrice;
    private List<ProductImage> images;
    private Category category;
    private Boolean isActive;
}
