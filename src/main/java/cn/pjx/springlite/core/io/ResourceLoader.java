package cn.pjx.springlite.core.io;

public interface ResourceLoader {

    /**
     * 类路径前缀
     */
    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 获取数据源
     *
     * @param location location
     * @return 数据源
     */
    Resource getResource(String location);
}
