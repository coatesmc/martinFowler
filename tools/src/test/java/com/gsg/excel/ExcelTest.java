package com.coates.excel;

import com.coates.tools.excelutils.ExcelException;
import com.coates.tools.excelutils.ExcelUtil;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

/**
 * @ClassName ExcelTest
 * @Description TODO
 * @Author mc
 * @Date 2019/5/13 9:38
 * @Version 1.0
 **/
public class ExcelTest  {
    private static Logger logger = LoggerFactory.getLogger(ExcelTest.class);

    public static void main(String[] args) {
        try {
            testExcel2003NoModel();
        } catch (ExcelException e) {
            e.printStackTrace();
        }
    }


    public static void testExcel2003NoModel() throws ExcelException {

        MultipartFile excelFile = getInputStream("C:\\Users\\Administrator\\Desktop\\单位福利充值名称.xls");

        List<CompanyInFoModel> importDtoList = ExcelUtil.readExcel(excelFile,CompanyInFoModel.class);
        for (CompanyInFoModel companyInFoModel:importDtoList ){
            System.out.println("this is data--->>{"+companyInFoModel+"}");
        }
    }


    private static MultipartFile getInputStream(String fileName) {
        try {
            File file = new File(fileName);
            FileInputStream input = new FileInputStream(file);
            return new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
