package com.example.mavenGit.Algorithms.sort;

/**
 * 基数排序
 *
 * @author leibao
 */
public class RadixSort {
    public RadixSort() {
    }

    /**
     * 代价 n的3次方
     *
     * @param src 长度为n的多位正整数
     * @param d   给定d位数
     * @return
     */
    public static int[] sort(int[] src) {
        if (null == src || src.length < 2) {
            return src;
        }
        int d = findMaxWidth(src);
        for (int i = 1; i <= d; i++) {// 按各个位置数字大小排序，1～d
            for (int j = 1; j < src.length; j++) {// (按当前位)插入排序
                int target = src[j];// 等待插入目标
                int key = findPosValue(src[j], i);
                int p = j - 1;
                while (p >= 0 && findPosValue(src[p], i) > key) {
                    src[p + 1] = src[p];
                    p--;
                }
                src[p + 1] = target;
            }
        }
        return src;
    }

    /**
     * @param src
     * @return
     */
    public static int[] sortWithArray(int[] src) {
        if (null == src || src.length < 2) {
            return src;
        }
        int d = findMaxWidth(src);
        // 每个位置上的数据位0～9，多分配一列用于统计个数
        int[][] array = new int[10][src.length + 1];
        for (int i = 0; i < 10; i++) {
            array[i][0] = 0;// array[i][0]记录第i行数据的个数
        }
        for (int i = 1; i <= d; i++) {
            // 1:将src中每一个值按照本位置上的值分配到对应的行中
            for (int j = 0; j < src.length; j++) {
                int row = findPosValue(src[j], i);// 行号就是当前位上的值
                array[row][0] += 1;// 值位row的count，放在第0列
                int col = array[row][0];
                // put the src[j] into array
                array[row][col] = src[j];
            }
            // 2:将array中的值copy回src中
            int k = 0;
            for (int p = 0; p < 10; p++) {
                int col = array[p][0];
                if (col < 1) {
                    continue;
                }
                for (int q = 1; q <= col; q++) {
                    src[k++] = array[p][q];
                }
                array[p][0] = 0;// 本列已经取完，计数重置为0
            }
            // 本轮结束，这个位置排序完成
        }
        return src;
    }

    /**
     * 找出数组最大位数
     *
     * @param src
     * @return
     */
    private static int findMaxWidth(int[] src) {
        if (null == src || src.length < 1) {
            return 0;
        }
        int max = 0;
        for (int value : src) {
            max = Math.max(max, value);
        }
        // max的宽度即为最大位数
        int d = 1;
        while (max > 0) {
            max = max / 10;
            if (max > 0) {
                d++;
            }
        }
        return d;
    }

    /**
     * @param value 整数值，如123
     * @param d     位值，如d=1表示各位，d=2表示十位
     * @return
     */
    private static int findPosValue(int value, int d) {
        if (value <= 0 || d < 1) {
            return 0;
        }
        int result = 0;
        int base = 1;
        int pos = 1;
        while (pos <= d) {// 1~d 共操作d次
            result = (value / base) % 10;
            base *= 10;
            pos++;
        }
        return result;
    }

    public static void main(String[] arg) {
        int[] src = {231, 456, 541, 678, 901, 109, 412, 89};
        long start = System.nanoTime();
        int[] result = RadixSort.sort(src);
        //int[] result = RadixSort.sortWithArray(src);
        long end = System.nanoTime();
        System.out.print("spentTime = " + (end - start) + "\n");
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]);
            if (i < result.length - 1) {
                System.out.print(",");
            }
        }
    }
}
