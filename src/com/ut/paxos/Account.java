package com.ut.paxos;

import java.io.Serializable;

public class Account implements Serializable{
    private int clientn;
    private int currentBalance;

    public Account(int clientn, int currentBalance) {
        this.clientn = clientn;
        this.currentBalance = currentBalance;
    }

    public void perform(AccountAction action){
        if(action instanceof Withdraw){
            Withdraw wdAction = (Withdraw)action;
            if(currentBalance > wdAction.getWdamount()){
                currentBalance = currentBalance - wdAction.getWdamount();
                System.out.println("AccountAction: "+clientn+" amount of "+wdAction.getWdamount()+ " withdrawn");
            }else{
                System.out.println("AccountAction: Withdraw Failed. Insufficient Balance");
            }
        }
    }

    public int getClientn() {
        return clientn;
    }

    public void setClientn(int clientn) {
        this.clientn = clientn;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(int currentBalance) {
        this.currentBalance = currentBalance;
    }
}
