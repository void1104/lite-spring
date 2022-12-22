package cn.pjx.springlite.aop.framework;

import cn.pjx.springlite.aop.AdvisedSupport;

/**
 * 代理工厂,解决关于JDK和Cglib两种代理的选择问题, 有了代理工厂就可以按照不同的创建需求进行控制
 */
public class ProxyFactory {

    private final AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    /**
     * 获取代理后的对象
     */
    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    /**
     * 创建代理对象(JDK / Cglib)
     */
    private AopProxy createAopProxy() {
        if (advisedSupport.isProxyTargetClass()) {
            return new Cglib2AopProxy(advisedSupport);
        }

        return new JdkDynamicAopProxy(advisedSupport);
    }

}
