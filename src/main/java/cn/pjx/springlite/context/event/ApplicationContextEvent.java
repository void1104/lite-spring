package cn.pjx.springlite.context.event;

import cn.pjx.springlite.context.ApplicationContext;
import cn.pjx.springlite.context.ApplicationEvent;

/**
 * 定义事件的抽象类,所有包括关闭,刷新,以及用户自己实现的事件,都需要继承这个类
 */
public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * Get the ApplicationContext that the event was raised for.
     */
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
