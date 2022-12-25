package cn.pjx.springlite.design;

/**
 * @author pengjiaxin3
 * @description
 * @date 12/25/22 12:57 PM
 */
public class Wife {

    private Husband husband;

    public String queryHusband() {
        return "Wife.husband、Mother.callMother：" + "111";
    }

    public Husband getHusband() {
        return husband;
    }

    public void setHusband(Husband husband) {
        this.husband = husband;
    }
}
