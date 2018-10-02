package com.itsm.reporter.operation;

import com.itsm.common.entity.AuditOperation;
import com.itsm.reporter.mappers.AuditOperationMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

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
        session.close();

        Workbook book = generate(auditOperations);

        book.write(new FileOutputStream("reports/excel/audit-operation/" + dateFormat.format(new Date()) + ".xls"));
        book.close();

    }

    private static Workbook generate(List<AuditOperation> list){
        Workbook book = new HSSFWorkbook();

        Sheet sheet = book.createSheet("Sheet 1");
        Row row0 = sheet.createRow(0);
        Cell header = row0.createCell(0);
        header.setCellValue("Audit operations report for " + dateFormat.format(new Date()));
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));

        Row secondRow = sheet.createRow(1);

        secondRow.createCell(0).setCellValue("#");
        secondRow.createCell(1).setCellValue("Date/Time");
        secondRow.createCell(2).setCellValue("Succ.");
        secondRow.createCell(3).setCellValue("Action");

        DataFormat format = book.createDataFormat();
        CellStyle cellDateStyle = book.createCellStyle();
        cellDateStyle.setDataFormat(format.getFormat("m/d/yy h:mm"));

        for (int i = 0; i < list.size(); i++) {
            Row row = sheet.createRow(i + 2);
            AuditOperation ao = list.get(i);

            row.createCell(0).setCellValue(ao.getId());
            row.createCell(1).setCellValue(ao.getDate());
            row.createCell(2).setCellValue( ao.isSuccessful() ? "Yes" : "No" );
            row.createCell(3).setCellValue(ao.getAction());

            row.getCell(1).setCellStyle(cellDateStyle);
        }

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        return book;

    }
}
