package cn.pjx.springlite.beans.factory.config;

/**
 * 存放spring bean的类信息
 *
 * - 此类只存放类的定义和不存放实例
 */
public class BeanDefinition {

    private Class beanClass;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
