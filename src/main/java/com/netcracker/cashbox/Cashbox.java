package com.netcracker.cashbox;


public class Cashbox {
    private static volatile Cashbox instance;
    private int cash;

    private Cashbox(){
        setCash(1000);
    }

    public static Cashbox getInstance(){
        Cashbox newInstance = instance;
        if(newInstance == null){
            synchronized (Cashbox.class){
                newInstance=instance;
                if(newInstance == null){
                    instance = newInstance = new Cashbox();
                }
            }
        }
        return newInstance;
    }

    /* ///////////////// */
    public synchronized int putMoney(int deposit) throws InterruptedException {
        cash+=deposit;
        return cash;
    }


    /* ////////////////// */
    public synchronized int takeMoney(int deposit) throws InterruptedException {
        if(cash>=deposit)
            cash-=deposit;
        else
            System.out.println("Not enough money in cashbox!");
        return cash;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }


}
