package com.redes.japo.updmulticast;

/**
 * Created by Japo on 30/03/2017.
 */

import java.io.Serializable;

public class AutoIDMessage implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String content;

    public AutoIDMessage(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}