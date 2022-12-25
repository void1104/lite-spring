package cn.pjx.springlite.beans.factory.support;

import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.factory.DisposableBean;
import cn.pjx.springlite.beans.factory.ObjectFactory;
import cn.pjx.springlite.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 默认实例注册中心
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 用于作为那些map中的value
     */
    protected static final Object NULL_OBJECT = new Object();

    /**
     * 一级缓存,存放普通对象
     */
    private final Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * 二级缓存,提前暴露对象,存放没有完全实例化的对象
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>();

    /**
     * 三级缓存,存放代理对象
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();

    /**
     * 存放需要执行销毁方法的bean
     */
    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        Object singletonObject = singletonObjects.get(beanName);
        if (null == singletonObject) {
            singletonObject = earlySingletonObjects.get(beanName);
            // 只有代理对象才会放到三级缓存中.
            if (null == singletonObject) {
                ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    singletonObject = singletonFactory.getObject();
                    // 把三级缓存中代理对象的真实对象取出来,放入二级缓存中.
                    earlySingletonObjects.put(beanName, singletonObject);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        if (!this.singletonObjects.containsKey(beanName)) {
            this.singletonFactories.put(beanName, singletonFactory);
            this.earlySingletonObjects.remove(beanName);
        }
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    public void destroySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeanException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
