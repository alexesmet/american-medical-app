package com.itsm.reporter.mappers;

import com.itsm.common.entity.Client;
import com.itsm.common.entity.Product;
import com.itsm.common.entity.Transaction;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface TransactionMapper {
    @Select("SELECT * FROM transactions")
    @Results({
            @Result(column = "id", property = "id", id = true),
            @Result(column = "time", javaType = java.util.Date.class, property = "datetime"),
            @Result(column = "client_id", javaType = Client.class, property = "patient",
                    one = @One(select = "com.itsm.reporter.mappers.ClientMapper.get")),
            @Result(column = "product_id", javaType = Product.class, property = "product",
                    one = @One(select = "com.itsm.reporter.mappers.ProductMapper.get")),
    })
    List<Transaction> getAll();


    @Select("SELECT * FROM transactions WHERE time BETWEEN #{dateBegin} AND #{dateEnd}")
    @Results({
            @Result(column = "id", property = "id", id = true),
            @Result(column = "time", javaType = java.util.Date.class, property = "datetime"),
            @Result(column = "client_id", javaType = Client.class, property = "patient",
                    one = @One(select = "com.itsm.reporter.mappers.ClientMapper.get")),
            @Result(column = "product_id", javaType = Product.class, property = "product",
                    one = @One(select = "com.itsm.reporter.mappers.ProductMapper.get")),
    })
    List<Transaction> getAllInRange(@Param("dateBegin") Date dateBegin, @Param("dateEnd") Date dateEnd);
}
