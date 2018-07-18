package com.example.mavenGit.Algorithms.common.entity;

import com.example.mavenGit.Algorithms.common.ChineseNum;
import com.example.mavenGit.Algorithms.common.IntegerNumPos;

import java.util.HashSet;

/**
 * Created by leibao on 2018/7/17.
 */
public class NumPoint {
    private ChineseNum num;//数字
    private IntegerNumPos pos;//单位

    public NumPoint(ChineseNum num, IntegerNumPos pos) {
        this.num = num;
        this.pos = pos;
    }

    public ChineseNum getNum() {
        return num;
    }

    public void setNum(ChineseNum num) {
        this.num = num;
    }

    public IntegerNumPos getPos() {
        return pos;
    }

    public void setPos(IntegerNumPos pos) {
        this.pos = pos;
    }


    //

    //中文数字集合
    private static HashSet<Character> chineseNumSet = new HashSet<Character>();
    //中文正常单位集合
    private static HashSet<Character> chineseNormalPosSet = new HashSet<>();
    //中文特殊数字单位(万、亿)
    private static HashSet<Character> chineseSpecialPosSet = new HashSet<>();

    static {
        chineseNumSet.add('零');
        chineseNumSet.add('一');
        chineseNumSet.add('二');
        chineseNumSet.add('三');
        chineseNumSet.add('四');
        chineseNumSet.add('五');
        chineseNumSet.add('六');
        chineseNumSet.add('七');
        chineseNumSet.add('八');
        chineseNumSet.add('九');
        //
        chineseNormalPosSet.add('十');
        chineseNormalPosSet.add('百');
        chineseNormalPosSet.add('千');
        //
        chineseSpecialPosSet.add('万');
        chineseSpecialPosSet.add('亿');

    }

    //是否中文数字
    public static boolean isChineseNum(char ch) {
        return chineseNumSet.contains(ch);
    }

    //是否正常单位
    public static boolean isChineseNormalPos(char ch) {
        return chineseNormalPosSet.contains(ch);
    }

    //是否特殊单位（万、亿）
    public static boolean isChineseSpecialPos(char ch) {
        return chineseSpecialPosSet.contains(ch);
    }

    //字符是否为单位
    public static boolean isChinesePos(char ch) {
        return isChineseNormalPos(ch) || isChineseSpecialPos(ch);
    }

    //是否合法字符
    public static boolean isIllegalChar(char ch) {
        return !isChineseNum(ch)
                && !isChineseNormalPos(ch)
                && !isChineseSpecialPos(ch);
    }

    //是否同一类型:都是数字或者都是单位
    public static boolean isSameTypeChars(char ch1, char ch2) {
        return (isChineseNum(ch1) && isChineseNum(ch2))
                || (isChinesePos(ch1) && isChinesePos(ch2));
    }

    //当两个连续字符都是单位时，是否合法:仅类似 千亿、万亿类型合法
    public static boolean isRightSamePosChars(char ch1, char ch2) {
        return
                (isChineseNormalPos(ch1) && isChineseSpecialPos(ch2))
                || (ch1 == '万' && ch2 == '亿');
    }
}
