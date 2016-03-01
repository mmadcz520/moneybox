package com.changtou.moneybox.common.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by Jone on 2015/11/24.
 */
public class Utils {
    /**
     * 格式化
     */
    private static DecimalFormat dfs = null;

    public static DecimalFormat format(String pattern) {
        if (dfs == null) {
            dfs = new DecimalFormat();
        }
        dfs.setRoundingMode(RoundingMode.FLOOR);
        dfs.applyPattern(pattern);
        return dfs;
    }
}