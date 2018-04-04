package com.netcracker.entities;

import com.netcracker.cashbox.Cashbox;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Worker extends Thread {
    private static final Cashbox cashBox = Cashbox.getInstance();
    private BlockingQueue<Client> queue = new ArrayBlockingQueue<Client>(30);
    private static boolean check;
    private String workerName;
    private int counter;

    public Worker(String name){
        setCheck(true);
        setWorkerName(name);
        setCounter(0);
    }

    public static void setCheck(boolean check) {
        Worker.check = check;
    }

    @Override
    public String toString() {
        return "Worker: " + workerName + ", in queue now: " + queue.size() + " clients.";
    }

    public int getCountClients(){
        return queue.size();
    }

    public void addClientsToQueue(Client client){
//        queue.add(client);
        synchronized (queue) {
            queue.add(client);
            queue.notify();
        }

    }

    public Client takeClient() throws InterruptedException {
        synchronized (queue){
            if(queue.isEmpty()){
                queue.wait();
            }
        }
        counter++;
        return  queue.take();
    }


    public synchronized int getMoney(int amount) throws InterruptedException {
        if(amount <= cashBox.getCash()){
            cashBox.takeMoney(amount);
//            System.out.println("Withdrawn: "+ amount);
        }else cashBox.takeMoney(amount);
//            else System.out.println("Not enough money at ATM!");
        return cashBox.getCash();
    }

    public synchronized int putMoney(int amount) throws InterruptedException {
        cashBox.putMoney(amount);
//        System.out.println("Credited: "+ amount);
        return cashBox.getCash();
    }

    public void startProcess(){
        while (!queue.isEmpty()){
            synchronized (cashBox){
                //текущее значение счета
                int currentCashBox = Cashbox.getInstance().getCash();
                try {
                    Client client = takeClient();
                    int amount = client.getAmount();
                    if(client.isOperation()){
                        if (currentCashBox < amount) {
                            System.out.println();
                            System.out.println(toString());
                            System.out.println("Client with id " + client.getId() + " can't take money. Not enough cash!");
                            System.out.println("In cashbox now: " + cashBox.getCash());

                        }

                        while (currentCashBox < amount){
                            Thread.sleep(100);
                        }

                        Thread.sleep(client.getTimeForOperation());
                        getMoney(client.getAmount());

                        System.out.println();
                        System.out.println(toString());
                        System.out.println("Client with id " + client.getId() + " successfully took " + client.getAmount() +". Operation time: " + client.getTimeForOperation());
                        System.out.println("Withdrawn: " + client.getAmount());
                        System.out.println("In cashbox now: " + cashBox.getCash());

                    } else {

                        Thread.sleep(client.getTimeForOperation());
                        putMoney(client.getAmount());

                        System.out.println();
                        System.out.println(toString());
                        System.out.println("Client with id " + client.getId() + " successfully put " + client.getAmount() +". Operation time: " + client.getTimeForOperation());
                        System.out.println("Credited: " + client.getAmount());
                        System.out.println("In cashbox now: " + cashBox.getCash());

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void run(){
        while (check){
            startProcess();
        }
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
