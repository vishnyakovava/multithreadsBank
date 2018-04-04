package com.netcracker;


import com.netcracker.entities.ClientQueue;
import com.netcracker.entities.Worker;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws InterruptedException {

        Worker worker = new Worker("John");
        Worker worker1 = new Worker("Mary");
        Worker worker2 = new Worker("Joanna");
        List<Worker> list = new ArrayList<>();
        list.add(worker);
        list.add(worker1);
        list.add(worker2);

        ClientQueue threads = new ClientQueue(15, list);

        for(int i=0; i<list.size(); i++){
            list.get(i).start();
        }

        threads.start();

        try {
            threads.join();
            Worker.setCheck(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
