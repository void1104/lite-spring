package cn.pjx.springlite.context.event;

import cn.pjx.springlite.beans.factory.BeanFactory;
import cn.pjx.springlite.context.ApplicationEvent;
import cn.pjx.springlite.context.ApplicationListener;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    /**
     * 广播发送事件给到事件关心的监听者
     */
    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
