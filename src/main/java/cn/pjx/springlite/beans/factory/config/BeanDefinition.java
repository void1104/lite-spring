package cn.pjx.springlite.beans.factory.config;

import cn.hutool.core.util.StrUtil;
import cn.pjx.springlite.beans.PropertyValues;

/**
 * 存放spring bean的类信息
 * <p>
 * - 此类只存放类的定义和不存放实例
 */
public class BeanDefinition {

    private final String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

    private String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    /**
     * beanClass
     */
    private Class beanClass;

    /**
     * bean属性对象列表
     */
    private PropertyValues propertyValues;

    /**
     * 初始化方法
     */
    private String initMethodName;

    /**
     * 销毁方法
     */
    private String destroyMethodName;

    /**
     * bean实例类型(单例/原型)
     */
    private String scope = SCOPE_SINGLETON;

    private boolean singleton = true;

    private boolean prototype = false;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    public void setScope(String scope) {
        if (StrUtil.isEmpty(scope)) {
            return;
        }
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    public boolean isSingleton() {
        return singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }
}
