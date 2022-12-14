package cn.pjx.springlite;

import cn.pjx.springlite.beans.PropertyValue;
import cn.pjx.springlite.beans.PropertyValues;
import cn.pjx.springlite.beans.factory.config.BeanDefinition;
import cn.pjx.springlite.beans.factory.config.BeanReference;
import cn.pjx.springlite.beans.factory.support.DefaultListableBeanFactory;
import cn.pjx.springlite.beans.factory.xml.XmlBeanDefinitionReader;
import cn.pjx.springlite.common.MyBeanFactoryPostProcessor;
import cn.pjx.springlite.common.MyBeanPostProcessor;
import cn.pjx.springlite.context.support.ClassPathXmlApplicationContext;
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
     * <p>
     * - 实现初始化实例bean时，同时给bean注入对象属性.
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

    /**
     * 测试04
     * <p>
     * - 实现通过xml配置文件去初始化bean定义
     */
    @Test
    public void test4_forXmlReader() {
        // 1.初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2.手动读取配置文件
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        UserService userService = beanFactory.getBean("userService", UserService.class);
        String s = userService.queryUserInfo();
        assert s.equals("A's info");
    }

    /**
     * 测试05 - 1
     * <p>
     * - 封装了一层上下文，提供给用户做容器自动初始化
     */
    @Test
    public void test5_forContext() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");

        UserService userService = context.getBean("userService", UserService.class);
        String s = userService.queryUserInfo();
    }

    /**
     * 测试05 - 2
     * - 引入了beanFactoryPostProcessor和beanPostProcessor，提供给用户对bean执行自定义操作
     */
    @Test
    public void test5_forPostProcessor() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        MyBeanFactoryPostProcessor p1 = new MyBeanFactoryPostProcessor();
        p1.postProcessBeanFactory(beanFactory);

        MyBeanPostProcessor p2 = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(p2);

        UserService userService = beanFactory.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        // 这里本该打印A's info，但因为BeanFactoryPostProcessor修改了bean定义，所以打印了void‘s info
        System.out.println("测试结果：" + result);
    }

    /**
     * 测试06
     * - 引入了InitialBean和DisposableBean
     */
    @Test
    public void test6_forInitAndDestroy() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");

        UserService userService = context.getBean("userService", UserService.class);
        String s = userService.queryUserInfo();
        System.out.println(s);
    }

    /**
     * 测试07
     * - 测试一下实现Aware接口，获取bean对应的spring资源.
     * - 顺便测试一下对象作用域（单例 / 原型）
     */
    @Test
    public void test7_forAware() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");

        UserService userService = context.getBean("userService", UserService.class);
        String res = userService.queryUserInfo();
        System.out.println(res);
        System.out.println(userService.getBeanName());
        System.out.println(userService.getApplicationContext());
        System.out.println(userService.getBeanFactory());

        UserService userService1 = context.getBean("userService", UserService.class);
        System.out.println(userService == userService1);
    }

    /**
     * 测试08
     * - 测试一下FactoryBean, 在getObject方法中定义代理类返回.
     */
    @Test
    public void test8_forFactoryBean() {
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        UserService userService = applicationContext.getBean("userService", UserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }
}
