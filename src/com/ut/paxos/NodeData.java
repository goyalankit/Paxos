package com.ut.paxos;

import java.io.Serializable;

public class NodeData implements Serializable{

    private String hostName;
    private int port;
    private int nodeId;

    public NodeData(String hostName, int port, int nodeId) {
        this.hostName = hostName;
        this.port = port;
        this.nodeId = nodeId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }
}
