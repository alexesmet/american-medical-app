package com.itsm.reporter.mappers;

import com.itsm.common.entity.Client;
import com.itsm.common.entity.Product;
import com.itsm.common.entity.Transaction;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TransactionMapper {
    @Select("SELECT * FROM transactions")
    @Results({
            @Result(column = "id", property = "id", id = true),
            @Result(column = "time", javaType = java.util.Date.class, property = "datetime"),
            @Result(column = "client_id", javaType = Client.class, property = "patient",
                    one = @One()), //TODO: Write a mapper
            @Result(column = "product_id", javaType = Product.class, property = "product"),

    })
    List<Transaction> getAll();

    //TODO: Add getBetweenDates()
}
