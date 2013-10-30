package com.ut.paxos;

/**
 * Created with IntelliJ IDEA.
 * User: ankit
 * Date: 10/30/13
 * Time: 12:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class P1aMessage extends Message {
    String value;

    public P1aMessage(NodeData source, NodeData destination, String value) {
        super(source, destination);
        this.value = value;
    }

}
