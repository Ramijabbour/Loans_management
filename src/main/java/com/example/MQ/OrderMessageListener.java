package com.example.MQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.SiteConfig.MasterService;
import com.example.settelmets.Repositories.OnHoldCheckRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderMessageListener {

    @Autowired
    private OnHoldCheckRepository onHoldCheckRepository;
    
    
    
/*
    static final Logger logger = LoggerFactory.getLogger(OrderMessageListener.class);
    @RabbitListener(queues = RabbitConfig.QUEUE_ORDERS)
    public void processOrder(Chaque check) {
        check.id=check.id;
        System.out.println(check.Amount+" "+check.CheckId+" "+check.FirstBank+" "+check.SecondBank+" "+check.active);

      //  check.localDateTime= MasterService.getCurrDateTime();
        onHoldCheckRepository.save(check);
        logger.info("Order Received: "+check);
    }
  */  
//    @Scheduled(fixedRate = 7200000)
//    public void reportCurrentTime() {
//       return;
//    }

    
}