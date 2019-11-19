package graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private class Node {

        private int data;
        private int weight;

        public Node(int dt, int weight) {
            data = dt;
            this.weight = weight;
        }
    }
    private ArrayList<Node>[] adj;
    private int jumlah;

    public Graph(int n) {
        adj = new ArrayList[n];
        jumlah = n;
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList();
        }
    }

    public void addAdj(int asal, int akhir, int weight) {
        Node n = new Node(akhir, weight);
        adj[asal].add(n);
    }

    public void cetak(String komentar) {
        System.out.println(komentar);

    }

    public void cariJarakMin(int s, int e) {
        boolean[] isVisited = new boolean[jumlah];
        ArrayList<Integer> pathList = new ArrayList<>();
        ArrayList<Node> pathWeight = new ArrayList<>();
        ArrayList<Integer> minPath = new ArrayList<>();
        pathWeight.add(new Node(s, 0));
        int min[] = new int[1];
        min[0] = Integer.MAX_VALUE;
        jarakMin(s, e, isVisited, pathWeight, min, minPath);
        System.out.println(minPath);
    }

    public void jarakMin(int start, int end, boolean[] isVisited, List<Node> path, int[] min, List<Integer> minPath) {
        isVisited[start] = true;
        if (start == end) {
            int sum = 0;
            for (Node n : path) {
                sum += n.weight;
            }
            if (sum < min[0]) {
                min[0] = sum;
                minPath.removeAll(minPath);
                for(Node n : path){
                    minPath.add(n.data);
                }
            }
            isVisited[start] = false;
            return;
        }
        for (Node n : adj[start]) {
            if (!isVisited[n.data]) {
                int i = n.data;
                path.add(n);
                jarakMin(i, end, isVisited, path, min, minPath);
                path.remove(n);
            }
        }
        isVisited[start] = false;
    }

    public static void main(String[] args) {
        Graph g = new Graph(7);
        g.addAdj(0, 1, 5);
        g.addAdj(0, 2, 2);
        g.addAdj(1, 3, 4);
        g.addAdj(1, 4, 5);
        g.addAdj(1, 6, 2);
        g.addAdj(2, 3, 3);
        g.addAdj(2, 4, 7);
        g.addAdj(3, 5, 4);
        g.addAdj(4, 5, 6);
        g.addAdj(6, 5, 4);
        g.cariJarakMin(0, 4);
    }
}
