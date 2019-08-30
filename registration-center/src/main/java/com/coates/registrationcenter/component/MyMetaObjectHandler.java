package com.coates.registrationcenter.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyMetaObjectHandler
 * @Description TODO
 * @Author mc
 * @Date 2019/8/27 15:23
 * @Version 1.0
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        boolean hasSetter = metaObject.hasSetter("createTime");
        if (hasSetter) {
            // setInsertFieldValByName("",new Date());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object val = getFieldValByName("updateTime", metaObject);
        if (val != null) {
            //setUpdateFieldValByName("updateTime",new Date());
        }
    }
}
