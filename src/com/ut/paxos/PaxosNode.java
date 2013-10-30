package com.ut.paxos;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Set;

public class PaxosNode {

    public NodeData getNodeData() {
        return nodeData;
    }

    /*
    *
    * Node Variables. Information about itself and other nodes in the system
    *
    * */
    private NodeData nodeData;
    private Set<NodeData> nodes;
    private boolean isLeader;

    private int currentSlotNumber;
    private int currentBallotNumber;



    public PaxosNode(String hostName, int port, int nodeId){
        this.nodeData = new NodeData(hostName, port, nodeId);
        this.currentBallotNumber = 0;
        this.currentSlotNumber = 0;
    }

    private class MessageListener extends Thread{
        private ServerSocket serverSocket;
        public MessageListener(){
            try {
                serverSocket = new ServerSocket(nodeData.getPort());
            } catch (IOException e) {
                e.printStackTrace(); //change message
            }
        }

        public void run() {
            Socket socket;
            ObjectInputStream oIn;
            while (true) {
                try {
                    socket = serverSocket.accept();
                    oIn = new ObjectInputStream(socket.getInputStream());
                    Message message = (Message) oIn.readObject();
                    if (message instanceof P1aMessage) {
                        P1aMessage p1aMessage = (P1aMessage) message;
                        System.out.println(nodeData.getNodeId() + ": Data received from " + p1aMessage.getSource().getNodeId());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    writeToConsole("IOException while trying read the socket!");
                } catch (ClassNotFoundException e) {
                    writeToConsole("Class not found while reading input stream!");
                }
            }
        }
    }

    private void sendMessageToAll(Message m){
        for(NodeData node: nodes){
            m.setDestination(node);
            if(node.getNodeId() == nodeData.getNodeId())
                deliver(m);
            else
                sendMessageToANode(node, m);
        }

    }

    private void deliver(Message m){
        System.out.println("Message received!");

    }

    private void sendMessageToANode(NodeData node, Message m)
    {
        Socket socket = null;
        ObjectOutputStream out = null;
        m.setDestination(node);

        try
        {
            socket = new Socket(node.getHostName(), node.getPort());
            socket.setSoTimeout(1000);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(m);
            out.flush();
        }
        catch(ConnectException e)
        {
            writeToConsole("Connection Exception!");
        }
        catch(SocketTimeoutException e)
        {
            writeToConsole("Socket timedout when connecting to host: "+node.getNodeId());

        }
        catch(IOException e)
        {
            e.printStackTrace();
            writeToConsole("IOException while sending message!");
        }
        finally
        {
            try
            {
                if(out != null)
                    out.close();
                if(socket != null)
                    socket.close();
            }
            catch(IOException e){}
        }
    }

    public void start(){
        MessageListener messageListener = new MessageListener();
        messageListener.start();
    }

    public void propose(AccountAction accountAction){
        propose(accountAction, currentSlotNumber++);
    }

    public void propose(AccountAction accountAction, int slotNumber){
        Proposal p = new Proposal(slotNumber, currentBallotNumber, accountAction);
        Message m = new P1aMessage(nodeData, null, currentBallotNumber);
        sendMessageToAll(m);
    }

    public Set<NodeData> getNodes() {
        return nodes;
    }

    public void setNodes(Set<NodeData> nodes) {
        this.nodes = nodes;
    }

    public void setNodeData(NodeData nodeData) {
        this.nodeData = nodeData;
    }

    public synchronized void writeToConsole(String message){
        System.err.println(message);
    }
}
