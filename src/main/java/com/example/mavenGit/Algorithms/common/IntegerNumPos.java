package com.example.mavenGit.Algorithms.common;

/**
 * Created by leibao on 2018/7/17.
 * num base from 1 to 100000000
 */
public enum IntegerNumPos {
    GE('个', 1),
    SHI('十', 10),
    BAI('百', 100),
    QIAN('千', 1000),
    WAN('万', 10000),
    YI('亿', 100000000);
    private char chineseName;
    private Integer numStand;

    IntegerNumPos(char chineseName, Integer numStand) {
        this.chineseName = chineseName;
        this.numStand = numStand;
    }

    public static IntegerNumPos getByName(char chineseName) {
        for (IntegerNumPos num : IntegerNumPos.values()) {
            if (num.chineseName == chineseName) {
                return num;
            }
        }
        return null;
    }

    public char getChineseName() {
        return chineseName;
    }

    public void setChineseName(char chineseName) {
        this.chineseName = chineseName;
    }

    public Integer getNumStand() {
        return numStand;
    }

    public void setNumStand(Integer numStand) {
        this.numStand = numStand;
    }
}
