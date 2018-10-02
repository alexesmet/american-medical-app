package com.itsm.reporter.mappers;

import com.itsm.common.entity.State;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface StateMapper {
    @Select("SELECT * FROM states WHERE id=#{id}")
    @Results({
            @Result(column = "id", property = "id", javaType = long.class),
            @Result(column = "name", property = "name", javaType = String.class),
            @Result(column = "code", property = "code", javaType = int.class)
    })
    State get(long id);

}
