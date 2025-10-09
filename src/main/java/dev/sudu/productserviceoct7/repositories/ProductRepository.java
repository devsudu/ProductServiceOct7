package dev.sudu.productserviceoct7.repositories;

import dev.sudu.productserviceoct7.models.Category;
import dev.sudu.productserviceoct7.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndUserId(Long productId, Long userId);

    List<Product> findAllByNameAndUserId(String name, Long userId);

    Optional<Category> findByCategoryIdAndUserId(Long categoryId, Long userId);

    Page<Product> findAllByUserIdAndIsActive(Long userId, Boolean isActive, Pageable pageable);

    Page<Product> findAllProductsByUserId(Long userId, Pageable pageable);

    Page<Product> findAllProductsByNameAndUserId(String name, Long userId, Pageable pageable);

    Page<Product> findAllProductsByUserIdAndCategoryId(Long userId, Long categoryId, Pageable pageable);

    Page<Product> findAllProductsByUserIdAndSellingPriceLessThanEqual(Long userId, Double sellingPriceIsLessThan, Pageable pageable);

    Page<Product> findAllProductsByUserIdAndNameAndCategoryId(Long userId, String name, Long categoryId, Pageable pageable);
}
