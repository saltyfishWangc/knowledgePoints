package com.wangc.knowledgepoints.algorithm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 从一个数组中列出n个数相加等于固定数的组合
 * @date 2020/5/7 20:06
 */
public class arraycombinetoequalnumber {

    static String input[] = { "2", "360.00","1231","2321", "5879.52", "8040", "14277.60" };

    public static void main(String[] args) {
        List<String> list = Arrays.asList(input);
        List<String> list1 = new ArrayList<String>(list);
        List<Integer[]> ls = Combination(list1, 0);
        for (int i = 0; i < ls.size(); i++) {
            Integer[] tt = ls.get(i);
            System.out.print("第" + (i + 1) + "组组合元素:");
            String number = "0";
            for (int j = 0; j < tt.length; j++) {
                System.out.print(list1.get(tt[j]) + " ");
                number = addMoney(number,list1.get(tt[j]));
            }
            if(number.equals("3552‬")) {
                System.out.println("success");
            }
            System.out.println(",");
        }
    }
    public static String addMoney(String mny1, String mny2) {
        if (mny1 == null || mny1 == "" || mny2 == null || mny2 == "") {
            System.out.println("传入金额不能为空");
        }
        BigDecimal bmny1 = new BigDecimal(mny1);
        BigDecimal bmny2 = new BigDecimal(mny2);
        return bmny1.add(bmny2).toPlainString();
    }
    /**
     * 求 某集合 所有与组合 下标 元素组合 公式 n!/r!(n-r)! 如果 r > 0 则返回的 下标组合 仅为 某一种 组合 如果 r = 0 则
     * 下标组合 为 1 到 n 所有组合
     *
     * @param list 求组合的 集合元素
     * @param r    组合长度
     * @return
     */
    public final static List<Integer[]> Combination(final List<String> list, final int r) {
        List<Integer[]> reslist = new ArrayList<Integer[]>();

        int end = list.size();
        int start = 1;
        if (r > 0) {
            end = r;
            start = r;
        }
        for (int i = start; i <= end; i++) {
            Integer[] inv = new Integer[i];
            Combination(list, 0, inv, 0,reslist);
        }
        return reslist;
    }

    public final static void Combination(final List<String> list1, final int a_pos, final Integer[] inv, final int rs_pos,List<Integer[]> reslist) {
        if (rs_pos >= inv.length) {
            Integer[] inv2 = inv.clone();
            reslist.add(inv2);
        } else
            for (int ap = a_pos; ap < list1.size(); ap++) {
                inv[rs_pos] = ap;
                Combination(list1, ap + 1, inv, rs_pos + 1,reslist);
            }
    }
}
