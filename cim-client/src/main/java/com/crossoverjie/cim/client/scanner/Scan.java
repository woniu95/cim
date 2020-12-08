package com.crossoverjie.cim.client.scanner;

import com.crossoverjie.cim.client.client.CIMClient;
import com.crossoverjie.cim.client.config.AppConfiguration;
import com.crossoverjie.cim.client.po.ClientTimeInfo;
import com.crossoverjie.cim.client.service.EchoService;
import com.crossoverjie.cim.client.service.MsgHandle;
import com.crossoverjie.cim.client.service.MsgLogger;
import com.crossoverjie.cim.client.service.impl.system.IIdleClientService;
import com.crossoverjie.cim.client.util.SpringBeanFactory;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Scanner;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/12/21 16:44
 * @since JDK 1.8
 */
public class Scan implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(Scan.class);

    /**
     * 系统参数
     */
    private AppConfiguration configuration;

    private MsgHandle msgHandle ;

    private MsgLogger msgLogger ;

    private EchoService echoService ;


    private CIMClient cimClient;

    private ClientTimeInfo clientTimeInfo;

    public Scan() {
        this.configuration = SpringBeanFactory.getBean(AppConfiguration.class);
        this.msgHandle = SpringBeanFactory.getBean(MsgHandle.class) ;
        this.msgLogger = SpringBeanFactory.getBean(MsgLogger.class) ;
        this.echoService = SpringBeanFactory.getBean(EchoService.class) ;
        this.cimClient = SpringBeanFactory.getBean(CIMClient.class);
        this.clientTimeInfo = SpringBeanFactory.getBean(ClientTimeInfo.class);
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);

        while (true) {

            String msg = sc.nextLine();
            //检查消息
            if (msgHandle.checkMsg(msg)) {

                if(!clientTimeInfo.isAlive()){
                    cimClient.start();
                }
                clientTimeInfo.setLastOperateTime(new Date());

                //系统内置命令
                if (!msgHandle.innerCommand(msg)){
                    //真正的发送消息
                    msgHandle.sendMsg(msg) ;

                    //写入聊天记录
                    msgLogger.log(msg) ;

                    echoService.echo(EmojiParser.parseToUnicode(msg));
                }

            }

        }
    }

}
