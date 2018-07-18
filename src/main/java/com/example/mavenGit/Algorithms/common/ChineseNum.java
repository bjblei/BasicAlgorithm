package com.example.mavenGit.Algorithms.common;

/**
 * Created by leibao on 2018/7/17.
 */
public enum ChineseNum {
    POS('空', -1),//表示该位数字不存在，仅有符号
    LIN('零', 0),
    YI('一', 1),
    ER('二', 2),
    SAN('三',3),
    SI('四', 4),
    WU('五', 5),
    LIU('六',6),
    QI('七', 7),
    BA('八', 8),
    JIU('九',9);

    private char name;
    private Integer num;

    ChineseNum(char name, Integer num) {
        this.name = name;
        this.num = num;
    }

    ChineseNum(char name) {
        ChineseNum chNum = getByName(name);
        if (null == chNum) {
            throw new RuntimeException("非法中文数字:" + name);
        }
        this.name = chNum.name;
        this.num = chNum.num;
    }

    public static ChineseNum getByName(char chineseName) {
        for (ChineseNum num : ChineseNum.values()) {
            if (num.name == chineseName) {
                return num;
            }
        }
        return null;
    }

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
