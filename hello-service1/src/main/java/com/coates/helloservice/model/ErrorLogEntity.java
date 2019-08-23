package com.coates.helloservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 错误日志实体
 * @author huang_kangjie
 * @create 2018-06-09 15:09
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "t_score_error_log")
public class ErrorLogEntity {

     @Id
     @Column(name = "score_error_log_id")
//     @ApiModelProperty(name = "日志id")
     private Long scoreErrorLogId;

     @Column(name = "score_type")
//     @ApiModelProperty(name = "积分类型")
     private Integer scoreType;

     @Column(name = "oper_type")
//     @ApiModelProperty(name = "操作类型")
     private Integer operType;

     @Column(name = "content")
//     @ApiModelProperty(name = "消息体json")
     private String content;

     @Column(name = "remark")
//     @ApiModelProperty(name = "备注")
     private String remark;

     @Column(name = "create_time")
//     @ApiModelProperty(name = "创建时间")
     private Date createTime;

}
