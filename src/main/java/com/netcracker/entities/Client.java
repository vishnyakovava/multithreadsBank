package com.netcracker.entities;

import java.util.Random;

public class Client {

    private  int id;
    private int timeForOperation;
    private boolean operation;
    private int amount;
    public Random rand = new Random();

    public Client(int id, int amount, int timeForOperation, boolean operation) {
        setId(id);
        setAmount(amount);
        setTimeForOperation(timeForOperation);
        setOperation(operation);
    }

    public Client(){
        setId(rand.nextInt(100));
        setAmount(rand.nextInt(500));
        setTimeForOperation(rand.nextInt(5000)+3000);
        setOperation(rand.nextBoolean());
    }

    public void getInformation(){
        String oper = operation == true ? "take" : "put";
        System.out.println("Client id: "+getId() + ", operation time: " + getTimeForOperation() + ", money amount: " + getAmount() + ", operation: " + oper);
    }

    @Override
    public String toString() {
        return "Client id: "+getId() + ", operation time: " + getTimeForOperation() + ", money amount: " + getAmount() + ", operation: " + isOperation();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeForOperation() {
        return timeForOperation;
    }

    public void setTimeForOperation(int timeForOperation) {
        this.timeForOperation = timeForOperation;
    }

    public boolean isOperation() {
        return operation;
    }

    public void setOperation(boolean operation) {
        this.operation = operation;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
