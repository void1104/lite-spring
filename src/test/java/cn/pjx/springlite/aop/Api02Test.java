package cn.pjx.springlite.aop;

import cn.pjx.springlite.aop.aspectj.AspectJExpressionPointcut;
import cn.pjx.springlite.aop.aspectj.AspectJExpressionPointcutAdvisor;
import cn.pjx.springlite.aop.bean.IUserService;
import cn.pjx.springlite.aop.bean.UserService;
import cn.pjx.springlite.aop.bean.UserServiceBeforeAdvice;
import cn.pjx.springlite.aop.bean.UserServiceInterceptor;
import cn.pjx.springlite.aop.framework.ProxyFactory;
import cn.pjx.springlite.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import cn.pjx.springlite.context.support.ClassPathXmlApplicationContext;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Before;
import org.junit.Test;

public class Api02Test {

    private AdvisedSupport advisedSupport;

    @Before
    public void init() {
        // 目标对象
        IUserService userService = new UserService();
        // 组装代理信息
        advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* cn.pjx.springlite.aop.bean.IUserService.*(..))"));
    }

    @Test
    public void test_proxyFactory() {
        // false: JDK动态代理, true:CGlib动态代理
        advisedSupport.setProxyTargetClass(false);
        IUserService proxy = (IUserService) new ProxyFactory(advisedSupport).getProxy();

        System.out.println("测试结果: " + proxy.queryUserInfo());
    }

    @Test
    public void test_beforeAdvice() {
        UserServiceBeforeAdvice beforeAdvice = new UserServiceBeforeAdvice();
        MethodBeforeAdviceInterceptor interceptor = new MethodBeforeAdviceInterceptor(beforeAdvice);
        advisedSupport.setMethodInterceptor(interceptor);

        IUserService proxy = (IUserService) new ProxyFactory(advisedSupport).getProxy();
        System.out.println("测试结果: " + proxy.queryUserInfo());
    }

    @Test
    public void test_advisor() {
        // 目标对象
        IUserService userService = new UserService();

        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression("execution(* cn.pjx.springlite.aop.bean.IUserService.*(..))");
        advisor.setAdvice(new MethodBeforeAdviceInterceptor(new UserServiceBeforeAdvice()));

        ClassFilter classFilter = advisor.getPointcut().getClassFilter();
        if (classFilter.matches(userService.getClass())) {
            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(userService);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

            IUserService proxy = (IUserService) new ProxyFactory(advisedSupport).getProxy();
            System.out.println("测试结果: " + proxy.queryUserInfo());
        }
    }

    /**
     * 测试将aop融入spring生命周期
     */
    @Test
    public void test_aopWithSpringLifecycle() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-aop.xml");
        IUserService userService = context.getBean("userService", IUserService.class);
        System.out.println("测试结果: " + userService.queryUserInfo());
    }
}
