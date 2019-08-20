package com.coates.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ExcelListener
 * @Description TODO
 * @Author mc
 * @Date 2019/5/13 9:56
 * @Version 1.0
 **/
public class ExcelListener extends AnalysisEventListener {
    public final static Logger logger = LoggerFactory.getLogger(ExcelListener.class);
    //自定义用于暂时存储data。
    //可以通过实例获取该值
    private List<CompanyInFoModel> datas = new ArrayList<>();

    /**
     * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
     */
    @Override
    public void invoke(Object object, AnalysisContext context) {
        CompanyInFoModel companyInFoModel = (CompanyInFoModel) object;
        datas.add(companyInFoModel);
        //根据自己业务做处理
        doSomething(object);
    }
    private void doSomething(Object object) {
        //System.out.println("this is object -->{"+object+"}");

        System.out.println(datas);


        //logger.info("this is object-->{}",object);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        /*
            datas.clear();
            解析结束销毁不用的资源
         */
    }

    public List<CompanyInFoModel> getDatas() {
        return datas;
    }

    public void setDatas(List<CompanyInFoModel> datas) {
        this.datas = datas;
    }
}
