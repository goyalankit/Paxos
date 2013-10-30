package com.ut.paxos;

public class Main {

    public static void main(String [] args){
        PaxosNode paxosNode1 = new PaxosNode();
        PaxosNode paxosNode2 = new PaxosNode();

        //Node 1
        NodeData nodeData1 = new NodeData("localhost", 3123, 0);
        paxosNode1.setNodeData(nodeData1);

       //Node 2
        NodeData nodeData2 = new NodeData("localhost", 3154, 1);
        paxosNode2.setNodeData(nodeData2);

        paxosNode1.start(); //start listening
        Message m = new P1aMessage(nodeData2, nodeData1, "hello from node 2");
        paxosNode2.unicast(nodeData1 , m);

    }
}
