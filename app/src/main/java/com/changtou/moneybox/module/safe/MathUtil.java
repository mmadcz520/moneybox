package com.changtou.moneybox.module.safe;

/**
 * 九宫格密码算法类
 *
 * Created by Administrator on 2015/3/10.
 */
public class MathUtil {

    /**
     * 计算两点间的距离
     */
    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.abs(x1 - x2) * Math.abs(x1 - x2)
                + Math.abs(y1 - y2) * Math.abs(y1 - y2));
    }

    /**
     *判断是不是在当前点上
     */
    public static double pointTotoDegrees(double x, double y) {
        return Math.toDegrees(Math.atan2(x, y));
    }

    /**
     * 判断是不是在某个圆形范围内
     */
    public static boolean checkInRound(float sx, float sy, float r, float x,
                                       float y) {
        return Math.sqrt((sx - x) * (sx - x) + (sy - y) * (sy - y)) < r;
    }
}
