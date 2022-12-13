package cn.pjx.springlite.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import cn.pjx.springlite.beans.factory.DisposableBean;
import cn.pjx.springlite.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * 销毁方法适配器（接口和配置）
 * 销毁方法是由AbstractApplicationContext在注册虚拟机钩子后，虚拟机关闭前执行的操作动作.
 * 在销毁时不希望关注销毁的那些类型的方法，而是希望有一个统一的接口，所以这里就新增了适配类，做统一处理.
 *
 * - 个人理解：
 * 如果这里不用适配器，当是xml或者注解方式实现时，存放到beanRegistry就不知道如何定义父类.
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        // 调用实现接口DisposableBean的销毁方法
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        } else {
            // 或者是配置了xml的destroy方法
            if (StrUtil.isNotEmpty(destroyMethodName)) {
                Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
                destroyMethod.invoke(bean);
            }
        }
    }
}
