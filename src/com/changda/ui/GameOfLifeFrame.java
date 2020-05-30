package com.changda.ui;

import com.changda.model.CellMatrix;
import com.changda.logic.MatrixLogic;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 南街
 * @program lift-game
 * @classname CellMatrix
 * @description 生命游戏界面
 * @create 2020-05-23 11:07
 **/
public class GameOfLifeFrame extends JFrame {

    /**
     * 按钮组
     */
    private final JButton randomGameBtn = new JButton("生成随机细胞矩阵");
    private final JButton clearGameBtn = new JButton("清空细胞矩阵");
    private final JButton nextGameBtn = new JButton("进行单步演化");
    private final JButton automaticBtn = new JButton("开始自动演化");
    private final JButton stopBtn = new JButton("结束自动演化");

    /**
     * 文本组
     */
    private final JTextField durationText = new JTextField();
    private final JTextField rowsText = new JTextField();
    private final JTextField colsText = new JTextField();

    /**
     * 游戏结束的标志
     */
    private volatile boolean stop = false;

    /**
     * 工作线程
     */
    private Thread worker;

    /**
     * 细胞矩阵
     */
    private CellMatrix cellMatrix;

    /**
     * UI
     */
    private JPanel gridPanel = new JPanel();

    /**
     * 显示的矩阵
     */
    private JTextField[][] textMatrix;

    /**
     * 动画默认间隔1000ms
     */
    private static final int DEFAULT_DURATION = 1000;
    /**
     * 默认行10
     */
    private static final int DEFAULT_ROWS = 10;
    /**
     * 默认列10
     */
    private static final int DEFAULT_COLS = 10;

    public GameOfLifeFrame() {
        setTitle("生命游戏-17计科三班,朱林");
        // 初始化按钮
        initTextFiled();
        // 绑定按钮和监听器
        bindButtonListener();
        // 设置按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        buttonPanel.add(randomGameBtn);
        buttonPanel.add(clearGameBtn);
        buttonPanel.add(nextGameBtn);
        buttonPanel.add(automaticBtn);
        buttonPanel.add(stopBtn);

        JLabel durationLabel = new JLabel("间隔设置(单位:ms,需要>500ms)");
        durationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel colsLabel = new JLabel("列设置(>5)");
        colsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel rowsLabel = new JLabel("行设置(>5)");
        rowsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel textPanel = new JPanel(new GridLayout(3, 2));
        textPanel.add(rowsLabel);
        textPanel.add(rowsText);
        textPanel.add(colsLabel);
        textPanel.add(colsText);
        textPanel.add(durationLabel);
        textPanel.add(durationText);

        getContentPane().add(BorderLayout.NORTH, buttonPanel);
        getContentPane().add(BorderLayout.SOUTH, textPanel);

        this.setSize(800, 800);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 初始化文本框
     */
    private void initTextFiled() {
        durationText.setText(String.valueOf(DEFAULT_DURATION));
        rowsText.setText(String.valueOf(DEFAULT_ROWS));
        colsText.setText(String.valueOf(DEFAULT_COLS));
    }

    /**
     * 绑定按钮和监听事件
     */
    private void bindButtonListener() {
        randomGameBtn.addActionListener(e -> {
            try {
                int rows = Integer.parseInt(rowsText.getText().trim());
                int cols = Integer.parseInt(colsText.getText().trim());
                int duration = Integer.parseInt(durationText.getText().trim());
                if (rows < 5 || cols < 5 || duration < 500) {
                    alertMessage("输入参数有误，请检查", "出错啦！");
                    return;
                }
                cellMatrix = new CellMatrix(rows, cols, duration);
            } catch (NumberFormatException e1) {
                alertMessage("输入参数有误，请检查", "出错啦！");
                return;
            }
            initGridLayout();
            showMatrix();
            gridPanel.updateUI();
        });
        nextGameBtn.addActionListener(e -> {
            if (isRun()) return;
            if (!gameOfLife()) return;
            showMatrix();
            gridPanel.updateUI();
        });
        clearGameBtn.addActionListener(e -> {
            if (isRun()) return;
            cellMatrix.setMatrix(null);
            initGridLayout();
            gridPanel.updateUI();
        });
        automaticBtn.addActionListener(e -> {
            if (isRun()) return;
            worker = new Thread(() -> {
                while (!stop) {
                    if (!gameOfLife()) {
                        worker = null;
                        return;
                    }
                    showMatrix();
                    gridPanel.updateUI();
                    try {
                        TimeUnit.MILLISECONDS.sleep(cellMatrix.getDuration());
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                stop = false;
            });
            worker.start();
        });
        stopBtn.addActionListener(e -> {
            if (worker != null && worker.isAlive()) {
                stop = true;
                worker = null;
            }
        });
    }

    /**
     * 判断游戏是否已经运行
     */
    private boolean isRun() {
        if (worker != null && worker.isAlive()) {
            alertMessage("当前游戏正在运行，请先停止", "提示");
            return true;
        }
        return false;
    }

    /**
     * 修改UI
     */
    private void showMatrix() {
        int[][] matrix = cellMatrix.getMatrix();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                if (matrix[y][x] == 1) {
                    textMatrix[y][x].setBackground(Color.BLACK);
                } else {
                    textMatrix[y][x].setBackground(Color.WHITE);
                }
                textMatrix[y][x].setEditable(false);
            }
        }
    }

    /**
     * 创建显示的布局
     */
    private void initGridLayout() {
        int rows = cellMatrix.getRows();
        int cols = cellMatrix.getCols();
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, cols));
        textMatrix = new JTextField[rows][cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                JTextField text = new JTextField();
                textMatrix[y][x] = text;
                gridPanel.add(text);
            }
        }
        add(BorderLayout.CENTER, gridPanel);
    }

    /**
     * 提示框
     *
     * @param msg   提示信息
     * @param title 标题
     */
    private void alertMessage(String msg, String title) {
        JOptionPane.showMessageDialog(gridPanel, msg, title, JOptionPane.WARNING_MESSAGE);
    }

    /**
     * 细胞演化
     */
    private boolean gameOfLife() {
        if (cellMatrix == null || cellMatrix.getMatrix() == null) {
            alertMessage("还未初始化细胞矩阵！", "提示");
            return false;
        }
        if (!MatrixLogic.gameOfLife(cellMatrix.getMatrix())) {
            alertMessage("当前细胞全部死亡，游戏结束", "提示");
            initGridLayout();
            gridPanel.updateUI();
            return false;
        }
        return true;
    }
}
