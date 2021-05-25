package com.guojianyong.test.serviceTest;//package com.guojianyong.test.serviceTest;
//
//import com.guojianyong.model.Moment;
//import com.guojianyong.model.ServiceResult;
//import com.guojianyong.service.MomentService;
//import com.guojianyong.service.factory.ServiceProxyFactory;
//import com.guojianyong.service.impl.MomentServiceImpl;
//import org.junit.Test;
//
//import java.lang.reflect.Method;
//import java.math.BigInteger;
//
//public class MessageServiceTest {
//
//    MomentService momentService = (MomentService) new ServiceProxyFactory().getProxyInstance(new MomentServiceImpl());
//
//    @Test
//    public void insertMessage() {
//
//        ServiceResult serviceResult = momentService.listMyMoment(BigInteger.valueOf(1), 1);
//        System.out.println(serviceResult.getStatus());
//        System.out.println(serviceResult.getMessage());
//        System.out.println(serviceResult.getData());
//
//    }
//
//    @Test
//    public void listAllMessage() throws NoSuchMethodException {
//
//        Method method = Moment.class.getMethod("setId" , BigInteger.class);
//        System.out.println(method);
//    }
//
//    @Test
//    public void listUnreadMessage() {
//    }
//
//    @Test
//    public void listAllUnreadMessage() {
//    }
//
//    @Test
//    public void removeMessage() {
//    }
//
//    @Test
//    public void removeAllMessage() {
//    }
//
//    @Test
//    public void exportMessage() {
//    }
//
//    @Test
//    public void drawBackMessage() {
//    }
//
//    @Test
//    public void setAlreadyRead() {
//    }
//}
