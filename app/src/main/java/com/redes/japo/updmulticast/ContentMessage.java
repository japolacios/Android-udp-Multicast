package com.redes.japo.updmulticast;

/**
 * Created by Japo on 1/04/2017.
 */

import java.io.Serializable;

public class ContentMessage implements Serializable{
    /**
     * Sender's ID
     */
    private int sender;
    /**
     * Receiver's ID
     */
    private int receiver;
    private int type;
    private int score;

    public ContentMessage(int _sender, int _receiver, int _type, int _score) {
        sender = _sender;
        receiver = _receiver;
        score = _score;
        type = _type;
    }

    public int getSender() {
        return sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public int getType(){
        return type;
    }

    public int getScore(){
        return score;
    }

}
