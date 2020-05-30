package com.changda.logic;

import java.util.Random;

/**
 * @author 南街
 * @program lift-game
 * @classname MatrixOperation
 * @description 矩阵操作
 * @create 2020-05-23 13:50
 **/
public class MatrixLogic {
    // 用一个长度为8的数组来保存遍历每一个位置时，其周边位置的相对坐标偏移
    // 顺序：中竖，中横，左竖，右竖
    static int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1};
    static int[] dy = {0, 0, -1, 1, -1, 1, -1, 1};
    // 矩阵
    static int[][] board;
    // 行，列
    static int rows, cols;
    // 随机种子
    static Random random = new Random();

    private MatrixLogic() {
    }

    /**
     * 根据输入的行列，生成固定行列大小的随机细胞矩阵状态
     * @param rows 行
     * @param cols 列
     * @return int[][] 随机细胞矩阵
     * @date 2020/5/23 14:09
     */
    public static int[][] getMatrix(int rows,int cols){
        int[][] randomBoard = new int[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                randomBoard[row][col] = random.nextInt(2);
            }
        }
        return randomBoard;
    }

    /**
     * 推断矩阵的下一轮状态
     * @param initBoard 矩阵
     * @return boolean 操作是否有效
     * @date 2020/5/23 18:35
     */
    public static boolean gameOfLife(int[][] initBoard) {
        // 是否全部死亡标记，默认全部死亡
        boolean flag = false;

        board = initBoard;
        rows = initBoard.length;
        cols = initBoard[0].length;

        // 遍历
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 拿到当前位置周围活细胞数量
                int liveCount = countAlive(i, j);
                // 1. 活细胞周围八个位置有两个或三个活细胞，下一轮继续活
                if (board[i][j] == 1 && (liveCount == 2 || liveCount == 3)){
                    board[i][j] = 3;
                    flag = true;
                }
                // 2. 死细胞周围有三个活细胞，下一轮复活了
                if (board[i][j] == 0 && liveCount == 3){
                    board[i][j] = 2;
                    flag = true;
                }
            }
        }

        // 更新结果
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 将细胞状态转成0,1
                // 0 => 0, 1 => 0, 2 => 1, 3 => 1
                board[i][j] >>= 1;
            }
        }
        return flag;
    }

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
    private static int countAlive(int x, int y) {
        int liveCount = 0;
        // 判断周边的八个相对位置
        for (int k = 0; k < 8; k++) {
            int nx = x + dx[k];
            int ny = y + dy[k];
            // 处理数组越界问题
            if (nx < 0 || nx >= rows || ny < 0 || ny >= cols) continue;
            liveCount += (board[nx][ny] & 1);
        }
        return liveCount;
    }


}
