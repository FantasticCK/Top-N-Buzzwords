package com.CK;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        new Solution().topNToys(6, 2, new String[]{"elmo", "elsa", "legos", "drone", "tablet", "warcraft"}, 6,
                new String[]{"Elmo is the hottest of the season! Elmo will be on every kid's wishlist!",
                        "The new Elmo dolls are super high quality",
                        "Expect the Elsa dolls to be very popular this year, Elsa!",
                        "Elsa and Elmo are the toys I'll be buying for my kids, Elsa is good",
                        "For parents of older kids, look into buying them a drone",
                        "Warcraft is slowly rising in popularity ahead of the holiday season"}
                );
    }
}

class Solution {
    class Node {
        Set<Integer> quoteIdxSet;
        int freq;
        String name;
        Node() {
            name = "";
            quoteIdxSet = new HashSet<>();
            freq = 0;
        }
    }

    public List<String> topNToys(int numToys, int topToys, String[] toys, int numQuotes, String[] quotes) {
        Map<String, Integer> toyToIndex = new HashMap<>();
        Node[] nodes = new Node[numToys];
        for (int i = 0; i < toys.length; i++) {
            toyToIndex.put(toys[i], i);
            nodes[i] = new Node();
            nodes[i].name = toys[i];
        }
        for (int qIdx = 0; qIdx < quotes.length; qIdx++) {
            String[] quoteArr = quotes[qIdx].split(" ");
            for (int i = 0; i < quoteArr.length; i++) {
                String word = quoteArr[i].replaceAll("\\W+", "").toLowerCase();
                if (!word.equals("") && toyToIndex.containsKey(word)) {
                    nodes[toyToIndex.get(word)].quoteIdxSet.add(qIdx);
                    nodes[toyToIndex.get(word)].freq++;
                }
            }
        }

        PriorityQueue<Node> heap = new PriorityQueue<>((n1, n2) -> n1.freq != n2.freq ?
                n2.freq - n1.freq :
                n2.quoteIdxSet.size() - n1.quoteIdxSet.size() != 0 ?
                        n2.quoteIdxSet.size() - n1.quoteIdxSet.size() :
                        n1.name.compareTo(n2.name)
        );
        for (Node node : nodes) {
            heap.offer(node);
        }
        List<String> res = new ArrayList<>();
        for (int i = 0; i < topToys; i++) {
            if (!heap.isEmpty()) {
                res.add(heap.poll().name);
            }
        }
        return res;
    }
}
