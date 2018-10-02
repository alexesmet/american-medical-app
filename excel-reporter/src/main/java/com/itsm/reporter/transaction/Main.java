package com.itsm.reporter.transaction;

import com.itsm.common.entity.AuditOperation;
import com.itsm.common.entity.Transaction;
import com.itsm.reporter.mappers.TransactionMapper;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm");

    public static void main(String[] args) throws IOException, ParseException {

        if(args.length != 2){
            System.err.println("Input two arguments: begin and end date in the following format: yyyy-MM-dd_HH-mm");
            System.exit(1);
        }

        Date date1 = dateFormat.parse(args[0]);
        Date date2 = dateFormat.parse(args[1]);

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession();
        TransactionMapper mapper = session.getMapper(TransactionMapper.class);
        List<Transaction> transactionList = mapper.getAllInRange(date1,date2);
        session.close();

        Workbook book = generate(transactionList, date1, date2);

        book.write(new FileOutputStream("reports/excel/transactions/T_" + dateFormat.format(new Date()) + ".xls"));
        book.close();

    }

    private static Workbook generate(List<Transaction> list, Date date1, Date date2){


        Workbook book = new HSSFWorkbook();

        Sheet sheet = book.createSheet("Sheet 1");

        sheet.createRow(0)
                .createCell(0)
                .setCellValue("Transactions report for " + dateFormat.format(new Date()));

        sheet.createRow(1)
                .createCell(0)
                .setCellValue("From " + dateFormat.format(date1) + " to " + dateFormat.format(date2));


        sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));
        sheet.addMergedRegion(new CellRangeAddress(1,1,0,4));

        Row thirdRow = sheet.createRow(2);

        thirdRow.createCell(0).setCellValue("#");
        thirdRow.createCell(1).setCellValue("Date/Time");
        thirdRow.createCell(2).setCellValue("Client");
        thirdRow.createCell(3).setCellValue("Product");
        thirdRow.createCell(4).setCellValue("State");

        DataFormat format = book.createDataFormat();
        CellStyle cellDateStyle = book.createCellStyle();
        cellDateStyle.setDataFormat(format.getFormat("m/d/yy h:mm"));

        for (int i = 0; i < list.size(); i++) {
            Row row = sheet.createRow(i + 2);
            Transaction tr = list.get(i);

            row.createCell(0).setCellValue(tr.getId());
            row.createCell(1).setCellValue(tr.getDatetime());
            row.createCell(2).setCellValue(tr.getPatient().getName());
            row.createCell(3).setCellValue(tr.getProduct().getName());
            row.createCell(4).setCellValue(tr.getPatient().getState().getName());

            row.getCell(1).setCellStyle(cellDateStyle);
        }

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        return book;
    }
}
