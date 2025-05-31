// package com.example.app.service;

// import com.example.app.model.Product;
// import com.example.app.util.ProductExcelUtil;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class ProductService {

//     public List<Product> getAllProducts() {
//         return ProductExcelUtil.readProducts();
//     }

//     public Product addProduct(Product product, Long userId) {
//         List<Product> products = ProductExcelUtil.readProducts();
//         Long newId = products.isEmpty() ? 1L : products.get(products.size() - 1).getId() + 1;
//         product.setId(newId);
//         product.setOwnerId(userId);
//         products.add(product);
//         ProductExcelUtil.writeAll(products); 
//         return product;
//     }
    
    
//     public Product updateProduct(Long id, Product updated, Long userId) {
//         List<Product> products = ProductExcelUtil.readProducts();
//         for (Product product : products) {
//             if (product.getId().equals(id) && product.getOwnerId().equals(userId)) {
//                 product.setName(updated.getName());
//                 product.setDescription(updated.getDescription());
//                 product.setPrice(updated.getPrice());
//                 ProductExcelUtil.writeAll(products); // 전체 다시 저장
//                 return product;
//             }
//         }
//         return null;
//     }
    
//     public boolean deleteProduct(Long id, Long userId) {
//         List<Product> products = ProductExcelUtil.readProducts();
//         boolean removed = products.removeIf(p -> p.getId().equals(id) && p.getOwnerId().equals(userId));
//         if (removed) {
//             ProductExcelUtil.writeAll(products); // 전체 다시 저장
//         }
//         return removed;
//     }
// }
