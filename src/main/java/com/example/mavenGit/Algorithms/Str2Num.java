package com.example.mavenGit.Algorithms;

import com.example.mavenGit.Algorithms.common.ChineseNum;
import com.example.mavenGit.Algorithms.common.IntegerNumPos;
import com.example.mavenGit.Algorithms.common.entity.NumPoint;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by leibao on 2018/7/17.
 * 将给定的形如"三千一百五十三万九千零二"的中文字符串转换成阿拉伯数字
 * 如果不符合语法规范，返回-1
 */
public class Str2Num {


    /**
     * @param str
     * @return
     */
    //todo 尝试用一个指针简化处理
    public static Integer getNumFromChineseStr(String str) {
        if (null == str || "".equals(str.trim())) {

        }
        char[] arrays = str.toCharArray();
        if (null == arrays) {
            return -1;
        }
        int len = arrays.length;
        if (len == 0) {
            return -1;
        }

        //首先排除起始字符非法的情况
        char ch1 = arrays[0];
        ChineseNum chinNum = ChineseNum.getByName(ch1);
        if (null == chinNum || chinNum.getNum() <= 0) { //第一个字符必须是非0数字
            return -1;
        }
        if (len == 1) {
            return chinNum.getNum();//只有1位
        }
        //将原来的字符串解析为NumPoint对象：数量／单位组合
        List<NumPoint> list = new LinkedList<NumPoint>();
        for (int i = 0; i < len; i++) {
            char ch = arrays[i];
            if (NumPoint.isIllegalChar(ch)) {
                System.out.print("非法字符：pos=prev,index=" + i + ",char=" + ch);
                return -1;
            }
            //非数字字符：原则上略过
            if (NumPoint.isChinesePos(ch)) {
                if (NumPoint.isChineseNormalPos(ch)) {// 三十 类型已经处理过
                    System.out.print("非法字符：pos=prev,index=" + i + ",char=" + ch);
                    return -1;
                }
                //'万'或者'亿'
                ChineseNum num = ChineseNum.POS;//数字
                IntegerNumPos pos = IntegerNumPos.getByName(ch);//单位
                NumPoint point = new NumPoint(num, pos);
                list.add(point);
                if (i + 1 >= len) {
                    return handleNumPointList(list);
                }

                //后边还有字符
                if (NumPoint.isChineseNum(arrays[i + 1])) {
                    continue;
                }
                //连续字符:两个非数字字符
                //非法组合
                if (!NumPoint.isRightSamePosChars(ch, arrays[i + 1])) {
                    System.out.print("非法字符组合：pos=prev&next,prev|next=" + ch + "|" + arrays[i + 1]);
                    return -1;
                }
                // 万亿连用
                continue;
            }
            //数字
            if (ch == '零') {
                continue;
            }
            //正整数
            if (i == len - 1
                    || (i < len - 1 && NumPoint.isChineseSpecialPos(arrays[i + 1]))) {
                ChineseNum num = ChineseNum.getByName(ch);//数字
                IntegerNumPos pos = IntegerNumPos.GE;//单位
                NumPoint point = new NumPoint(num, pos);
                list.add(point);
                continue;
            }
            //结束
            if (i + 1 >= len) {
                return handleNumPointList(list);
            }
            char ch2 = arrays[i + 1];
            if (NumPoint.isIllegalChar(ch2)) {
                System.out.print("非法字符：pos=next,index=" + (i + 1) + ",char=" + ch2);
                return -1;
            }
            if (!NumPoint.isChineseNormalPos(ch2)) {
                System.out.println("非法组合:" + str.substring(i, i + 2 > len ? len : i + 2));
                return -1;
            }
            ChineseNum num = ChineseNum.getByName(ch);//数字
            IntegerNumPos pos = IntegerNumPos.getByName(ch2);//单位
            NumPoint point = new NumPoint(num, pos);
            list.add(point);
            i++;//多加一次
        }
        int sum = 0;
        sum = handleNumPointList(list);
        return sum;
    }

