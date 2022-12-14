package cn.pjx.springlite.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FactoryBean注册服务 处理关于FactoryBean此类对象的注册操作
 */
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    protected static final Object NULL_OBJECT = new Object();

    /**
     *
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        return (object != NULL_OBJECT ? object : null);
    }

    //    protected Object getObjectFromFactoryBean() {
    //
    //    }
}
