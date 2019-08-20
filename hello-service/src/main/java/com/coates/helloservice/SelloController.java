package com.coates.helloservice;


import com.coates.helloservice.Threads.ThreadPoolManager;
import com.coates.helloservice.excel.ExcelUtil;
import com.coates.tools.entity.ApiResult;
import com.coates.tools.entity.ApiResultPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController()
public class SelloController {

    public static Logger logger = LoggerFactory.getLogger(SelloController.class);


    @Autowired
    RestTemplate restTemplate;

    private final ThreadPoolManager threadPoolManager;

    // @Value("${score-thread-pool.core}")
    private Integer corePoolSize = 10;

    // @Value("${score-thread-pool.maximum}")
    private Integer maximumPoolSize = 30;

    //@Value("${score-thread-pool.queue}")
    private Integer blockingQueue = 80000;

    @Autowired
    public SelloController(ThreadPoolManager threadPoolManager) {
        this.threadPoolManager = threadPoolManager;
    }


    @RequestMapping(value = "/getUserInfo")
    @ResponseBody
    public ApiResultPage Hello(@RequestParam String account, @RequestParam int pageIndex, @RequestParam int pageSize) throws InterruptedException {
        logger.info("hello world-{}", "8081");
        logger.info("invoking timeout endpoint");
        Map<String, Object> map = new HashMap<>();
        map.put("account", account);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        ResponseEntity<ApiResultPage> responseEntity = restTemplate.getForEntity("http://REGISTRATION-CENTER/userAccountInfo/getUserInfo" +
                        "?account={account}&pageIndex={pageIndex}&pageSize={pageSize}",
                ApiResultPage.class, map);
        logger.info("this is result ——————>>>>> {}", responseEntity.getBody());
        return responseEntity.getBody();
    }

    /**
     * 测试模拟下单请求 入口
     *
     * @param id
     * @return
     * @throws InterruptedException
     */
    @GetMapping(value = "/start/{id}")
    public String start(@PathVariable Long id) {
        //模拟的随机数
        String orderNo = System.currentTimeMillis() + UUID.randomUUID().toString();
        threadPoolManager.addOrders(orderNo);

        logger.info("hello world-{}", "8071");
        logger.info("invoking timeout endpoint");
        return "Test ThreadPoolExecutor start";
    }

    /**
     * 停止服务
     *
     * @param id
     * @return
     */
    @GetMapping("/end/{id}")
    public String end(@PathVariable Long id) {
        threadPoolManager.shutdown();
        Queue q = threadPoolManager.getMsgQueue();
        System.out.println("关闭了线程服务，还有未处理的信息条数：" + q.size());
        return "Test ThreadPoolExecutor start";
    }


    public String canasync() {
        //判断是否可以异步
        boolean canAsync = true;
        if (canAsync) {
        /*    LogAspectHelper.SSLog ssLog = HttpLogAspect.getSSLog();   //重新设置上下文
            //缓存积分消费到队列
            OrderCacheManager.getInstance(corePoolSize, maximumPoolSize, blockingQueue).exchange(null);*/
        }
        return null;
    }

    /**
     * 数据导入
     *
     * @param workerId  操作人id
     * @param file      上传文件
     * @param companyId 公司id
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/uploadData")
    @ResponseBody
    public ApiResult uploadDataBase64(String workerId, MultipartFile file, String companyId, String isGroup) throws Exception {
      /*  if (StringUtils.isEmpty(companyId)) {
            return null
        }
        if (StringUtils.isEmpty(workerId)) {

        }
        if (StringUtils.isEmpty(file)) {
            throw new ApiResult("上传文件不能为空");
        }
        //清楚本单位旧数据
        gsUserBatchImportService.queryNoDataAndDeleteData(workerId, companyId);
        GsCompany gsCompany = companyService.getObjByStringId(companyId);
        if (gsCompany==null){
            throw new MyRuntimeException("操作单位不存在，请重新登录后在尝试。");
        }
        //解析用户数据
        gsUserBatchImportService.uploadDataBase64(workerId, file, companyId, gsCompany, isGroup, fileUrl);*/
        List<GsUserBatchVo> list = new ArrayList<>();
        List<Object> readExcel = ExcelUtil.readExcel(file, new GsUserBatchVo(), 1, 1);
        logger.info("this is readExcel--->>[{}]", readExcel);
        for (Object object : readExcel) {
            list.add((GsUserBatchVo) object);
        }
        logger.info("this is readExcel--->>[{}]", list.toString());
        return null;
    }


}
