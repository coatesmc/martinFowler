package com.coates.gateway.routes;

import com.coates.gateway.entity.GatewayDefine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName GatewayDefineRepository
 * @Description TODO
 * @Author mc
 * @Date 2019/6/13 16:16
 * @Version 1.0
 **/
@Repository
public interface GatewayDefineRepository extends JpaRepository<GatewayDefine,String> {
    @Override
    List<GatewayDefine> findAll();
    @Override
    GatewayDefine save(GatewayDefine gatewayDefine);
    @Override
    void deleteById(String id);
    @Override
    boolean existsById(String id);
}
