package cn.pjx.springlite.aop;

import cn.pjx.springlite.aop.aspectj.AspectJExpressionPointcut;
import cn.pjx.springlite.aop.bean.UserService;
import cn.pjx.springlite.aop.bean.IUserService;
import cn.pjx.springlite.aop.bean.UserServiceInterceptor;
import cn.pjx.springlite.aop.framework.Cglib2AopProxy;
import cn.pjx.springlite.aop.framework.JdkDynamicAopProxy;
import org.junit.Test;

import java.lang.reflect.Method;

public class ApiTest {

    /**
     * 单独测试AspectJ匹配类的能力.
     */
    @Test
    public void test_aop() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* cn.pjx.springlite.aop.bean.UserService.*(..))");
        Class<UserService> clazz = UserService.class;

        Method method = clazz.getDeclaredMethod("queryUserInfo");

        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));
    }

    /**
     * 单独测试JDK静态代理对象
     */
    @Test
    public void test_dynamic() {
        // 目标对象(被代理对象)
        IUserService userService = new UserService();
        // 组装代理信息
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        // 这里的表达式如果写interface的则jdk和cglib都可以代理到, 如果写类的则只有cglib能代理成功
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* cn.pjx.springlite.aop.bean.UserService.*(..))"));

        // 代理对象(JDK)
        IUserService proxyJdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果: " + proxyJdk.queryUserInfo());

        // 代理对象(Cglib2AopProxy)
        IUserService proxyCglib = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果: " + proxyCglib.register("pengjiaxin!"));
    }
}
