package com.ut.paxos;

import java.io.Serializable;

public abstract class Message implements Serializable{

    protected NodeData source;
    protected NodeData destination;

    protected Message(NodeData source, NodeData destination) {
        this.source = source;
        this.destination = destination;
    }

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


class P1aMessage extends Message {
    int ballot_number;

    public P1aMessage(NodeData source, NodeData destination, int ballot_number) {
        super(source, destination);
        this.ballot_number = ballot_number;
    }
}

class P2aMessage extends Message {
    String value;

    public P2aMessage(NodeData source, NodeData destination, String value) {
        super(source, destination);
        this.value = value;
    }
}

class P1bMessage extends Message {
    String value;

    public P1bMessage(NodeData source, NodeData destination, String value) {
        super(source, destination);
        this.value = value;
    }
}

class P2bMessage extends Message {
    String value;

    public P2bMessage(NodeData source, NodeData destination, String value) {
        super(source, destination);
        this.value = value;
    }
}
