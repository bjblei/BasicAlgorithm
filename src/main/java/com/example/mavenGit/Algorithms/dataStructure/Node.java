package com.example.mavenGit.Algorithms.dataStructure;

import com.example.mavenGit.Algorithms.SortUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leibao on 2018/4/29.
 */
public class Node {
    private Object value;
    private Node next;

    public Node(Object value, Node next) {
        this.value = value;
        this.next = next;
    }

    public static Node buildSingleLinkList(Object[] value) {
        Node head = null;
        if (null == value || value.length == 0) {
            return head;
        }
        for (int i = value.length - 1; i >= 0; i--) {
            Node node = new Node(value[i], head);
            head = node;
        }
        return head;
    }

    public static Object[] getSingleListValues(Node head) {
        List<Object> list = new ArrayList<>();
        while (null != head) {
            Object value = head.value;
            if (null != value) {
                list.add(value);
            }
            head = head.next;
        }
        return list.toArray();
    }

    /**
     * 单链表反转
     *
     * @param head
     * @return
     */
    public static Node reverseSingleLinkedList(Node head) {
        if (null == head || null == head.next) {
            return head;
        }
        Node current = head.next;

        Node next = current.next;
        head.next = null;//头节点特殊处理

        current.next = head;
        head = current;
        while (null != next) {
            //current就是当前操作节点，要么是旧链表的头／要么是新链表的头
            current = next;
            next = current.next;
            current.next = head;
            head = current;
        }
        return head;
    }

    public static void main(String[] args) {
        int[] arrays = new int[]{1, 22, 38, 4, 5};
        System.out.print("原始数组:");
        SortUtils.printArray(arrays);
        System.out.println("");
        Integer[] node = new Integer[arrays.length];
        for (int i = 0; i < arrays.length; i++) {
            node[i] = arrays[i];
        }
        Node head = Node.buildSingleLinkList(node);
        System.out.print("单链表:");

        SortUtils.printArray(Node.getSingleListValues(head));
        System.out.println("");

        Node reverHead = Node.reverseSingleLinkedList(head);
        System.out.print("反转后的单链表:");

        SortUtils.printArray(Node.getSingleListValues(reverHead));
        System.out.println("");

    }
}
