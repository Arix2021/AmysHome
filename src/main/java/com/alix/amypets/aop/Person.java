package com.alix.amypets.aop;

import jdk.internal.util.xml.impl.Input;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.InputStreamReader;
import java.util.*;

public class Person {
    public static void main(String[] args) {
        HashMap<Integer, int[]> res = new HashMap<>();
        int count = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) {
                    if ((i + j * 2 + k * 5) == 246) {
                        res.put(count++,new int[] {i,j,k});
                    }
                }
            }
        }
        TreeMap<Integer, int[]> treeMap = new TreeMap<>(res);
        for (Map.Entry<Integer, int[]> entry : treeMap.entrySet()) {
            System.out.println(entry.getKey() + "\t" + Arrays.toString(Arrays.stream(entry.getValue()).toArray()));
        }
        System.out.println(res.size());
    }
}
