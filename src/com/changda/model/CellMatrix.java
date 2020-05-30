package com.changda.model;

import com.changda.logic.MatrixLogic;

import java.util.Arrays;

/**
 * @author 南街
 * @program lift-game
 * @classname CellMatrix
 * @description 细胞矩阵
 * @create 2020-05-23 11:07
 **/
public class CellMatrix {
    /**
     * 矩阵行
     */
    private int rows;

    /**
     * 矩阵列
     */
    private int cols;

    /**
     * 动画速度，每两个状态之间的毫秒数
     */
    private int duration;

    /**
     * 矩阵状态，1表示活，0表示死
     */
    private int[][] matrix;


    public CellMatrix(int rows, int cols, int duration) {
        this.rows = rows;
        this.cols = cols;
        this.duration = duration;
        this.matrix = MatrixLogic.getMatrix(rows,cols);
    }

    public int getRows() {
        return rows;
    }

    public CellMatrix setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public int getCols() {
        return cols;
    }

    public CellMatrix setCols(int cols) {
        this.cols = cols;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public CellMatrix setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public CellMatrix setMatrix(int[][] matrix) {
        this.matrix = matrix;
        return this;
    }

    @Override
    public String toString() {
        return "CellMatrix{" +
                "rows=" + rows +
                ", cols=" + cols +
                ", duration=" + duration +
                ", matrix=" + Arrays.deepToString(matrix) +
                '}';
    }
}
