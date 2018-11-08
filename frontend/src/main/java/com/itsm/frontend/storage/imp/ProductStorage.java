package com.itsm.frontend.storage.imp;

import com.itsm.common.entity.Product;
import com.itsm.frontend.storage.AbstractStorage;
import org.springframework.stereotype.Repository;

@Repository
public class ProductStorage extends AbstractStorage<Product> {

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }
}