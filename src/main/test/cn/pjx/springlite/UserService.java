package cn.pjx.springlite;

public class UserService {

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
}
