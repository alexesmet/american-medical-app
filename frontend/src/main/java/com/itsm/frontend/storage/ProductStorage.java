package com.itsm.frontend.storage;

import com.itsm.common.entity.Product;
import com.itsm.common.entity.State;
import com.itsm.frontend.auditor.Auditable;
import jdk.packager.services.userjvmoptions.PreferencesUserJvmOptions;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
@Repository
public class ProductStorage extends AbstractStorage<Product> {

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }
}