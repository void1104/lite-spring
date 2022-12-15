package cn.pjx.springlite.context.event;

/**
 * @author pengjiaxin3
 * @description
 * @date 12/15/22 2:59 PM
 */
public class ContextRefreshedEvent extends ApplicationContextEvent{
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
