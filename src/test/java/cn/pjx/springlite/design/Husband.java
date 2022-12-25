package cn.pjx.springlite.design;

import cn.pjx.springlite.stereotype.Component;

/**
 * @author pengjiaxin3
 * @description
 * @date 12/25/22 12:56 PM
 */
@Component
public class Husband {

    private Wife wife;

    public String queryWife(){
        return "Husband.wife";
    }

    public Wife getWife() {
        return wife;
    }

    public void setWife(Wife wife) {
        this.wife = wife;
    }
}
