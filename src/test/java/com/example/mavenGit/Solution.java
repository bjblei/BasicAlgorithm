package com.example.mavenGit;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        if(null==nums||nums.length<3){
            return list;
        }
        int len = nums.length;

        HashMap<Integer,HashSet<Integer>> map = new HashMap<>();

        for(int i=0;i<len-2;i++){
            int first = nums[i];
            HashSet<Integer> result = map.get(first);

            if(!map.containsKey(first)||null==result){
                result=new HashSet<>();
                map.put(first,result);
            }




            result.add(first);//先加入,如果不符合条件,直接忽略，不加入list中

            int target = -first;//转换为2sum问题，只需要后两个数和为target 即可
            int[] temp_index = new int[1024];
            int count = temp_index.length-1;//下标范围
            int second = nums[i+1];
            boolean contain_second=false;
            HashSet<Integer> min = new HashSet<>();//去重

            inner:
            for(int j=i+2;j<len;j++){
                int left = target-nums[j];
                if(left==second &&!contain_second){
                    result.add(second);
                    result.add(nums[j]);
//                    Collections.sort(result);

//                    list.add(result);
//                    contain_second = true;
//
//                    result = new ArrayList<>();
//                    result.add(nums[i]);//
                    continue inner;
                }
                int index = temp_index[left&count];//left 对应的index
                if(index!=0){//0是数组元素默认值
                    int exist = nums[index];
                    int min_value = exist<nums[j]?exist:nums[j];
                    if(min.contains(min_value)){//重复序列
                        temp_index[left&count]=0;//清理temp_index
                        continue inner;
                    }
                    //
                    min.add(min_value);
                    result.add(nums[index]);
                    result.add(nums[j]);
//                    Collections.sort(result);

//                    list.add(result);
                    temp_index[left&count]=0;//清理temp_index

//                    result = new ArrayList<>();
                    result.add(nums[i]);//
                    continue inner;
                }
                //没找到
                temp_index[nums[j]&count]=j;
            }
        }
        if(list.size()<2){
            return list;
        }



        List<List<Integer>> lastList = list.stream().filter(v->{
            int f1 = v.get(0);
            int f2 = v.get(1);

            boolean contain = false;
            HashSet<Integer> set = map.get(f1);
            if(!map.containsKey(f1)||null== set){
                contain = false;
                set = new HashSet<>();
                set.add(f2);
                map.put(f1,set);
                return !contain;
            }
            contain = set.contains(f2);
            if(!contain){
                set.add(f2);
            }
            return !contain;//已存在的丢弃

        }).collect(Collectors.toList());
        list.forEach(System.out::println);
        System.out.println("+++++++++++++++++");
        lastList.forEach(System.out::println);

        return lastList;
    }



public static void main(String[] args){
        //int[] nums = new int[]{-4,-2,1,-5,-4,-4,4,-2,0,4,0,-2,3,1,-5,0};
        long time = System.currentTimeMillis();
        int[] nums = new int[]{-1,0,1,2,-1,-4};
        Solution.threeSum(nums);
        System.out.print("time elapse:"+(System.currentTimeMillis()-time));
    }
}