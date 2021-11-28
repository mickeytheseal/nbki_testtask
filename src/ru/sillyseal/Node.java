package ru.sillyseal;

public class Node {
    int key, height;
    String data;
    Node left, right;

    Node(int d) {
        key = d;
        height = 1;
    }
}
