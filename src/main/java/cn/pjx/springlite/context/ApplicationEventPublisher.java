package cn.pjx.springlite.context;

/**
 * 事件发布者接口
 * - 所有的事件都需要从这个接口发布出去
 */
public interface ApplicationEventPublisher {

    /**
     * Notify all listeners registered with this application of an application
     * event. Events may be framework events (such as RequestHandledEvent)
     * or application-specific events.
     * @param event the event to publish
     */
    void publishEvent(ApplicationEvent event);
}
