package com.ut.paxos;

public class Proposal {

    private int slotNum;
    private int ballotNum;
    private AccountAction accountAction;

    public Proposal(int slotNum, int ballotNum, AccountAction accountAction) {
        this.slotNum = slotNum;
        this.ballotNum = ballotNum;
        this.accountAction = accountAction;
    }

    public int getSlotNum() {
        return slotNum;
    }

    public void setSlotNum(int slotNum) {
        this.slotNum = slotNum;
    }

    public int getBallotNum() {
        return ballotNum;
    }

    public void setBallotNum(int ballotNum) {
        this.ballotNum = ballotNum;
    }

    public AccountAction getAccountAction() {
        return accountAction;
    }

    public void setAccountAction(AccountAction accountAction) {
        this.accountAction = accountAction;
    }
}
