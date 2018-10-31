package com.itsm.frontend.storage;

import com.itsm.common.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public class ProductStorage extends AbstractStorage<Product> {

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }
}