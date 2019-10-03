package livecodingasd;

import java.util.ArrayList;
import java.util.Scanner;

class Node {

    private Process process;
    Node next;

    public Node(Process process) {
        this.process = process;
    }

    public void execute(int quantumTime) {
        this.process.execute(quantumTime);
    }

    public int checkBurstTIme() {
        return this.process.getBurstTime();
    }

}

class Process {

    private String name;
    private int burstTime;

    public Process(String name, int burstTime) {
        this.name = name;
        this.burstTime = burstTime;
    }

    public String getName() {
        return name;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void execute(int quantumTime) {
        int exc = 0;
        if (this.burstTime - quantumTime >= 0) {
            exc = quantumTime;
            this.burstTime -= quantumTime;
        } else {
            exc = this.burstTime;
            this.burstTime = 0;
        }
        System.out.printf("| %-15s | %-26s | %-15s |\n", name, exc, this.burstTime);
    }

}

class CircularLinkedList {

    private Node head = null;
    private Node tail = null;
    private int size;
    private int quantumTime;

    public CircularLinkedList(ArrayList<Process> processes, int quantumTime) {
        this.quantumTime = quantumTime;
        this.size = 0;
        this.initialization(processes);
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void add(Node add) {
        if (isEmpty()) {
            head = add;
            tail = add;
            tail.next = head;
        } else {
            add.next = head;
            tail.next = add;
            tail = add;
        }
        size++;
    }

    public void remove(Node remove) {
        Node temp = head;
        while (true) {
            if (temp.next == remove) {
                Node nexxt = temp.next.next;
                if (temp.next == head) {
                    head = nexxt;
                }
                if (temp.next == tail) {
                    tail = nexxt;
                }
                temp.next = nexxt;

                size--;
                break;
            }
            temp = temp.next;
        }
    }

    public void initialization(ArrayList<Process> processes) {
        processes.forEach(process -> {
            Node add = new Node(process);
            this.add(add);
        });
    }

    public void run() {
        System.out.printf("| %-15s | %-26s | %-15s |\n", "Nama Proses", "Burst Time yang Dieksekusi",
                "Sisa Burst Time");
        System.out.println("------------------------------------------------------------------");
        Node temp = head;
        while (size != 0) {
            if (temp.checkBurstTIme() != 0) {
                temp.execute(quantumTime);
            } else {
                remove(temp);
            }
            temp = temp.next;
        }
    }

}

public class RoundRobin {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner sc = new Scanner(System.in);
        ArrayList<Process> listofprocess = new ArrayList<Process>();
        int countProccess = sc.nextInt();
        int quantumTime = sc.nextInt();
        for (int i = 0; i < countProccess; i++) {
            String pName = sc.next();
            int bTime = sc.nextInt();
            listofprocess.add(new Process(pName, bTime));
        }
        CircularLinkedList listOfProcess = new CircularLinkedList(listofprocess, quantumTime);
        listOfProcess.run();

        sc.close();
    }
}