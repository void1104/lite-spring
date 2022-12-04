package cn.pjx.springlite.beans.factory;

/**
 * bean工厂接口
 */
public interface BeanFactory {

    /**
     * 获取bean实例
     *
     * @param name bean注册名称
     * @return bean实例
     */
    Object getBean(String name);

    /**
     * 获取bean实例
     *
     * @param name bean注册名称
     * @param args 构造函数入参
     * @return bean实例
     */
    Object getBean(String name, Object... args);
}
