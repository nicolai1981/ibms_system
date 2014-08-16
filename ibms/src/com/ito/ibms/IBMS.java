package com.ito.ibms;

import java.awt.EventQueue;

import com.ito.ibms.view.FrameHome;

public class IBMS {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrameHome frame = FrameHome.getInstance();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
