package com.itsm.reporter.mappers;

import com.itsm.common.entity.Product;
import com.itsm.common.entity.State;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface ProductMapper {
    @Select("SELECT * FROM products WHERE id=#{id}")
    @Results({
            @Result(column = "id", property = "id", id = true),
            @Result(column = "name", property = "name"),
            @Result(column = "state_id", property = "state", javaType = State.class,
                    one = @One(select = "com.itsm.reporter.mappers.StateMapper.get"))
    })
    Product get(long id);
}
