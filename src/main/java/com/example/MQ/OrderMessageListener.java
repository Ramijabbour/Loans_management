package com.example.MQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.settelmets.Models.Chaque;
import com.example.settelmets.Repositories.OnHoldCheckRepository;

import org.springframework.stereotype.Service;

@Service
public class OrderMessageListener {

    @Autowired
    private OnHoldCheckRepository onHoldCheckRepository;
    
    
    

    static final Logger logger = LoggerFactory.getLogger(OrderMessageListener.class);
    @RabbitListener(queues = "CheckQueue")
    public void processOrder(Chaque check) {
    	System.out.println("check recieved with info : "+check);
    	check.setSent(false);
        onHoldCheckRepository.save(check);
        logger.info("Order Received: "+check);
    }
    
//    @Scheduled(fixedRate = 7200000)
//    public void reportCurrentTime() {
//       return;
//    }

    
}