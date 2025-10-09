package dev.sudu.productserviceoct7.controllers;

import dev.sudu.productserviceoct7.dtos.ProductRequestDto;
import dev.sudu.productserviceoct7.dtos.ProductResponseDto;
import dev.sudu.productserviceoct7.exceptions.CategoryNotFoundException;
import dev.sudu.productserviceoct7.exceptions.InvalidTokenException;
import dev.sudu.productserviceoct7.exceptions.ProductAlreadyPresentException;
import dev.sudu.productserviceoct7.exceptions.ProductNotFoundException;
import dev.sudu.productserviceoct7.models.Product;
import dev.sudu.productserviceoct7.models.ProductImage;
import dev.sudu.productserviceoct7.services.interfaces.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestHeader HttpHeaders headers, @RequestBody ProductRequestDto productRequestDto) throws InvalidTokenException, ProductAlreadyPresentException {
        String token = headers.getFirst("token");
        Product product = productService.createProduct(token, productRequestDto.getName(), productRequestDto.getDescription(), productRequestDto.getMrp(), productRequestDto.getSellingPrice(), productRequestDto.getImages(), productRequestDto.getCategory());
        return new ResponseEntity<>(
                ProductResponseDto.from(product),
                null,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProduct(@RequestHeader HttpHeaders headers, @PathVariable("productId") Long productId) throws InvalidTokenException, ProductNotFoundException {
        System.out.println("=======--");
        String token = headers.getFirst("token");
        Product product = productService.getProduct(token, productId);
        return new ResponseEntity<>(
                ProductResponseDto.from(product),
                null,
                HttpStatus.OK
        );
    }

    @PatchMapping
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestHeader HttpHeaders headers, @RequestBody ProductRequestDto productRequestDto) throws InvalidTokenException, ProductNotFoundException, CategoryNotFoundException {
        String token = headers.getFirst("token");
        Product product = productService.updateProduct(token, productRequestDto.getId(), productRequestDto.getName(), productRequestDto.getDescription(), productRequestDto.getMrp(), productRequestDto.getSellingPrice(), productRequestDto.getImages(), productRequestDto.getCategory(), productRequestDto.getIsActive());
        return new ResponseEntity<>(
                ProductResponseDto.from(product),
                null,
                HttpStatus.ACCEPTED
        );
    }

    @PatchMapping("/{productId}/name/{name}")
    public ResponseEntity<ProductResponseDto> updateProductName(@RequestHeader HttpHeaders headers, @PathVariable("productId") Long productId, @PathVariable("name") String name) throws InvalidTokenException, ProductNotFoundException {
        String token = headers.getFirst("token");
        Product product = productService.updateProductName(token, productId, name);
        return new ResponseEntity<>(
                ProductResponseDto.from(product),
                null,
                HttpStatus.ACCEPTED
        );
    }

    @PatchMapping("/{productId}/description/{description}")
    public ResponseEntity<ProductResponseDto> updateProductDescription(@RequestHeader HttpHeaders headers, @PathVariable("productId") Long productId, @PathVariable("description") String description) throws InvalidTokenException, ProductNotFoundException {
        String token = headers.getFirst("token");
        Product product = productService.updateProductDescription(token, productId, description);
        return new ResponseEntity<>(
                ProductResponseDto.from(product),
                null,
                HttpStatus.ACCEPTED
        );
    }

    @PatchMapping("/{productId}/mrp/{mrp}")
    public ResponseEntity<ProductResponseDto> updateProductMrp(@RequestHeader HttpHeaders headers, @PathVariable("productId") Long productId, @PathVariable("mrp") Double mrp) throws InvalidTokenException, ProductNotFoundException {
        String token = headers.getFirst("token");
        Product product = productService.updateProductMrp(token, productId, mrp);
        return new ResponseEntity<>(
                ProductResponseDto.from(product),
                null,
                HttpStatus.ACCEPTED
        );
    }

    @PatchMapping("/{productId}/sellingPrice/{sellingPrice}")
    public ResponseEntity<ProductResponseDto> updateProductSellingPrice(@RequestHeader HttpHeaders headers, @PathVariable("productId") Long productId, @PathVariable("sellingPrice") Double sellingPrice) throws InvalidTokenException, ProductNotFoundException {
        String token = headers.getFirst("token");
        Product product = productService.updateProductSellingPrice(token, productId, sellingPrice);
        return new ResponseEntity<>(
                ProductResponseDto.from(product),
                null,
                HttpStatus.ACCEPTED
        );
    }

    @PatchMapping("/{productId}/mrp/{mrp}/sellingPrice/{sellingPrice}")
    public ResponseEntity<ProductResponseDto> updateProductMrpAndSellingPrice(@RequestHeader HttpHeaders headers, @PathVariable("productId") Long productId, @PathVariable("mrp") Double mrp, @PathVariable("sellingPrice") Double sellingPrice) throws InvalidTokenException, ProductNotFoundException {
        String token = headers.getFirst("token");
        Product product = productService.updateProductMrpAndSellingPrice(token, productId, mrp, sellingPrice);
        return new ResponseEntity<>(
                ProductResponseDto.from(product),
                null,
                HttpStatus.ACCEPTED
        );
    }

    public ResponseEntity<ProductResponseDto> updateProductImages(@RequestHeader HttpHeaders headers, @PathVariable("productId") Long productId, List<ProductImage> images) throws InvalidTokenException, ProductNotFoundException {
        String token = headers.getFirst("token");
        Product product = productService.updateProductImages(token, productId, images);
        return new ResponseEntity<>(
                ProductResponseDto.from(product),
                null,
                HttpStatus.ACCEPTED
        );
    }

    public ResponseEntity<ProductResponseDto> replaceProductImage(@RequestHeader HttpHeaders headers, @PathVariable("productId") Long productId, Long imageId, ProductImage image) throws InvalidTokenException, ProductNotFoundException {
        String token = headers.getFirst("token");
        Product product = productService.replaceProductImage(token, productId, imageId, image);
        return new ResponseEntity<>(
                ProductResponseDto.from(product),
                null,
                HttpStatus.ACCEPTED
        );
    }

    @PatchMapping("/{productId}/categoryId/{categoryId}")
    public ResponseEntity<ProductResponseDto> updateProductCategory(@RequestHeader HttpHeaders headers, @PathVariable("productId") Long productId, @PathVariable("categoryId") Long categoryId) throws InvalidTokenException, CategoryNotFoundException, ProductNotFoundException {
        String token = headers.getFirst("token");
        Product product = productService.updateProductCategory(token, productId, categoryId);
        return new ResponseEntity<>(
                ProductResponseDto.from(product),
                null,
                HttpStatus.ACCEPTED
        );
    }

    @PatchMapping("/{productId}/isActive/{isActive}")
    public ResponseEntity<ProductResponseDto> activateProduct(@RequestHeader HttpHeaders headers, @PathVariable("productId") Long productId, @PathVariable("isActive") Boolean isActive) throws InvalidTokenException, ProductNotFoundException { // activate or deactivate
        String token = headers.getFirst("token");
        Product product = productService.activateProduct(token, productId, isActive);
        return new ResponseEntity<>(
                ProductResponseDto.from(product),
                null,
                HttpStatus.OK
        );
    }

    @GetMapping("/name/{name}/pageNumber/{pageNumber}/pageSize/{pageSize}")
    ResponseEntity<Page<Product>> getAllProductsByName(@RequestHeader HttpHeaders headers, @PathVariable("name") String name, @PathVariable("pageNumber") Integer PageNumber, @PathVariable("pageSize") Integer pageSize) throws InvalidTokenException {
        String token = headers.getFirst("token");
        Page<Product> products = productService.getAllProductsByName(token, name, PageNumber, pageSize);
        return new ResponseEntity<>(
                products,
                null,
                HttpStatus.OK
        );
    }

    @GetMapping("/name/{name}/categoryId/{categoryId}/pageNumber/{pageNumber}/pageSize/{pageSize}")
    ResponseEntity<Page<Product>> getAllProductsByNameAndCategory(@RequestHeader HttpHeaders headers, @PathVariable("name") String name, @PathVariable("categoryId") Long categoryId, @PathVariable("pageNumber") Integer PageNumber, @PathVariable("pageSize") Integer pageSize) throws InvalidTokenException {
        String token = headers.getFirst("token");
        Page<Product> products = productService.getAllProductsByNameAndCategory(token, name, categoryId, PageNumber, pageSize);
        return new ResponseEntity<>(
                products,
                null,
                HttpStatus.OK
        );
    }

    @GetMapping("/sellingPrice/{sellingPrice}/pageNumber/{pageNumber}/pageSize/{pageSize}")
    ResponseEntity<Page<Product>> getAllProductsBySellingPrice(@RequestHeader HttpHeaders headers, @PathVariable("sellingPrice") Double sellingPrice, @PathVariable("pageNumber") Integer PageNumber, @PathVariable("pageSize") Integer pageSize) throws InvalidTokenException {
        String token = headers.getFirst("token");
        Page<Product> products = productService.getAllProductsBySellingPrice(token, sellingPrice, PageNumber, pageSize);
        return new ResponseEntity<>(
                products,
                null,
                HttpStatus.OK
        );
    }

    @GetMapping("/isActive/{isActive}/pageNumber/{pageNumber}/pageSize/{pageSize}")
    ResponseEntity<Page<Product>> getAllInactiveProducts(@RequestHeader HttpHeaders headers, @PathVariable("isActive") Boolean isActive, @PathVariable("pageNumber") Integer PageNumber, @PathVariable("pageSize") Integer pageSize) throws InvalidTokenException {
        String token = headers.getFirst("token");
        Page<Product> products = productService.getAllInactiveProducts(token, isActive, PageNumber, pageSize);
        return new ResponseEntity<>(
                products,
                null,
                HttpStatus.OK
        );
    }
}
