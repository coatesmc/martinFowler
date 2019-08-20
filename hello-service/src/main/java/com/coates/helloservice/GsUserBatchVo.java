package com.coates.helloservice;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @ClassName GsUserBatchVo
 * @Description TODO
 * @Author mc
 * @Date 2019/6/17 11:21
 * @Version 1.0
 **/
@Data
public class GsUserBatchVo extends BaseRowModel {

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名", index = 0)
    @NotNull(message = "姓名不能为空")
    @Min(2)
    @Pattern(regexp = "[\\u4E00-\\u9FA5]{2,5}", message = "姓名中文2-5位")
    private String userName;
    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号", index = 1)
    @NotNull(message = "手机号不能为空")
    @Max(value = 11)
    private String mobilePhone;
    /**
     * 证件号
     */
    @ExcelProperty(value = "证件号", index = 2)
    @NotNull(message = "证件号")
    @Max(value = 18)
    private String certificateCode;
    /**
     * 工号
     */
    @ExcelProperty(value = "工号", index = 3)
    private String workNumber;
    /**
     * 岗位
     */
    @ExcelProperty(value = "岗位", index = 4)
    private String job;
    /**
     * 职务
     */
    @ExcelProperty(value = "职务", index = 5)
    private String position;
    /**
     * 职级
     */
    @ExcelProperty(value = "职级", index = 6)
    private String rank;
    /**
     * 工作性质
     */
    @ExcelProperty(value = "工作性质", index = 7)
    private String natureWork;
    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱", index = 8)
    private String email;

    /**
     * 工作部门
     */
    @ExcelProperty(value = "工作部门", index = 9)
    private String department;

    /**
     * 入职日期
     */
    @ExcelProperty(value = "入职日期", index = 10)
    private String entryDate;

    /**
     * 户籍类型
     */
    @ExcelProperty(value = "户籍类型", index = 11)
    private String householdType;
    /**
     * 国籍
     */
    @ExcelProperty(value = "国籍", index = 12)
    private String nationality;
    /**
     * 婚姻
     */
    @ExcelProperty(value = "婚姻", index = 13)
    private String marriage;
    /**
     * 文化程度
     */
    @ExcelProperty(value = "文化程度", index = 14)
    private String levelEducation;
    /**
     * 政治面貌
     */
    @ExcelProperty(value = "政治面貌", index = 15)
    private String politicalLandscape;
    /**
     * 身高
     */
    @ExcelProperty(value = "身高", index = 16)
    private String height;
    /**
     * 体重
     */
    @ExcelProperty(value = "体重", index = 17)
    private String weight;
    /**
     * 血型
     */
    @ExcelProperty(value = "血型", index = 18)
    private String bloodType;

    /**
     * 时间(时)
     */
    @ExcelProperty(value = "时间(时)", index = 19)
    private String hour;
    /**
     * 分钟（分）
     */
    @ExcelProperty(value = "分钟（分）", index = 20)
    private String minute;

    /**
     * 传染病
     */
    @ExcelProperty(value = "传染病", index = 21)
    private String infectiousDiseases;
    /**
     * 皮肤病
     */
    @ExcelProperty(value = "皮肤病", index = 22)
    private String skinDisease;
    /**
     * 身体畸形
     */
    @ExcelProperty(value = "身体畸形", index = 23)
    private String physicalDeformities;
    /**
     * 残疾
     */
    @ExcelProperty(value = "残疾", index = 24)
    private String disability;
    /**
     * 个人征信
     */
    @ExcelProperty(value = "个人征信", index = 25)
    private String personalCreditRegistry;
    /**
     * 芝麻积分
     */
    @ExcelProperty(value = "芝麻积分", index = 26)
    private String sesameIntegral;
    /**
     * 违法记录
     */
    @ExcelProperty(value = "违法记录", index = 27)
    private String illegalRecord;
    /**
     * 中共党员
     */
    @ExcelProperty(value = "中共党员", index = 28)
    private String communistPartyChina;
    /**
     * 共青团员
     */
    @ExcelProperty(value = "共青团员", index = 29)
    private String leagueMembers;
    /**
     * 工会id
     */
    @ExcelProperty(value = "工会职务", index = 30)
    private String unionMember;
    /**
     * 劳动模范
     */
    @ExcelProperty(value = "劳动模范", index = 31)
    private String modelLabor;
    /**
     * 妇联
     */
    @ExcelProperty(value = "妇联工作", index = 32)
    private String womanFederation;

    /**
     * 身份标识
     */
    @ExcelProperty(value = "更多身份", index = 33)
    private String identity;

    /**
     * 是否工会会员
     */
    @ExcelProperty(value = "是否工会会员", index = 34)
    private String identityId;
    /**
     * 备注
     */
    @ExcelProperty(value = "备注", index = 35)
    private String remarks;
}