    /**
     * 使用两个指针,通过判定二者的组合形式来确定数据
     *
     * @param str
     * @return
     */
    public static Integer getNumFromChineseStr2(String str) {
        if (null == str || "".equals(str.trim())) {

        }
        char[] arrays = str.toCharArray();
        if (null == arrays) {
            return -1;
        }
        int len = arrays.length;
        if (len == 0) {
            return -1;
        }

        //首先排除起始字符非法的情况
        char ch1 = arrays[0];
        ChineseNum chinNum = ChineseNum.getByName(ch1);
        if (null == chinNum || chinNum.getNum() <= 0) { //第一个字符必须是非0数字
            return -1;
        }
        if (len == 1) {
            return chinNum.getNum();//只有1位
        }
        int prev = 0;
        int next = 1;
        //将原来的字符串解析为NumPoint对象：数量／单位组合
        List<NumPoint> list = new LinkedList<NumPoint>();
        //以next为当前处理对象
        while (prev < len) {
            char chPrev = arrays[prev];
            if (NumPoint.isIllegalChar(chPrev)) {
                System.out.print("非法字符：pos=prev,index=" + prev + ",char=" + chPrev);
                return -1;
            }

            next = prev + 1;
            //结束
            if (next >= len) {
                return handleNumPointList(list);
            }
            char chNext = arrays[next];
            if (NumPoint.isIllegalChar(chNext)) {
                System.out.print("非法字符：pos=next,index=" + next + ",char=" + chNext);
                return -1;
            }

            /**
             * prev|next的可能组合方式如下：
             * //部分符合
             * 数字|数字:prev必须为'零' 且二者不能相等(必须是零|非零 组合)
             * 单位|单位:必须符合NumPoint.isRightSamePosChars()
             * //正常:最普通模式
             * 数字|单位
             * //不符合：直接移动
             * 单位|数字
             */
            //1都是数字或都是单位
            if (NumPoint.isSameTypeChars(chPrev, chNext)) {
                boolean numIllegal = (NumPoint.isChineseNum(chPrev) && (chPrev != '零' || chPrev == chNext));
                boolean posIllegal = NumPoint.isChinesePos(chPrev) && !NumPoint.isRightSamePosChars(chPrev, chNext);
                if (numIllegal || posIllegal) {
                    System.out.print("非法字符组合：pos=prev&next,prev|next=" + chPrev + "|" + chNext);
                    return -1;
                }
                //符合条件情况
                /*
                1.1都是数字:只能是"零三"类型.一般跳过,将'三'留给下一次处理，但是'三'在个位或者后面跟着'万'|'亿'除外
                 */
                if (NumPoint.isChineseNum(chPrev)) {
                    if ((prev > 0 && NumPoint.isChineseNum(arrays[prev - 1]))
                            || (next < len - 1 && NumPoint.isChineseNum(arrays[next + 1]))) {
                        System.out.print("有连续3个数字,组合非法:" + str.substring(prev - 1, next < len - 1 ? next + 2 : len));
                        return -1;
                    }
                    //last one
                    if (next == len - 1 ||
                            (next < len - 1 && NumPoint.isChineseSpecialPos(arrays[next + 1]))) {
                        ChineseNum num = ChineseNum.getByName(chNext);//数字
                        IntegerNumPos pos = IntegerNumPos.GE;//单位
                        NumPoint point = new NumPoint(num, pos);
                        list.add(point);
                    }
                } else // 1.2都是单位
                {
                    if ((prev > 0 && NumPoint.isChinesePos(arrays[prev - 1]))
                            || (next < len - 1 && NumPoint.isChinesePos(arrays[next + 1]))) {
                        System.out.print("有连续3个单位字符,组合非法:" + str.substring(prev - 1, next < len - 1 ? next + 2 : len));
                        return -1;
                    }
                    ChineseNum num = ChineseNum.POS;//数字
                    IntegerNumPos pos = IntegerNumPos.getByName(chNext);//单位
                    NumPoint point = new NumPoint(num, pos);
                    list.add(point);
                }

            } else if (NumPoint.isChineseNum(chPrev)) { //2.1数字|单位
                ChineseNum num = ChineseNum.getByName(chPrev);
                IntegerNumPos pos = null;
                if (NumPoint.isChineseSpecialPos(chNext)) { //三亿（必然如"二十三亿"或"三百零三亿"，'三'已被处理过）
                    NumPoint point = new NumPoint(ChineseNum.POS, IntegerNumPos.getByName(chNext));
                    list.add(point);
                } else { //正常模式：数字／单位组合,如 三千
                    pos = IntegerNumPos.getByName(chNext);
                    NumPoint point = new NumPoint(num, pos);
                    list.add(point);
                }

            } else //2.2单位|数字:一般跳过，将数字留给下次处理，但是当数字是个位或者数字后面跟着'万'|'亿'除外
            {
                if (next == len - 1 ||
                        (next < len - 1 && NumPoint.isChineseSpecialPos(arrays[next + 1]))) {
                    ChineseNum num = ChineseNum.getByName(chNext);//数字
                    IntegerNumPos pos = IntegerNumPos.GE;//单位
                    NumPoint point = new NumPoint(num, pos);
                    list.add(point);
                }
            }
            prev = next;
            next = prev + 1;
        }

        return -1;
    }

    //**处理NumPoint 列表
    private static Integer handleNumPointList(List<NumPoint> pointList) {
        if (null == pointList || pointList.isEmpty()) {
            return -1;
        }
        //结果形如:(3*100+20）*1亿+（2*1000+3*1）*1万+（1*1000+2*100+3*10+4）*1个
        int total = 0;
        int sum = 0;
        int len = pointList.size();
        for (int i = 0; i < len; i++) {
            NumPoint point = pointList.get(i);
            ChineseNum num = point.getNum();
            IntegerNumPos pos = point.getPos();
            if (num.getNum() > 0) {
                sum += num.getNum() * pos.getNumStand();
            } else if (num.getNum() == -1 && sum > 0) {//special pos
                sum = sum * pos.getNumStand();
                total += sum;
                //将sum阶段性归零:三亿三千万零五百,不可能出现 万亿并用的情况===》超出Integer最大范围了
                sum = 0;
            }
        }
        total += sum;
        return total;
    }

    public static void main(String[] args) {
        // System.out.print(2200000000);注意2^32=2147483648 <230000000 不能超过这个数
        String str1 = "一千二百三十万零五百零四";
        String str2 = "二十一亿三千一百三十五万六千";
        System.out.println(str1 + "[==]" + getNumFromChineseStr(str1));
        System.out.println(str2 + "[==]" + getNumFromChineseStr(str2));

    }

}
