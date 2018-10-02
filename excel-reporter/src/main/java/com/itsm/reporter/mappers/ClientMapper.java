package com.itsm.reporter.mappers;

import com.itsm.common.entity.Client;
import com.itsm.common.entity.State;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface ClientMapper {

    @Select("SELECT * FROM clients WHERE id = #{id}")
    @Results({
            @Result(column = "id", property = "id", id = true),
            @Result(column = "name", property = "name"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "state_id", property = "state", javaType = State.class,
                    one = @One(select = "com.itsm.reporter.mappers.StateMapper.get"))
    })
    Client get(long id);


}
