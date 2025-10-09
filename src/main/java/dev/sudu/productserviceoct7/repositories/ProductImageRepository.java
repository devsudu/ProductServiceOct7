package dev.sudu.productserviceoct7.repositories;

import dev.sudu.productserviceoct7.models.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Optional<ProductImage> findByIdAndUserId(Long imageId, Long userId);

    Page<ProductImage> findAllByUserId(Long userId, Pageable pageable);
}
