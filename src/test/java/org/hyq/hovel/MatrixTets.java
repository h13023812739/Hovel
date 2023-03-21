package org.hyq.hovel;

import cn.hutool.core.collection.ListUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * test1 比 test 快
 * 因为读取a,b数组都是横向读取
 * 原理：二维数组数据结构，CPU L1、L2缓存模型
 * https://mp.weixin.qq.com/s/uZYwJBi7t7x-h0yd-WcOIw
 */
public class MatrixTets {
    int[][] a = {{1,2},{2,1}};
    int[][] b = {{1,2},{2,1}};
    int[][] c = new int[2][2];

    @Test
    public void test(){
        System.out.println("开始");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    c[i][j] += a[i][k]*b[k][j];
                }
            }
        }
        System.out.println(Arrays.toString(c[0]));
        System.out.println(Arrays.toString(c[1]));
    }

    @Test
    public void test1(){
        System.out.println("开始");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
//                int crt_A = a[i][j];
                for (int k = 0; k < 2; k++) {
                    c[j][k] += a[i][j]*b[i][k];
                }
            }
        }
        System.out.println(Arrays.toString(c[0]));
        System.out.println(Arrays.toString(c[1]));
    }
}
