package cn.pjx.springlite.event;

import cn.pjx.springlite.context.ApplicationListener;
import cn.pjx.springlite.context.event.ContextClosedEvent;

/**
 * 容器关闭事件监听器
 */
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("关闭事件:" + this.getClass().getName());
    }
}
