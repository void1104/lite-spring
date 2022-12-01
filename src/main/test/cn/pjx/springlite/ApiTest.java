package cn.pjx.springlite;

import cn.pjx.springlite.beans.factory.config.BeanDefinition;
import cn.pjx.springlite.beans.factory.support.DefaultListableBeanFactory;

public class ApiTest {

    public static void main(String[] args) {
        // 1.初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2.注册bean，其实是注册beanDefinition
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 3.获取bean，bean正是在获取的过程中才懒加载实例化的.
        UserService userService1 = (UserService) beanFactory.getBean("userService");
        userService1.queryUserInfo();
        // 4.获取bean，发现是同一个实例，证明单例测试成功
        UserService userService2 = (UserService) beanFactory.getBean("userService");
        System.out.println(userService1);
        System.out.println(userService2);
        System.out.println(userService1 == userService2);
    }
}
