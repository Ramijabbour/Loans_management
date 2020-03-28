package com.example.MQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageListener {

    @Autowired
    private OnHoldCheckRepository onHoldCheckRepository;

    /*
    static final Logger logger = LoggerFactory.getLogger(OrderMessageListener.class);

    @RabbitListener(queues = RabbitConfig.QUEUE_ORDERS)
    public void processOrder(Chaque check) {
        check.id=check.id;
        onHoldCheckRepository.save(check);
        logger.info("Order Received: "+check);
    }
    @Scheduled(fixedRate = 7200000)
    public void reportCurrentTime() {
       return;
    }
    */
    
}