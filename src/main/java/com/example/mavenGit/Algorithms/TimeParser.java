package com.example.mavenGit.Algorithms;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 输入6个非负整数,判定是否可以组成24小时制的时间，如果可以，输出形如
 * 00：00：00的字符串，否则输出"NOT POSSIBLE"
 * Created by leibao on 2018/4/27.
 */
public class TimeParser {
    private static final String ERROR_MSG = "NOT POSSIBLE";

    public static void main(String[] args) {
        TimeParser parser = new TimeParser();
        String timeStr = parser.getTimeStr(9, 8, 7, 5, 3, 0);
        System.out.print("earliest date=" + timeStr);
    }

    public String getTimeStr(int a, int b, int c, int d, int e, int f) {
        int[] array = new int[]{a, b, c, d, e, f};
        Arrays.sort(array);
        SortUtils.printArray(array);
        /**
         * 最终合法结果
         * (1)[0|1][0~9]:[0~5][0~9]:[0~5][0~9]
         * (2)[2][0~4]:[0~5][0~9]:[0~5][0~9]
         */
        /**
         * 思路:
         * 1)将6个数从小到大排序
         * 2)index=0的不能超过2（0,1,2）否则肯定不合法
         * 3)如果前三个数不能保证都小于6（根据index=2判定）肯定不合法
         * 4)如果index=0 的值为2，需要index=1的数据不大于4且前四个数小于6，否则不合法
         * 5)综合3)和4)只需要判定index=4的（秒位上第一个）是否小于6
         * 5.1)如果小于6,ok，否则
         * 5.2)一直找到第一个小于6且不在index=2（分钟第一个）位置上的数据
         * 重点：依次后移（而不是简单swap！！！！）
         */
        //首位不能大于2
        if (array[0] > 2) {
            return ERROR_MSG;
        }
        //保证时分秒三个数首位均小于6(任何时候)
        if (array[2] >= 6) {
            return ERROR_MSG;
        }
        if (array[0] == 2 && (array[1] > 4 || array[3] >= 6)) {
            return ERROR_MSG;
        }
        //合法字符串
        /**
         * 可能需要调整顺序的情况：index=4的（秒位第一个)大于5,需要前移
         */
        if (array[4] >= 6) {//index=2的已经确定小于6
            if (array[3] >= 6) { //如果index=3（D）也大于5，需用3和4一起移动
                SortUtils.swap(array, 2, 3);
                SortUtils.swap(array, 1, 2);
            }
            SortUtils.swap(array, 3, 4);
        }
        //数据位置已经符合条件
        return getDateStr(array);
    }

    private String getDateStr(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < array.length; k++) {
            sb.append(array[k]);
            if (k == 1 || k == 3) {
                sb.append(":");
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM:SS");
        Date date = null;
        try {
            date = sdf.parse(sb.toString());
        } catch (Exception ex) {
            return ERROR_MSG;
        }
        if (null == date) {
            return ERROR_MSG;
        }
        return sb.toString();
    }
}
