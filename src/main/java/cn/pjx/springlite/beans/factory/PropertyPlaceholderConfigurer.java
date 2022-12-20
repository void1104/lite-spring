package cn.pjx.springlite.beans.factory;

import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.PropertyValue;
import cn.pjx.springlite.beans.PropertyValues;
import cn.pjx.springlite.beans.factory.config.BeanDefinition;
import cn.pjx.springlite.beans.factory.config.BeanFactoryPostProcessor;
import cn.pjx.springlite.core.io.DefaultResourceLoader;
import cn.pjx.springlite.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 *
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeanException {
        // 加载属性文件
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    if (!(value instanceof String))
                        continue;
                    String strVal = (String) value;
                    StringBuilder buffer = new StringBuilder(strVal);
                    int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                    int stopIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                    // 也就是说,不是spring托管的对象中不能使用@Value注解.
                    if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
                        // 把bean对象中${token}替换成配置文件里token=xxx中xxx的值.
                        String propKey = strVal.substring(startIdx + 2, stopIdx);
                        String propVal = properties.getProperty(propKey);
                        buffer.replace(startIdx, stopIdx + 1, propVal);
                        // TODO 替换实际的值, 这里不会出现重复的propertyValue吗?
                        propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), buffer.toString()));
                    }
                }
            }
        } catch (IOException e) {
            throw new BeanException("Could not load properties", e);
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
