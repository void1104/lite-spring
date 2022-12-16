package cn.pjx.springlite.event;

import cn.pjx.springlite.context.ApplicationListener;
import cn.pjx.springlite.context.event.ContextRefreshedEvent;

/**
 * 容器初始化/刷新事件监听器
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("刷新事件：" + this.getClass().getName());
    }
}
