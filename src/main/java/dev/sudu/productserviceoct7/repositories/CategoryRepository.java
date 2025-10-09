package dev.sudu.productserviceoct7.repositories;

import dev.sudu.productserviceoct7.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findALlByNameAndUserId(String name, Long userId);

    Optional<Category> findByIdAndUserId(Long id, Long userId);

    Category deleteByIdAndUserId(Long categoryId, Long userId);

    Page<Category> findAllByUserId(Long userId, Pageable pageable);
}
