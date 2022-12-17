package cn.pjx.springlite.ioc.event;


import cn.pjx.springlite.context.ApplicationListener;

public class CustomerEventListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("自定义事件: " + event.getSource() + event.getId() + "" + event.getMessage());
    }
}
