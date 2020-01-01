package com.andersenlab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cell extends JPanel {
    private boolean alive = false;
    private boolean aliveAgain = false;

    public void markAlive() {
        aliveAgain = true;
    }

    public void markDead() {
        aliveAgain = false;
    }

    public boolean isMarkedAlive() {
        return aliveAgain;
    }

    public Cell() {
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isAlive()) {
                    setDead();
                } else {
                    setAlive();
                }
            }
        });
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive() {
        alive = true;
        setBackground(Color.BLACK);
    }

    public void setDead() {
        alive = false;
        setBackground(Color.WHITE);
    }
}
