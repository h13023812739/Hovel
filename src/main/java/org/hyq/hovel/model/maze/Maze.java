package org.hyq.hovel.model.maze;

import lombok.Data;
import org.hyq.hovel.enums.BlockEnum;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class Maze {

    static int ROW = 408;
    static int COL = 488;

    Block IN = new Block(1, 0);
    Block OUT = new Block(0, 1);

    private boolean[][] heartArray = new boolean[ROW][COL];
    private BlockEnum[][] mazeArray = new BlockEnum[ROW][COL];
    private boolean[][] unmaskArray = new boolean[ROW][COL];

    public void Maze(){
        System.out.println("================Maze 无参构造 方法===================");
    }

    public void init() throws Exception {
        System.out.println("================Maze init 方法===================");
        initHeart();
        initMaze();
        updateMaze();
//        draw();
    }

    private void initHeart() {
        int row = 1, col = 1;
        for (double y = 2.0f; y >= -2.0f; y -= 0.01f) {
            for (double x = -2.0f; x <= 2.0f; x += 0.01f) {
                double temp = Math.pow(x, 2) + Math.pow(y, 2) - 1;
                if (Math.pow(temp, 3) - Math.pow(x, 2) * Math.pow(y, 3) <= 0.0)
                    heartArray[row][col] = true;
                else
                    heartArray[row][col] = false;
                col++;
            }
            row++; col = 1;
        }
        System.out.println("初始心形图案结束");
//        for(int i = 0;i<heartArray.length;i++){
//            for(int j = 0;j<heartArray[i].length;j++){
//                System.out.print(heartArray[i][j]==true?"X":" ");
//            }
//            System.out.println(" ");
//        }

    }

    private void initMaze() {
        for (int i = 1; i < ROW - 1; i++) {
            for (int j = 1; j < COL - 1; j++) {
                if (heartArray[i][j] && !(heartArray[i - 1][j] && heartArray[i + 1][j] && heartArray[i][j - 1] && heartArray[i][j + 1])) {
                    mazeArray[i][j] = BlockEnum.OUTWALL;
                }
                else if (heartArray[i][j] && i % 2 == 0 && j % 2 == 0) {
                    mazeArray[i][j] = BlockEnum.ROAD;
                }
                else if (heartArray[i][j]) {
                    mazeArray[i][j] = BlockEnum.INWALL;
                }
                else {
                    mazeArray[i][j] =  BlockEnum.EMPTYWALL;
                }
            }
        }
//        for(int i = 0;i<mazeArray.length;i++){
//            for(int j = 0;j<mazeArray[i].length;j++){
//                System.out.print(mazeArray[i][j]==BlockEnum.OUTWALL?"X":(mazeArray[i][j]==BlockEnum.ROAD?"O":(mazeArray[i][j]==BlockEnum.INWALL?"W":"")));
//            }
//            System.out.println(" ");
//        }
        for (int i = 0; i < COL; i++) {
            mazeArray[0][i] = BlockEnum.EMPTYWALL;
            mazeArray[ROW-1][i] = BlockEnum.EMPTYWALL;
            unmaskArray[0][i] = true;
            unmaskArray[ROW-1][i] = true;
        }
        for (int i = 0; i < ROW; i++) {
            mazeArray[i][0] = BlockEnum.EMPTYWALL;
            mazeArray[i][COL-1] = BlockEnum.EMPTYWALL;
            unmaskArray[i][0] = true;
            unmaskArray[i][COL-1] = true;
        }
//        for(int i = 0;i<mazeArray.length;i++){
//            for(int j = 0;j<mazeArray[i].length;j++){
//                System.out.print(mazeArray[i][j]==BlockEnum.OUTWALL?"X":(mazeArray[i][j]==BlockEnum.ROAD?"O":(mazeArray[i][j]==BlockEnum.INWALL?"W":"")));
//            }
//            System.out.println(" ");
//        }
        mazeArray[IN.row][IN.col] = BlockEnum.ROAD;
        mazeArray[OUT.row][OUT.col] = BlockEnum.ROAD;
        System.out.println("初始化迷宫结束");
    }

    private void updateMaze() throws Exception {
        java.util.List<Block> list = new ArrayList<>();
        boolean[][] visited = new boolean[ROW][COL];
        list.add(IN);
        visited[IN.row][IN.col] = true;
        removeMask(IN);
        randomBFS(list, visited);
        removeOutWallMask();
        for(int i = 0;i<unmaskArray.length;i++){
            for(int j = 0;j<unmaskArray[i].length;j++){
                System.out.print(unmaskArray[i][j]?"X":" ");
            }
            System.out.println(" ");
        }
        System.out.println("更新路径结束");
    }

    private void randomBFS(List<Block> list, boolean[][] visited) throws Exception {
        while (list.size() != 0) {
            Block block = list.remove((int) (Math.random()*list.size()));
            int up = block.row - 2;
            int down = block.row + 2;
            int left = block.col - 2;
            int right = block.col + 2;
            int curRow = block.row;
            int curCol = block.col;
            Block temp;

            if (up >= 0 && mazeArray[up][curCol] == BlockEnum.ROAD && !visited[up][curCol]) {
                mazeArray[block.row-1][curCol] = BlockEnum.ROAD;
                visited[up][curCol] = true;
                temp = new Block(up, curCol);
                removeMask(temp);
                list.add(temp);
            }
            if (down < ROW && mazeArray[down][curCol] == BlockEnum.ROAD && !visited[down][curCol]) {
                mazeArray[block.row+1][curCol] = BlockEnum.ROAD;
                visited[down][curCol] = true;
                temp = new Block(down, curCol);
                removeMask(temp);
                list.add(temp);
            }
            if (left >= 0 && mazeArray[curRow][left] == BlockEnum.ROAD && !visited[curRow][left]) {
                mazeArray[curRow][block.col-1] = BlockEnum.ROAD;
                visited[curRow][left] = true;
                temp = new Block(curRow, left);
                removeMask(temp);
                list.add(temp);
            }
            if (right < COL && mazeArray[curRow][right] == BlockEnum.ROAD && !visited[curRow][right]) {
                mazeArray[curRow][block.col+1] = BlockEnum.ROAD;
                visited[curRow][right] = true;
                temp = new Block(curRow, right);
                removeMask(temp);
                list.add(temp);
            }
//            draw();
            Thread.sleep(40);
        }
    }



    private void removeMask(Block block) {
        for (int i = block.row-1; i <= block.row+1; i++)
            for (int j = block.col-1; j <= block.col+1; j++) {
                if (i >= 0 && i < ROW && j >= 0 && j <COL) {
                    unmaskArray[i][j] = true;
                }
            }
    }

    private void removeOutWallMask() throws Exception {
        for (int i = 1; i < ROW - 1; i++) {
            for (int j = 1; j < COL - 1; j++) {
                if (mazeArray[i][j] == BlockEnum.OUTWALL) {
                    unmaskArray[i][j] = true;
//                    draw();
                    Thread.sleep(20);
                }
            }
        }
    }

    public BlockEnum[][] getMaze() {
        return mazeArray;
    }

}

