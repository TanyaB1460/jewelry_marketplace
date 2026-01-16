package com.jewelry.service;
import com.jewelry.dao.ProductDao;
import com.jewelry.model.Product;
import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Product getProductById(Integer productId) throws SQLException {
        return productDao.findById(productId);
    }

    public List<Product> getAllProducts() throws SQLException {
        return productDao.findAll();
    }

    public List<Product> getProductsByMaker(Integer makerId) throws SQLException {
        return productDao.findByMakerId(makerId);
    }

    public void createProduct(Product product) throws SQLException {
        productDao.save(product);
    }

    public void updateProduct(Product product) throws SQLException {
        productDao.update(product);
    }

    public void deleteProduct(Integer productId) throws SQLException {
        productDao.delete(productId);
    }
}
