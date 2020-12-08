package com.crossoverjie.cim.client.service.impl.system;

import com.crossoverjie.cim.client.client.CIMClient;
import com.crossoverjie.cim.client.config.BeanConfig;
import com.crossoverjie.cim.client.po.ClientTimeInfo;
import com.crossoverjie.cim.client.service.EchoService;
import com.crossoverjie.cim.client.service.MsgLogger;
import com.crossoverjie.cim.client.service.RouteRequest;
import com.crossoverjie.cim.client.service.ShutDownMsg;
import com.crossoverjie.cim.client.util.SpringBeanFactory;
import com.crossoverjie.cim.common.data.construct.RingBufferWheel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: cim
 * @description:
 * @author: 王强
 * @create: 2020-12-08 10:12
 */
@Service
public class IdleClientIdleClientServiceImpl implements IIdleClientService {

    private final static Logger LOGGER = LoggerFactory.getLogger(IdleClientIdleClientServiceImpl.class);

    @Autowired
    private RouteRequest routeRequest ;

    @Autowired
    private CIMClient cimClient;

    @Autowired
    private MsgLogger msgLogger;

    @Resource(name = "callBackThreadPool")
    private ThreadPoolExecutor callBackExecutor;

    @Autowired
    private EchoService echoService ;


    @Autowired
    private ShutDownMsg shutDownMsg ;

    @Autowired
    private RingBufferWheel ringBufferWheel ;

    @Autowired
    private ClientTimeInfo clientTimeInfo;

    @Override
    public void release() {
        echoService.echo("idle cim client closing...");
        shutDownMsg.shutdown();
        routeRequest.offLine();
        msgLogger.stop();
//        callBackExecutor.shutdown();
//        ringBufferWheel.stop(false);
        clientTimeInfo.setAlive(false);
        try {
//            while (!callBackExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
//                echoService.echo("thread pool closing");
//            }
            cimClient.close();
        } catch (InterruptedException e) {
            LOGGER.error("InterruptedException", e);
        }
        echoService.echo("idle cim close success!");
    }

    @Override
    public void rebuild() {
//        BeanConfig beanConfig = SpringBeanFactory.getBean(BeanConfig.class);
//        beanConfig.bufferWheel();

    }


}
