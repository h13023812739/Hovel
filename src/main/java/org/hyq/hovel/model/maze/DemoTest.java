package org.hyq.hovel.model.maze;

import lombok.extern.slf4j.Slf4j;
import org.hyq.hovel.enums.BlockEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class DemoTest extends JFrame {
	private Maze maze;

	static Path path;

	private static final long serialversionUID = -1284128891988775645L;

	// 定义加缴额口大小
	public static final int GAME_WIDTH = 388;
	public static final int GAME_HEIGHT = 308;// 获取屏幕窗口大小
	public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

	public DemoTest() {
		// 设标题
		this.setTitle("迷宫");
		// 设置额口初始位量
		this.setLocation((WIDTH - GAME_WIDTH) / 2, (HEIGHT - GAME_HEIGHT) / 2);
		// 设置窗口大小
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		// 设置背景色
		this.setBackground(Color.BLACK);
		// 设置窗口关闭方式
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		// 设驾额口显示
//		this.setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		Image offScreen = createImage(GAME_WIDTH,GAME_HEIGHT);
		Graphics drawoffScreen = offScreen.getGraphics();
		try {
			maze = (Maze) Class.forName("org.hyq.hovel.model.maze.Maze").newInstance();
			path = (Path) Class.forName("org.hyq.hovel.model.maze.Path").newInstance();
			maze.init();
//			path.find(maze);
			draw( offScreen,  g,  drawoffScreen, maze.getMazeArray(),  maze.getUnmaskArray(), 308, 388);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("画图完成");
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		draw3DHeart(g);
	}

	public void draw3DHeart(Graphics g){
		Image offScreen = createImage(GAME_WIDTH,GAME_HEIGHT);
		Graphics drawoffScreen = offScreen.getGraphics();
		double x,y, r;
		for (int i = 0; i < 90; i++) {
			for (int j = 0; j < 90; j++) {
				r = Math.PI / 45 * i * (1 - Math.sin(Math.PI / 45 * j)) * 18;
				x = r * Math.cos(Math.PI / 45 * j) * Math.sin(Math.PI / 45 * i) + GAME_WIDTH / 2;
				y = -r * Math.sin(Math.PI / 45 * j) + GAME_HEIGHT / 4;

				//设置绘制颜色
				drawoffScreen.setColor(Color.PINK);
				// 绘制构图
				drawoffScreen.fillOval((int) x, (int) y, 2, 2);
			}
		}
		// 生成图片
		g.drawImage(offScreen,0,0,this);
	}

	public void draw(Image offScreen, Graphics g, Graphics drawoffScreen, BlockEnum[][] mazeArray, boolean[][] unmaskArray, int ROW, int COL) throws Exception {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				if (!unmaskArray[i][j]) {
//					System.out.print(Color.ANSI_BLACK_BACKGROUND + " " + Color.ANSI_RESET);
					drawoffScreen.setColor(Color.BLACK);
					drawoffScreen.fillOval( j, i, 2, 2);
					g.drawImage(offScreen,0,0,this);
				}
				else {
					switch (mazeArray[i][j]) {

						case EMPTYWALL:
						case ROAD:
//						System.out.print(" ");
							drawoffScreen.setColor(Color.lightGray);
							drawoffScreen.fillOval( j, i, 2, 2);
							g.drawImage(offScreen,0,0,this);
							break;

						case OUTWALL:
						case INWALL:
//						System.out.print(Color.ANSI_CYAN + "▣"+ Color.ANSI_RESET);
							drawoffScreen.setColor(Color.PINK);
							drawoffScreen.fillOval( j, i, 2, 2);
							g.drawImage(offScreen,0,0,this);
							break;

						case BACK:
//						System.out.print(Color.ANSI_RED + "□"+ Color.ANSI_RESET);
							drawoffScreen.setColor(Color.RED);
							drawoffScreen.fillOval( j, i, 2, 2);
							g.drawImage(offScreen,0,0,this);
							break;

						case UP:
//						System.out.print(Color.ANSI_GREEN + "↑"+ Color.ANSI_RESET);
							drawoffScreen.setColor(Color.GREEN);
							drawoffScreen.fillOval( j, i, 2, 2);
							g.drawImage(offScreen,0,0,this);
							break;
						case DOWN:
//						System.out.print(Color.ANSI_GREEN + "↓"+ Color.ANSI_RESET);
							drawoffScreen.setColor(Color.GREEN);
							drawoffScreen.fillOval( j, i, 2, 2);
							g.drawImage(offScreen,0,0,this);
							break;
						case LEFT:
//						System.out.print(Color.ANSI_GREEN + "←"+ Color.ANSI_RESET);
							drawoffScreen.setColor(Color.GREEN);
							drawoffScreen.fillOval( j, i, 2, 2);
							g.drawImage(offScreen,0,0,this);
							break;
						case RIGHT:
//						System.out.print(Color.ANSI_GREEN + "→"+ Color.ANSI_RESET);
							drawoffScreen.setColor(Color.GREEN);
							drawoffScreen.fillOval( j, i, 2, 2);
							g.drawImage(offScreen,0,0,this);
							break;

						default:
							break;
					}
				}
			}
		}
	}


//	public static void main(String[] args) {
//		DemoTest demo = new DemoTest();
////		try {
////			Thread.sleep(3000);
////		} catch (InterruptedException e) {
////			e.printStackTrace();
////		}
//		demo.setVisible(true);
//	}
}
