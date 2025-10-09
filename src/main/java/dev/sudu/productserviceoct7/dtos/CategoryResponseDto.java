package dev.sudu.productserviceoct7.dtos;

import dev.sudu.productserviceoct7.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseDto {
    private Long categoryId;
    private String categoryName;
    private Long createdAt;
    private Long lastModifiedAt;

    public static CategoryResponseDto from(Category category) {
        if(category == null) return null;

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setCategoryId(category.getId());
        categoryResponseDto.setCategoryName(category.getName());
        categoryResponseDto.setCreatedAt(category.getCreatedAt());
        categoryResponseDto.setLastModifiedAt(category.getLastModifiedAt());
        return categoryResponseDto;
    }
}
