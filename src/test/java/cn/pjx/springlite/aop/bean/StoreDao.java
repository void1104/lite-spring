package cn.pjx.springlite.aop.bean;

import cn.pjx.springlite.beans.factory.annotation.Autowired;
import cn.pjx.springlite.beans.factory.annotation.Qualifier;
import cn.pjx.springlite.beans.factory.annotation.Value;
import cn.pjx.springlite.stereotype.Component;

@Component("store")
public class StoreDao {

    @Value("${store.name}")
    private String storeName;

    @Autowired
    @Qualifier("user")
    private UserDao userDao;

    public String getUser() {
        return storeName + ":" + userDao.getUsername();
    }
}
