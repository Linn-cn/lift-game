package com.changda.demo;

import java.sql.Time;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author 南街
 * @program lift-game
 * @classname DemoConsole
 * @description 控制台版本的生命游戏
 * @create 2020-05-23 10:07
 **/
public class DemoConsole {

    // 用一个长度为8的数组来保存遍历每一个位置时，其周边位置的相对坐标偏移
    // 顺序：中竖，中横，左竖，右竖
    int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1};
    int[] dy = {0, 0, -1, 1, -1, 1, -1, 1};
    // 矩阵
    int[][] board;
    // 行，列
    int rows, cols;

    // 随机种子
    static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        // int[][] board = {{0, 1, 0}, {0, 0, 1}, {1, 1, 1}, {0, 0, 0}};
        // new DemoConsole().gameOfLife(board);
        // System.out.println(Arrays.deepToString(board));
        int[][] randomBoard = new int[5][5];
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                randomBoard[row][col] = random.nextInt(2);
            }
        }
        System.out.println(Arrays.deepToString(randomBoard));
    }

    /**
     * 推断矩阵的下一轮状态
     *
     * @param board 矩阵
     */
    // public void gameOfLife(int[][] board) {
    //     this.board = board;
    //     this.rows = board.length;
    //     this.cols = board[0].length;
    //
    //     // 遍历
    //     for (int i = 0; i < rows; i++) {
    //         for (int j = 0; j < cols; j++) {
    //             // 拿到当前位置周围活细胞数量
    //             int liveCount = countAlive(i, j);
    //             // 1. 活细胞周围八个位置有两个或三个活细胞，下一轮继续活
    //             if (board[i][j] == 1 && (liveCount == 2 || liveCount == 3)) board[i][j] = 3;
    //             // 2. 死细胞周围有三个活细胞，下一轮复活了
    //             if (board[i][j] == 0 && liveCount == 3) board[i][j] = 2;
    //         }
    //     }
    //
    //     // 更新结果
    //     for (int i = 0; i < rows; i++) {
    //         for (int j = 0; j < cols; j++) {
    //             // 将细胞状态转成0,1
    //             // 0 => 0, 1 => 0, 2 => 1, 3 => 1
    //             board[i][j] >>= 1;
    //         }
    //     }
    // }

    /**
     * 分析：1 代表细胞活的， 0 代表细胞死的，那么这个位置就四种状态，用【下一个状态，当前状态】表示，最后需要用右移操作更新结果
     * <p>
     * 状态 0： 00，死的，下一轮还是死的
     * 状态 1： 01，活的，下一轮死了
     * 状态 2： 10，死的，下一轮活了
     * 状态 3： 11，活的，下一轮继续活着
     *
     * @param x 横轴
     * @param y 纵轴
     * @return 活细胞数
     */
    // private int countAlive(int x, int y) {
    //     int liveCount = 0;
    //     // 判断周边的八个相对位置
    //     for (int k = 0; k < 8; k++) {
    //         int nx = x + dx[k];
    //         int ny = y + dy[k];
    //         // 处理数组越界问题
    //         if (nx < 0 || nx >= rows || ny < 0 || ny >= cols) continue;
    //         liveCount += (board[nx][ny] & 1);
    //     }
    //     return liveCount;
    // }
}
