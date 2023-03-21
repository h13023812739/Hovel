package org.hyq.hovel.controller;

import java .awt .*;

public class DemoTest extends Frame {
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
                // 设置暂口关闭方式
//                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // 设驾额口显示
                this.setVisible(true);
        }

        @Override
        public void paint(Graphics g) {
                double x,y, r;
                Image offScreen = createImage(GAME_WIDTH,GAME_HEIGHT);
                Graphics drawoffScreen = offScreen.getGraphics();
                for (int i = 0; i < 90; i++) {
                        for (int j = 0; j < 90; j++) {
                                r = Math.PI / 45 * i * (1 - Math.sin(Math.PI / 45 * j)) + 18;
                                x = r * Math.cos(Math.PI / 45 * j) * Math.sin(Math.PI / 45 * i) + GAME_WIDTH / 2;
                                y = -r * Math.sin(Math.PI / 45 * j) + GAME_HEIGHT / 4;

                                //设警百能额色
                                drawoffScreen.setColor(Color.PINK);// 绘利构围
                                drawoffScreen.fillOval((int) x, (int) y, 2, 2);
                        }
                }

                // 生或图片
                g.drawImage(offScreen,0,8,this);
        }

        public static void main(String[] args) {
                DemoTest demo = new DemoTest();
                demo.setVisible(true);
        }

}