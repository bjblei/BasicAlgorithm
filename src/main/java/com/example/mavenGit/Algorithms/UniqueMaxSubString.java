package com.example.mavenGit.Algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 找到给定字符串中不含重复字符的最大子串
 * Created by leibao on 2018/7/5.
 */
public class UniqueMaxSubString {

    static class LongestSubStr {
        private int start = 0;
        private int end = 0;
        private int length = 0;
        private String value = "";

        public LongestSubStr(int start, int end, String initValue) {
            this.start = start;
            this.end = end;
            this.length = end >= start ? end - start + 1 : 0;//头尾都包含
            value = initValue;
        }

        @Override
        public String toString() {
            return "LongestSubStr{\n" +
                    "start=" + start + "\n" +
                    "end=" + end + "\n" +
                    "length=" + length + "\n" +
                    "value='" + value + "'\n}";
        }
    }

    /**
     * 算法主体
     *
     * @param initStrValue
     * @return
     */
    public static LongestSubStr findMaxQuniueSubStr(String initStrValue, boolean printProcess) {
        LongestSubStr subStr = new LongestSubStr(0, 0, "");
        // empty string
        if (null == initStrValue || initStrValue.trim().equals("")) {
            return subStr;
        }
        List<LongestSubStr> list2Sort = new ArrayList<>();
        char[] array = initStrValue.toCharArray();
        int len = array.length;
        int start = 0;
        int end = start + 1;
        while (end < len) { //一次遍历
            int index = repeatCharIndex(array, start, end - 1, array[end]);
            if (index >= 0) {//找到了
                //区间[start,end-1]
                LongestSubStr sub = new LongestSubStr(start, end - 1, initStrValue.substring(start, end));
                list2Sort.add(sub);
                start = index + 1;
            }
            end++;
        }
        //结果排序
        list2Sort.sort(new Comparator<LongestSubStr>() {
            @Override
            public int compare(LongestSubStr o1, LongestSubStr o2) {
                return o2.length - o1.length;//倒序排列
            }
        });
        if (printProcess) {
            System.out.println("all subStrings as follow:");
            System.out.println("**************************");
            int cnt = 0;
            for (LongestSubStr str : list2Sort) {
                System.out.println(cnt + ":" + str);
                cnt++;
            }
            System.out.println("\n**************************");
        }

        LongestSubStr maxSubStr = list2Sort.get(0);
        if (null != maxSubStr) {
            return maxSubStr;
        }

        return subStr;
    }

    /**
     * 找到字符串数组中重复字符的下标记
     * 在[begin,end]区间内寻找值等于ch的字符下标
     *
     * @param array
     * @param begin
     * @param end
     * @param ch
     * @return
     */
    private static int repeatCharIndex(char[] array, int begin, int end, char ch) {
        //不符合条件的参数
        if (null == array
                || array.length == 0
                || begin < 0
                || end >= array.length
                || begin > end) {
            return -1;
        }
        for (int k = begin; k <= end; k++) {
            if (array[k] == ch) {
                return k;
            }
        }

        return -1;
    }

    /***
     *
     * @param args
     */
    public static void main(String[] args) {
        String str = "abcdefghijkabdclkmiowekrgmlahgrfkqls";
        System.out.print(UniqueMaxSubString.findMaxQuniueSubStr(str, true));
    }
}
