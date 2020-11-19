package com.c0destudy.sokoban;

import com.c0destudy.sokoban.resource.Resource;
import com.c0destudy.sokoban.resource.Skin;
import com.c0destudy.sokoban.ui.frame.FrameManager;

public class Entry
{
    public static void main(String[] args) {
        Resource.init();
        Skin.setCurrentSkin(new Skin("Builder")); // use default skin
        FrameManager.showMainFrame();
    }
}
