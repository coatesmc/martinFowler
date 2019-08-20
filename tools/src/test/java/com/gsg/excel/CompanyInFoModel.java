package com.coates.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName model
 * @Description TODO
 * @Author mc
 * @Date 2019/5/13 10:17
 * @Version 1.0
 **/
@Data
public class CompanyInFoModel extends BaseRowModel {
    @ExcelProperty(value ="单位名称", index=0)
    private String  companyName;
    @ExcelProperty(value ="单位联系人",index=1)
    private String companyContact;
    @ExcelProperty(value ="联系电话",index=2)
    private String contactNumber;
    @ExcelProperty(value ="充值金额",index=3)
    private BigDecimal money;
}
