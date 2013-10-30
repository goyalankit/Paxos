package com.ut.paxos;

import java.io.Serializable;

public abstract class Message implements Serializable
{
    protected NodeData source;
    protected NodeData destination;

    public NodeData getSource() {
        return source;
    }

    public void setSource(NodeData source) {
        this.source = source;
    }

    public NodeData getDestination() {
        return destination;
    }

    public void setDestination(NodeData destination) {
        this.destination = destination;
    }
}
