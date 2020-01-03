package com.andersenlab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameField {
    private static final int CELLS_IN_ROW = 68;
    private static final int CELL_SIZE = 15;
    private static final int HEIGHT = CELL_SIZE * CELLS_IN_ROW;
    private static final int WIDTH = CELL_SIZE * CELLS_IN_ROW;
    private final JPanel panel;
    private final List<Cell> cells = new ArrayList<>();

    public GameField() {
        JFrame frame = new JFrame("Game of Life");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        panel = new JPanel();
        panel.setLayout(new GridLayout(CELLS_IN_ROW, CELLS_IN_ROW, 0, 0));
        panel.setSize(WIDTH, HEIGHT);

        draw();
        frame.add(panel);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) { // One step of the game happens when you pressed Enter
                    step();
                }
            }
        });
    }

    private void draw() {
        Cell cell;
        for (int i = 0; i < CELLS_IN_ROW; i++) {
            for (int j = 0; j < CELLS_IN_ROW; j++) {
                cell = new Cell();
                cells.add(cell);
                panel.add(cell);
            }
        }
    }

    private void step() {
        int countOfNeighbors;

        for (Cell cell : cells) {
            countOfNeighbors = countOfNeighbors(cell);

            if (!cell.isAlive() && countOfNeighbors == 3) {
                cell.markAlive();
            } else if (cell.isAlive() && (countOfNeighbors == 2 || countOfNeighbors == 3)) {
                cell.markAlive();
            } else {
                cell.markDead();
            }
        }

        for (Cell cell : cells) {
            if (cell.isMarkedAlive()) {
                cell.setAlive();
            } else {
                cell.setDead();
            }
        }
    }

    private int countOfNeighbors(Cell cell) {
        int index = cells.indexOf(cell);
        int count = 0;
        int i = index / CELLS_IN_ROW;
        int j = index % CELLS_IN_ROW;
        List<Integer> indexes = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (!(x == 0 && y == 0)) {
                    indexes.add(((i + x + CELLS_IN_ROW) % CELLS_IN_ROW) * CELLS_IN_ROW +
                            (j + y + CELLS_IN_ROW) % CELLS_IN_ROW);
                }
            }
        }

        for (int number : indexes) {
            if (cells.get(number).isAlive()) {
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameField::new);
    }
}
