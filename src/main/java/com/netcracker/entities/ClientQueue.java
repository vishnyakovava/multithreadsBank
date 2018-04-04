package com.netcracker.entities;


import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ClientQueue extends Thread {

    private int countClients;
    private List<Worker> list;

    public ClientQueue(int countClients, List<Worker> list){
        setCountClients(countClients);
        setList(list);

    }

    @Override
    public void run(){

        Random rand = new Random();
        for(int i=0; i<countClients; i++){
            try {
                Thread.sleep(rand.nextInt(5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Client client = new Client();
            System.out.println();
            System.out.println("New client came to the queue.");
            client.getInformation();

            int index = 0;
            for (int j = 0; j <list.size()-1; j++) {
//                if(list.get(j).getCountClients() < count){
//                    count = list.get(j).getCountClients();
//                    index = j;
//                }

                if(list.get(j).getCountClients() > list.get(j+1).getCountClients())
                {
                    index = j+1;
                }
            }

            list.get(index).addClientsToQueue(client);
            System.out.println("Worker "+ list.get(index).getWorkerName() +" added to queue client with id: " + client.getId() + ". Processed: " + list.get(index).getCounter());
        }
    }

    public int getCountClients() {
        return countClients;
    }

    public void setCountClients(int countClients) {
        this.countClients = countClients;
    }

    public List<Worker> getList() {
        return list;
    }

    public void setList(List<Worker> list) {
        this.list = list;
    }
}
