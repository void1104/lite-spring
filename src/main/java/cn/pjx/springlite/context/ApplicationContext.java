package cn.pjx.springlite.context;

import cn.pjx.springlite.beans.factory.ListableBeanFactory;
import cn.pjx.springlite.core.io.ResourceLoader;

public interface ApplicationContext extends ListableBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
