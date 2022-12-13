package cn.pjx.springlite;

import cn.pjx.springlite.beans.factory.DisposableBean;
import cn.pjx.springlite.beans.factory.InitializingBean;

public class UserService implements InitializingBean, DisposableBean {

    private String username;

    private UserDao userDao;

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
}
