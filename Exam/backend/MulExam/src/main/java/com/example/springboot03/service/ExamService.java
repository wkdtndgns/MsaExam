package com.example.springboot03.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExamService {
    public static Map<Integer, Integer[]> multiplicationTable;
    public ExamService() {
        int size = 9;
        multiplicationTable = new HashMap<>();
        int num = 1;
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                multiplicationTable.put(num, new Integer[]{i,j});
                num++;
            }
        }
    }

    public void multipleToString(){
        for (Map.Entry<Integer, Integer[]> entry : multiplicationTable.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + Arrays.toString(entry.getValue()));
        }
    }

    public static boolean checkResult(Integer key, Integer result){
        Integer[] arr = multiplicationTable.get(key);
        return result == (arr[0] * arr[1]);
    }
}
