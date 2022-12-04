package cn.pjx.springlite;

import cn.pjx.springlite.beans.PropertyValue;
import cn.pjx.springlite.beans.PropertyValues;
import cn.pjx.springlite.beans.factory.config.BeanDefinition;
import cn.pjx.springlite.beans.factory.config.BeanReference;
import cn.pjx.springlite.beans.factory.support.DefaultListableBeanFactory;
import org.junit.Test;

public class ApiTest {

    /**
     * 测试01
     * - 通过工厂去保存/获取单例bean
     */
    @Test
    public void test1_forNormalFactory() {
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

        assert userService1.queryUserInfo().equals("search for broadcast!!!");
        assert userService1 == userService2;
    }

    /**
     * 测试02
     * - 实现有参函数实例化Bean
     * - 使用策略模式引入实例化Bean的方式（CGLIB, JDK）
     */
    @Test
    public void test2_forParamConstructor() {
        // 1.初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2.注入bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 3.获取bean，相比测试01多了一个入参
        UserService userService = (UserService) beanFactory.getBean("userService", "pengjiaxin3");
        assert userService.queryUserInfo().equals("search for [pengjiaxin3]");
    }

    /**
     * 测试03
     */
    @Test
    public void test3_forInject() {
        // 1.初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2.构建bean信息，这一步目前是在单测中手动实现，但实际上应该是由spring一起完成
        BeanDefinition beanDefinitionUserService = new BeanDefinition(UserService.class);
        BeanDefinition beanDefinitionUserDao = new BeanDefinition(UserDao.class);
        // 3.将userDao设为userService的property
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));
        beanDefinitionUserService.setPropertyValues(propertyValues);
        // 4.注入bean
        beanFactory.registerBeanDefinition("userService", beanDefinitionUserService);
        beanFactory.registerBeanDefinition("userDao", beanDefinitionUserDao);
        // 5.获取bean，获取过程中除了实例化bean，还会注入bean的属性
        UserService userService1 = (UserService) beanFactory.getBean("userService", "A");
        assert userService1.queryUserInfo().equals("A's info");
    }
}
