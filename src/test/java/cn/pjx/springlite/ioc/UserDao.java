package cn.pjx.springlite.ioc;

import java.util.HashMap;
import java.util.Map;

public class UserDao {

    public static Map<String, String> db = new HashMap<>();

    static {
        db.put("A", "A's info");
        db.put("B", "B's info");
        db.put("C", "C's info");
        db.put("void", "void's info");
    }

    public String search(String name) {
        return db.get(name);
    }

    public void init() {
        System.out.println("userDao is init!!!");
    }

    public void destroy() {
        System.out.println("userDao is destroy!!!");
    }
}
