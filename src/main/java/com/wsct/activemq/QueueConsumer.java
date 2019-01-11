package com.wsct.activemq;
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
 
import javax.jms.ObjectMessage;
import java.util.List;
 /**
  * 消费队列
  * @author 96342
  *
  */
@Component
public class QueueConsumer {
	@Value("${mq.flightName}")
	final String flightQueueName = "flight";
	@Value("${mq.taskName}")
	final String taskQueueName = "task";
	@Value("${mq.oilBillName}")
	final String oilBillQueueName ="oilBill";
	
    @JmsListener(destination = flightQueueName, containerFactory = "jmsListenerContainerQueue")
    public void receiveFlightQueue(String msg) {
        System.out.println("接收到消息...." + msg);
    }
    @JmsListener(destination = taskQueueName, containerFactory = "jmsListenerContainerQueue")
    public void receiveTaskQueue(String msg) {
        System.out.println("接收到消息...." + msg);
    }
    @JmsListener(destination = oilBillQueueName, containerFactory = "jmsListenerContainerQueue")
    public void receiveOilBillQueue(String msg) {
        System.out.println("接收到消息...." + msg);
    }
 
//    @JmsListener(destination = "stringListQueue", containerFactory = "jmsListenerContainerQueue")
//    public void receiveStringListQueue(List<String> list) {
//        System.out.println("接收到集合队列消息...." + list);
//    }
// 
// 
//    @JmsListener(destination = "objQueue", containerFactory = "jmsListenerContainerQueue")
//    public void receiveObjQueue(ObjectMessage objectMessage) throws Exception {
//        System.out.println("接收到对象队列消息...." + objectMessage.getObject());
//    }
// 
// 
//    @JmsListener(destination = "objListQueue", containerFactory = "jmsListenerContainerQueue")
//    public void receiveObjListQueue(ObjectMessage objectMessage) throws Exception {
//        System.out.println("接收到的对象队列消息..." + objectMessage.getObject());
//    }
// 
 
}
