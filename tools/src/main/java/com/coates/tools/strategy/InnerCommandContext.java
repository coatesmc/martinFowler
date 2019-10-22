package com.coates.tools.strategy;

import com.coates.tools.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName InnerCommandContext
 * @Description TODO
 * @Author mc
 * @Date 2019/9/17 9:44
 * @Version 1.0
 **/
@Component
public class InnerCommandContext {
    private final static Logger LOGGER = LoggerFactory.getLogger(InnerCommandContext.class);
    @Autowired
    ApplicationContext applicationContext;

    public InnerCommand getInstance(String command) throws ClassNotFoundException {
        Map<String, Object> allClazz = SystemCommandEnum.getAllClazz();
        String[] trim = command.trim().split("");
        String clazz = (String) allClazz.get(trim[0]);
        InnerCommand innerCommand = null;
        if (StringUtils.isEmpty(clazz)) {
             clazz = PrintAllCommand.class.getName();
        }
        innerCommand = (InnerCommand) SpringBeanFactory.getBean(Class.forName(clazz));

        return null;
    }
}
