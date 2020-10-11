package com.c0destudy.sokoban.ui.frame;

import com.c0destudy.sokoban.ui.panel.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame
{
    private final int OFFSET = 30;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GameFrame frame = new GameFrame();
            frame.setVisible(true);
        });
    }

    public GameFrame() {
        super();
        initUI();
    }

    private void initUI() {
        final GamePanel panel = new GamePanel();
        setTitle("Sokoban");
        setSize(panel.getSize());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().add(panel);
        pack();
    }
}