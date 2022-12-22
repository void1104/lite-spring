package cn.pjx.springlite.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.PropertyValue;
import cn.pjx.springlite.beans.PropertyValues;
import cn.pjx.springlite.beans.factory.*;
import cn.pjx.springlite.beans.factory.config.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 装配工厂类
 * <p>
 * - 提供创建bean，保存单例缓存的能力.
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    /**
     * 依赖实例化策略类，获得实例化策略的能力
     */
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Object bean = null;
        try {
            // 实例化bean
            bean = createBeanInstance(beanDefinition, args);
            // 在设置Bean属性之前,允许BeanPostProcessor,修改属性值
            applyBeanPostProcessorsBeforeApplyingPropertyValues(bean, beanDefinition);
            // 给Bean填充属性
            applyPropertyValue(beanName, bean, beanDefinition);
            // 执行bean的自定义初始化方法 和 BeanPostProcessor的前置和后置方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeanException("create bean:[" + beanName + "] failed", e);
        }

        // 注册实现了DisposableBean的需要销毁逻辑的Bean对象
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        // 如果是单例,则注册单例到注册中心
        if (beanDefinition.isSingleton()) {
            registerSingleton(beanName, bean);
        }
        return bean;
    }

    /**
     * 通过实例化策略实例化bean
     *
     * @param beanDefinition bean定义
     * @param args           构造函数参数
     * @return 实例化的bean
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition, Object[] args) {
        Constructor constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        // \获取bean的所有构造函数，根据参数的长度进行匹配（简化版）
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        for (Constructor ctor : declaredConstructors) {
            if (null != args && ctor.getParameterTypes().length == args.length) {
                constructorToUse = ctor;
                break;
            }
        }
        return instantiationStrategy.instantiate(beanDefinition, constructorToUse, args);
    }

    /**
     * 在设置 Bean 属性之前,允许 BeanPostProcessor修改成员变量值,主要用于依赖注入.
     *
     * @param bean           bean
     * @param beanDefinition beanDefinition
     */
    protected void applyBeanPostProcessorsBeforeApplyingPropertyValues(Object bean, BeanDefinition beanDefinition) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                PropertyValues pvs = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(beanDefinition.getPropertyValues(), bean);
                if (null != pvs) {
                    for (PropertyValue propertyValue : pvs.getPropertyValues()) {
                        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                    }
                }
            }
        }
    }

    /**
     * 给bean注入成员属性
     *
     * @param beanName       bean名称
     * @param bean           bean实例
     * @param beanDefinition bean定义
     */
    private void applyPropertyValue(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {

                String name = propertyValue.getName();
                Object value = propertyValue.getValue();
                if (value instanceof BeanReference) {
                    // A依赖B，获取B的实例化
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                // 属性填充
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeanException("Error setting property values: " + beanName, e);
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return this.instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    /**
     * 实例化Bean的方法, 执行顺序:beforeBeanProcess -> invokeInit -> afterBeanProcess
     *
     * @param beanName       beanName
     * @param bean           bean
     * @param beanDefinition beanDefinition
     * @return after all process bean
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // invokeAwareMethods
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }
        // 1.执行before处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // 2.调用用户定义初始化函数
        try {
            invokeInitMethod(wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeanException("Invocation of init method of bean[" + beanName + "] failed", e);
        }

        // 3.执行after处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        return wrappedBean;
    }

    /**
     * 由Spring托管的Bean对象初始化方法,类似于bean的构造函数,不过是交给spring托管的
     *
     * @param bean           bean
     * @param beanDefinition beanDefinition
     */
    private void invokeInitMethod(Object bean, BeanDefinition beanDefinition) throws Exception {
        // 实现了InitializingBean接口，才调用初始化方法
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        } else {
            // 或者xml中配置了初始化方法名称
            String initMethodName = beanDefinition.getInitMethodName();
            if (StrUtil.isNotEmpty(initMethodName)) {
                Method method = beanDefinition.getBeanClass().getMethod(initMethodName);
                method.invoke(bean);
            }
        }
    }

    /**
     * 将实现了DisposableBean接口的bean注册到DefaultSingletonBeanRegistry中,当spring进入生命销毁周期时,执行对应的destroy方法.
     *
     * @param beanName       beanName
     * @param bean           bean
     * @param beanDefinition beanDefinition
     */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 非Singleton类型的Bean不执行销毁方法
        if (!beanDefinition.isSingleton()) {
            return;
        }
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanDefinition));
        }
    }

    /**
     * 如果bean实现了InstantiationAwareBeanPostProcessor接口,调用其postProcessBeforeInstantiation方法.
     * 这里主要是针对bean的实例化动作,主要应用场景的aop下的类代理
     *
     * @param beanClass beanClass
     * @param beanName  beanName
     * @return after process bean
     */
    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            if (processor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) processor).postProcessBeforeInstantiation(beanClass, beanName);
                if (null != result) return result;
            }
        }
        return null;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeanException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeanException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (null == current)
                return result;
            result = current;
        }
        return result;
    }
}
