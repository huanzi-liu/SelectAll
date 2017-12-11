package com.example.administrator.selectall;

import java.io.Serializable;

/**
 * @author HuanZi
 * @date 2017\11\13 0013
 * @explain ${text}
 */
public class bean implements Serializable {

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    private boolean isChecked;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    String string;

}
