package cn.pjx.springlite.design;

import cn.pjx.springlite.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * @author pengjiaxin3
 * @description
 * @date 12/25/22 12:56 PM
 */
public class TestCircleInject02 {

    @Test
    public void test_circular() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-circle.xml");
        Husband husband = applicationContext.getBean("husband", Husband.class);
        Wife wife = applicationContext.getBean("wife", Wife.class);
        System.out.println("老公的媳妇：" + husband.queryWife());
        System.out.println("媳妇的老公：" + wife.queryHusband());
    }
}
