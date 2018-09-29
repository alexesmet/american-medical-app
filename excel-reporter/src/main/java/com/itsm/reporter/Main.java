package com.itsm.reporter;

import com.itsm.common.entity.AuditOperation;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm");

    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession();
        AuditOperationMapper mapper = session.getMapper(AuditOperationMapper.class);
        List<AuditOperation> auditOperations = mapper.getAll();

        WorkbookFactory workbookFactory = new WorkbookFactory(dateFormat);
        Workbook book = workbookFactory.generate(auditOperations);

        book.write(new FileOutputStream("reports/excel/" + dateFormat.format(new Date()) + ".xls"));
        book.close();

    }
}
