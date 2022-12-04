package cn.pjx.springlite.beans.factory.config;

import cn.pjx.springlite.beans.PropertyValues;

/**
 * 存放spring bean的类信息
 * <p>
 * - 此类只存放类的定义和不存放实例
 */
public class BeanDefinition {

    /**
     * beanClass
     */
    private Class beanClass;

    /**
     * bean属性对象列表
     */
    private PropertyValues propertyValues;

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
}
