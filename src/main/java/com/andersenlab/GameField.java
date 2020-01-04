package com.andersenlab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameField {
    private static final int CELLS_IN_ROW = 68;
    private static final int CELL_SIZE = 15;
    private static final int HEIGHT = CELL_SIZE * CELLS_IN_ROW;
    private static final int WIDTH = CELL_SIZE * CELLS_IN_ROW;
    private static final int M_SECONDS = 200;
    private final JPanel panel;
    private final List<Cell> cells = new ArrayList<>();
    private boolean isPressed = false;

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
                if (e.getKeyChar() == KeyEvent.VK_ENTER && !isPressed) { // The game starts when you pressed Enter
                    isPressed = true; // The game starts just once
                    Timer timer = new Timer(M_SECONDS, new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            step();
                        }
                    });
                    timer.start();
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
        cells.forEach(cell -> {
            int countOfNeighbors = countOfNeighbors(cell);
            if (countOfNeighbors == 4) {
                cell.markAlive(cell.isAlive());
            } else {
                cell.markAlive(countOfNeighbors == 3);
            }
        });

        cells.parallelStream().forEach(cell -> cell.setAlive(cell.isMarkedAlive()));
    }

    private int countOfNeighbors(Cell cell) {
        int index = cells.indexOf(cell);
        int i = index / CELLS_IN_ROW;
        int j = index % CELLS_IN_ROW;
        List<Integer> indexes = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                indexes.add(((i + x + CELLS_IN_ROW) % CELLS_IN_ROW) * CELLS_IN_ROW +
                        (j + y + CELLS_IN_ROW) % CELLS_IN_ROW);
            }
        }

        return (int) indexes.parallelStream().filter(integer -> cells.get(integer).isAlive()).count();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameField::new);
    }
}
