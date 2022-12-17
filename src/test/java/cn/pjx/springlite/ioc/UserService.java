package cn.pjx.springlite.ioc;

import cn.pjx.springlite.beans.BeanException;
import cn.pjx.springlite.beans.factory.*;
import cn.pjx.springlite.context.ApplicationContext;
import cn.pjx.springlite.context.ApplicationContextAware;

public class UserService implements InitializingBean, DisposableBean, BeanFactoryAware, BeanNameAware, ApplicationContextAware {

    private String username;

    private IUserDao userDao;

    private BeanFactory beanFactory;

    private String beanName;

    private ApplicationContext applicationContext;

    UserService() {

    }

    UserService(String username) {
        this.username = username;
    }

    public String queryUserInfo() {
        if (username != null) {
            return userDao.search(username);
        }
        return "search for broadcast!!!";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("userService is destroy!!!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("userService is init!!!");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeanException {
        this.applicationContext = applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public String getBeanName() {
        return beanName;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
