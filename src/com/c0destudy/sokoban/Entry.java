package com.c0destudy.sokoban;

import com.c0destudy.sokoban.ui.frame.MainFrame;

import java.awt.*;

public class Entry
{
    // 프로그램 시작 클래스

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
