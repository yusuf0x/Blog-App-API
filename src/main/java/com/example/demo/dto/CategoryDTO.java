package com.example.demo.dto;
import com.example.demo.models.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    public static Category toEntity(CategoryDTO categoryDTO){
        if(categoryDTO == null){
            return null;
        }
        Category category1 = new Category();
        category1.setId(categoryDTO.getId());
        category1.setTitle(categoryDTO.getTitle());
        category1.setDescription(categoryDTO.getDescription());
        return category1;
    }
    public static CategoryDTO fromEntity(Category category){
        if(category == null){
            return null;
        }
        return CategoryDTO.builder()
                .id(category.getId())
                .description(category.getDescription())
                .title(category.getTitle())
                .build();
    }

}
