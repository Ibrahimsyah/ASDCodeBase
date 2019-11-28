package graph;

import java.util.ArrayList;
import java.util.Scanner;

class Node {

    Node next;
    int id;
    String name;
    ArrayList<String> tweets = new ArrayList<>();

    public Node(int id, String name, Node next) {
        this.id = id;
        this.name = name;
        this.next = next;
    }

    public Node(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

public class Twittard {

    private Node[] node;
    private int jNode;

    public Twittard(int j) {
        node = new Node[j];
        jNode = j;
    }

    public void addEdge(int id, String name, int head) {
        Node n = new Node(id, name, node[head]);
        node[head] = n;
    }

    public void follow(int id, int follow, boolean isMessage) {
        Node followed = node[follow];
        while (followed != null) {
            if (followed.id == id) {
                System.out.println(ANSI_RED + "ERROR: ANDA TELAH MENGIKUTI AKUN INI" + ANSI_RESET);
                return;
            }
            followed = followed.next;
        }
        Node follower = node[id];
        while (follower.next != null) {
            follower = follower.next;
        }
        Node n = new Node(follower.id, follower.name, node[follow]);
        node[follow] = n;
        Node temp = node[follow];
        while (temp.next != null) {
            temp = temp.next;
        }
        if (isMessage) {
            System.out.println(ANSI_GREEN + "SUCCESS: Berhasil Mengikuti " + temp.name + ANSI_RESET);
        }
    }

    public void unfollow(int id, int unfoll) {
        Node unfollowed = node[unfoll];
        if (unfollowed.id == id) {
            node[unfoll] = unfollowed.next;
            Node temp = node[unfoll];
            while (temp.next != null) {
                temp = temp.next;
            }
            System.out.println(ANSI_GREEN + "SUCCESS: Berhasil Unfollow " + temp.name + ANSI_RESET);
        } else {
            while (unfollowed.next != null) {
                if (unfollowed.next.id == id) {
                    unfollowed.next = unfollowed.next.next;
                    Node temp = node[unfoll];
                    while (temp.next != null) {
                        temp = temp.next;
                    }
                    System.out.println(ANSI_GREEN + "SUCCESS: Berhasil Unfollow " + temp.name + ANSI_RESET);
                    return;
                }
                unfollowed = unfollowed.next;
            }
        }
    }

    public void daftar(int id, String name) {
        node[id] = new Node(id, name);
    }

    public void cetak() {
        for (int i = 0; i < jNode; i++) {
            System.out.print("[" + i + "]");
            Node n = node[i];
            while (n.next != null) {
                System.out.print("<-" + n.name);
                n = n.next;
            }
            System.out.println();
        }
    }

    public Node getNode(int n) {
        return node[n];
    }

    public void getFollower(int id) {
        int count = 0;
        System.out.println("==============");
        System.out.println("Follower Anda");
        Node temp = node[id];
        while (temp.next != null) {
            System.out.println(++count + ". " + temp.name);
            temp = temp.next;
        }
        System.out.println("Jumlah Followers: " + count);
        System.out.println("==============");
    }

    public void getFollowing(int id) {
        int count = 0;
        System.out.println("==============");
        System.out.println("Following Anda");
        for (int i = 0; i < jNode; i++) {
            if (i == id) {
                continue;
            }
            Node temp = node[i];
            while (temp != null) {
                if (temp.id == id) {
                    Node followed = node[i];
                    while (followed.next != null) {
                        followed = followed.next;
                    }
                    System.out.println(++count + ". " + followed.name + " (ID: " + followed.id + ")");
                }
                temp = temp.next;
            }
        }
        System.out.println("Jumlah Following: " + count);
        System.out.println("==============");
    }

    public void tweet(int id, String tweet) {
        Node acc = node[id];
        while (acc.next != null) {
            acc = acc.next;
        }
        acc.tweets.add(tweet);
    }

    public void showTweets(int id) {
        Node acc = node[id];
        while (acc.next != null) {
            acc = acc.next;
        }
        System.out.println("Tweet anda: ");
        int count = 0;
        for (String a : acc.tweets) {
            System.out.println("\t" + ++count + ". " + a);
        }
        System.out.println("Total Tweets: " + count);
    }

    public void printAllUser() {
        for (int i = 0; i < jNode; i++) {
            if (i == 0) {
                continue;
            }
            Node temp = node[i];
            while (temp.next != null) {
                temp = temp.next;
            }
            System.out.println(temp.name + " \tID = " + temp.id);
        }
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    public static void main(String[] args) {

        Twittard graph = new Twittard(3);
        Scanner in = new Scanner(System.in);
        graph.daftar(0, "Ibrahim");
        graph.daftar(1, "Azhari");
        graph.daftar(2, "Fulan");
        graph.follow(2, 0, false);
        graph.follow(1, 0, false);
        boolean nagih = true;
        while (nagih) {
            System.out.println("== Twitter KW ==");
            System.out.println("[1]Cek Follower dan Following \t[2]Follow Seseorang");
            System.out.println("[3]Unfollow Seseorang \t\t[4]Tweet");
            System.out.println("[5]Lihat Tweet \t\t\t[6]Exit");
            System.out.print("Pilihan anda: ");
            int pilihan = in.nextInt();
            switch (pilihan) {
                case 1:
                    graph.getFollower(0);
                    graph.getFollowing(0);
                    break;
                case 2:
                    graph.printAllUser();
                    System.out.print("Ketik ID akun yang ingin anda follow: ");
                    int id = in.nextInt();
                    graph.follow(0, id, true);
                    break;
                case 3:
                    graph.getFollowing(0);
                    System.out.print("Ketik ID akun yang ingin anda unfollow: ");
                    int unfoll = in.nextInt();
                    graph.unfollow(0, unfoll);
                    break;
                case 4:
                    in.nextLine();
                    System.out.print("Masukkan Tweet anda: ");
                    String tweet = in.nextLine();
                    graph.tweet(0, tweet);
                    break;
                case 5:
                    graph.showTweets(0);
                    break;
                case 6:
                    nagih = !nagih;
            }
            System.out.println();
        }
    }
}
