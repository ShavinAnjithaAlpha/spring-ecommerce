package org.shavin.ecommerce.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.shavin.ecommerce.exception.ProductPurchaseException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final Productrepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest request) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();

    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
            var productIds = request.stream()
                    .map(ProductPurchaseRequest::productId)
                    .toList();
            var storedProduct = repository.findAllByIdInOrderById(productIds);
            if (productIds.size() != storedProduct.size()) {
                throw new ProductPurchaseException("One or more prodict does not exists");
            }

            var storedRequests = request
                    .stream()
                    .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                    .toList();

            var purchasingProducts = new ArrayList<ProductPurchaseResponse>();
            for (int i = 0; i < storedProduct.size(); i++) {
                var product  = storedProduct.get(i);
                var productRequest = storedRequests.get(i);
                if (product.getAvailableQuantity() < productRequest.quantity()) {
                    throw new ProductPurchaseException("Insufficient quantity for product with product id " + product.getId());
                }

                var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
                product.setAvailableQuantity(newAvailableQuantity);
                repository.save(product);

                purchasingProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
            }

            return purchasingProducts;
    }

    public ProductResponse findById(Integer productId) {
        return repository.findById(productId).map(mapper::fromProduct)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + productId));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromProduct)
                .collect(Collectors.toList());
    }
}
