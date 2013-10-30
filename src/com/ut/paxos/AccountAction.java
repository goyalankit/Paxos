package com.ut.paxos;

public class AccountAction {
    Account account;

    public Account getAccount() {
        return account;
    }
}

class Withdraw extends AccountAction {
    private int wdamount;

    public Withdraw(Account account, int amount){
        this.account = account;
        this.wdamount = amount;
    }

    int getWdamount() {
        return wdamount;
    }
}

class Deposit extends AccountAction {
    private int dpamount;
    public Deposit(Account account, int amount){
        this.account = account;
        this.dpamount = amount;
    }


}