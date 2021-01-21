package com.wangc.knowledgepoints.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @date 2020/5/7 20:30
 */
public class test2 {

    public static void main(String... args) {
        forScore();
    }

    public static void forScore(){
//        int[] es = {1,2,4,5,4,6,2,3,5};
//        int result = 8;
//        int[] es = {30000,2500,4000,5500,1400,6000,20000,35000,5800};
//        int result = 36500;
//
//        List<List<Integer>> list = count(es, 0, -1, result);
//        System.out.println(list);
//
//        for(List<Integer> l:list){
//            for(Integer i:l){
//                System.out.print(es[i]+",");
//            }
//            System.out.println();
//        }
        String str = "2";
        System.out.println(str.getBytes().length);
        System.out.println("你好".getBytes().length);

        System.out.println("你好".length());
        System.out.println("nihao".length());
    }

    public static List<List<Integer>> count(int[] es, int sum, int currIndex, int result){
        List<List<Integer>> indexLink = new ArrayList<List<Integer>>();
        for(int i=currIndex+1; i<es.length; i++){
            int s = es[i]+sum;
            if(s < result){
                List<List<Integer>> iLink = count(es, s, i, result);

                for(List list:iLink){
                    list.add(0,i);
                }

                if(iLink.size() > 0){
                    indexLink.addAll(iLink);
                }

            }else if(s > result){
                continue;
            }else{
                List<Integer> list = new ArrayList<Integer>();
                list.add(i);
                indexLink.add(list);
            }
        }

        return indexLink;
    }
}
