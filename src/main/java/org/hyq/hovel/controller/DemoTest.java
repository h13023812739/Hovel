package org.hyq.hovel.controller;

import javax.swing.*;
import java.awt.*;

public class DemoTest extends JFrame {
        private static final long serialversionUID = -1284128891988775645L;
        // 定义加缴额口大小
        public static final int GAME_WIDTH = 588;
        public static final int GAME_HEIGHT = 508;// 获取屏幕窗口大小
        public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
        public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
        public DemoTest() {
                // 设标题
                this.setTitle("心形曲线");
                // 设置额口初始位量
                this.setLocation((WIDTH - GAME_WIDTH) / 2, (HEIGHT - GAME_HEIGHT) / 2);
                // 设置窗口大小
                this.setSize(GAME_WIDTH, GAME_HEIGHT);
                // 设置背景色
                this.setBackground(Color.BLACK);
                // 设置窗口关闭方式
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // 设驾额口显示
                this.setVisible(true);
        }

        @Override
        public void paint(Graphics g) {
//                double x,y, r;
                Image offScreen = createImage(GAME_WIDTH,GAME_HEIGHT);
                Graphics drawoffScreen = offScreen.getGraphics();
//                for (int i = 0; i < 90; i++) {
//                        for (int j = 0; j < 90; j++) {
//                                r = Math.PI / 45 * i * (1 - Math.sin(Math.PI / 45 * j)) * 18;
//                                x = r * Math.cos(Math.PI / 45 * j) * Math.sin(Math.PI / 45 * i) + GAME_WIDTH / 2;
//                                y = -r * Math.sin(Math.PI / 45 * j) + GAME_HEIGHT / 4;
//
//                                //设警百能额色
//                                drawoffScreen.setColor(Color.PINK);// 绘利构围
//                                drawoffScreen.fillOval((int) x, (int) y, 2, 2);
//                        }
//                }

                // (x^2 + y^2 - 1)^3 - x^2 y^3 = 0
                int row = 1, col = 0;
                for (double y = 1.5f; y >= -1.5f; y -= 0.01f) {
                        for (double x = -1.5f; x <= 1.5f; x += 0.01f) {
                                double temp = Math.pow(x, 2) + Math.pow(y, 2) - 1;
                                if ((Math.pow(temp, 3) - Math.pow(x, 2) * Math.pow(y, 3)) <= 0.0){
                                //设警百能额色
                                drawoffScreen.setColor(Color.PINK);// 绘利构围
                                drawoffScreen.fillOval( 588/4+col,  508/4+row, 2, 2);
                                }
                                col++;
                        }
                        row++; col = 0;
                }

                // 生成图片
                g.drawImage(offScreen,0,0,this);
        }

        public static void main(String[] args) {
                DemoTest demo = new DemoTest();
                demo.setVisible(true);
        }

}