package com.itsm.reporter.mappers;

import com.itsm.common.entity.State;
import org.apache.ibatis.annotations.Select;

public interface StateMapper {
    @Select("SELECT * FROM states WHERE id=#{id}")
    State get(long id);

}
