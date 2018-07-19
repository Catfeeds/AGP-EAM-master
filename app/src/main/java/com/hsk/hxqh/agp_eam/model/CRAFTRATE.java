package com.hsk.hxqh.agp_eam.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 * 工种技能
 */

public class CRAFTRATE implements Serializable {
    public String CRAFT;//
    public String DESCRIPTION;//
    public String SKILLLEVEL;

    public String getCRAFT() {
        return CRAFT;
    }

    public void setCRAFT(String CRAFT) {
        this.CRAFT = CRAFT;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getSKILLLEVEL() {
        return SKILLLEVEL;
    }

    public void setSKILLLEVEL(String SKILLLEVEL) {
        this.SKILLLEVEL = SKILLLEVEL;
    }
}
