package com.itsm.reporter.transaction;

import com.itsm.common.entity.AuditOperation;
import com.itsm.common.entity.State;
import com.itsm.reporter.mappers.AuditOperationMapper;
import com.itsm.reporter.mappers.StateMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession();
        StateMapper mapper = session.getMapper(StateMapper.class);

        System.out.println(mapper.get(3));



        session.close();


    }
}
