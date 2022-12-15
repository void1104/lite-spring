package cn.pjx.springlite.context.event;

import cn.pjx.springlite.context.ApplicationEvent;
import cn.pjx.springlite.context.ApplicationListener;

/**
 * 事件广播器
 */
public interface ApplicationEventMulticaster {

    /**
     * 添加监听类
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 移除监听类
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 广播事件
     */
    void multicastEvent(ApplicationEvent event);
}
