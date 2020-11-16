package com.c0destudy.sokoban;

import com.c0destudy.sokoban.resource.Resource;
import com.c0destudy.sokoban.ui.frame.FrameManager;

public class Entry
{
    // 프로그램 시작 클래스
    public static void main(String[] args) {
        Resource.initResource();
        FrameManager.showMainFrame();
    }
}
