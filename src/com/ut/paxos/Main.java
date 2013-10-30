package com.ut.paxos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class Main {

    private static Set<PaxosNode> nodes;
    private static Set<Account> accounts;
    private static Set<NodeData> nodeDatas;

    public static void main(String [] args) throws IOException {

        nodes = new HashSet<PaxosNode>();
        accounts = new HashSet<Account>();
        nodeDatas = new HashSet<NodeData>();

        System.out.println("type help to get list of commands");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            String[] s = in.readLine().split(" ", 2);
            String cmd = s[0];

            if(cmd.equalsIgnoreCase("cnodes"))
            {
                if(s[1] !=null)
                    createNodes(Integer.parseInt(s[1]));
            }
            else if(cmd.equalsIgnoreCase("cacc"))
            {
                if(s.length > 1)
                    createAccounts(Integer.parseInt(s[1]));
            }
            else if(cmd.equalsIgnoreCase("start"))
            {
                if(s.length > 1)
                    startNode(Integer.parseInt(s[1]));
                else
                    startAllNodes();
            }

            else if(cmd.equalsIgnoreCase("CMD"))
            {
                //CMD ACCOUNT_NUM W 12
                if(s[1] != null){
                    String [] t = s[1].split(" ");
                    Account actionAccount = null;
                    for(Account account: accounts){
                        if(account.getClientn() == Integer.parseInt(t[0])){
                            actionAccount = account;
                            break;
                        }
                    }
                    if(actionAccount != null){
                        AccountAction command = createCommand(actionAccount, t);
                        propose(command);
                    }else{
                        System.out.println("Invalid account number.");
                    }
                }
            }

            else if(cmd.equalsIgnoreCase("exit"))
            {
                System.exit(0);
            }

            else if(cmd.equalsIgnoreCase("help"))
            {
                String m = "";
                m += "List of valid commands:";
                m += "\n\tcnodes [<num>] - create n nodes";
                m += "\n\tcacc [<num>]- create n accounts";
                m += "\n\tstart [<num>]- start the nodes";
                m += "\n\texit - exit the program";
                m += "\n\thelp - displays this list";
                System.err.println(m);
            }

            else
            {
                System.err.println("******** Invalid Command *******");
            }
        }
    }

    private static AccountAction createCommand(Account account, String[] command){
        AccountAction accountAction = null;
        if(command[1].equalsIgnoreCase("w")){
            accountAction = new Withdraw(account, Integer.parseInt(command[2]));
        }
        return accountAction;
    }

    private static void createAccounts(int numAccounts) {
        for (int j = 0; j < numAccounts; j++) {
            accounts.add(new Account(j, 100));
        }
    }

    private static void propose(AccountAction command){
        for(PaxosNode node : nodes){
            wrinteOnConsole("proposing command for" + command.getAccount().getClientn(), false);
            if(node.getNodeData().isLeader()){
                node.propose(command);
            }
        }

    }


    private static void startNode(int nodeNum) {
        for(PaxosNode node : nodes){
            if(node.getNodeData().getNodeId() == nodeNum)
                node.start();
        }
        wrinteOnConsole(nodeNum + " started", false);
    }
    private static void startAllNodes(){
        for(PaxosNode node : nodes){
            node.start();

            wrinteOnConsole("starting node "+node.getNodeData().getNodeId(), false);
        }
    }

    private static void createNodes(int numOfNodes) {
        int port = 1056;
        for (int i = 0; i < numOfNodes; i++) {
            PaxosNode node = new PaxosNode("localhost", port++, i);
            if(node.getNodeData().getNodeId() == 0){
                node.getNodeData().setLeader(true); //make node number 0, an initial leader.
            }
            nodes.add(node);
            nodeDatas.add(node.getNodeData());
        }

        for(PaxosNode node : nodes){
            node.setNodes(nodeDatas);
        }
        wrinteOnConsole(numOfNodes + " nodes created", false);
    }

    private static void wrinteOnConsole(String s, boolean isError)
    {
        PrintStream out = isError ? System.err : System.out;
        out.print("*** ");
        out.print(s);
        out.println(" ***");
    }
}
