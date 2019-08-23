package com.coates.helloservice.common.errors;

import com.coates.helloservice.model.ErrorLogEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ErrorLogService
 * @Description TODO
 * @Author mc
 * @Date 2019/8/23 17:09
 * @Version 1.0
 **/
@Slf4j
@Service
public class ErrorLogService {

    private  Object errorLogMapper;

   /* public ErrorLogService(Object errorLogMapper) {
        this.errorLogMapper = errorLogMapper;
    }*/


    /**
     * 添加错误日志
     * @param operType  积分操作类型（0：新增，1：扣分）
     * @param scoreType  积分类型
     * @param content 具体操作内容
     * @param remark 备注
     * @return
     */
    @Transactional
    public Boolean addErrorLog(Integer operType, Integer scoreType, String content, String remark){
        if( operType == null || scoreType == null || StringUtils.isEmpty(content) ) {
            return Boolean.FALSE;
        }

        ErrorLogEntity errorLog = new ErrorLogEntity();
        errorLog.setOperType(operType);
        errorLog.setContent(content);
        errorLog.setRemark(remark);
        errorLog.setScoreType(scoreType);
        errorLog.setCreateTime(new Date(System.currentTimeMillis()));
       // int i = ErrorLogMapper.insertUseGeneratedKeys(errorLog);
        log.info("日志记录为:[{}]",errorLog);
        int i = 1;
        return i > 0;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<ErrorLogEntity> getAll(){
       // errorLogMapper.selectAll();
        return new ArrayList<>();
    }

    @Transactional
    public int deleteById(Long id){
        //errorLogMapper.deleteByPrimaryKey(id)
        return 0;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<ErrorLogEntity> detailedInquiry(String ids,
                                                     String score_type,
                                                     String oper_type,
                                                     String content,
                                                     String remark,
                                                     String start_time, String end_time){
        //errorLogMapper.detailedInquiry(ids,
        //                score_type,
        //                oper_type,
        //                content,
        //                remark,
        //                start_time, end_time);

        return new ArrayList<>();
    }
}
