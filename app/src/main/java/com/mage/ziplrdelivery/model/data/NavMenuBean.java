package com.mage.ziplrdelivery.model.data;

public class NavMenuBean {
    private int index;
    private String name;
    private int drawable;

    public NavMenuBean(int index, String name, int drawable) {
        this.index = index;
        this.name = name;
        this.drawable = drawable;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
