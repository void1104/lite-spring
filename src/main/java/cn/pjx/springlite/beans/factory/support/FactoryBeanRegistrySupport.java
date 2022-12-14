package cn.pjx.springlite.beans.factory.support;

import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FactoryBean注册服务 处理关于FactoryBean此类对象的注册操作
 */
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    /**
     * 存放FactoryBean单例的容器,用于跟普通的单例bean区分
     * - 需要特殊解释的是，普通的BeanRegistry中存放的bean包括实现了FactoryBean的类，而这里存放的是FactoryBean#getObject的代理类实例.
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        return (object != NULL_OBJECT ? object : null);
    }

    protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName) {
        if (factory.isSingleton()) {
            Object object = factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factory, beanName);
                factoryBeanObjectCache.put(beanName, (object != null ? object : NULL_OBJECT));
            }
        } else {
            return doGetObjectFromFactoryBean(factory, beanName);
        }
        return null;
    }

    private Object doGetObjectFromFactoryBean(final FactoryBean factory, final String beanName) {
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeanException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }
}
