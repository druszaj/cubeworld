package cubeworldgame;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import java.awt.FlowLayout;

import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.*;

// Screen Class
// *******************
// Purpose: Window/canvas to display upon and receive input from user.

public class Screen extends Canvas {
    
    // Attributes
    // **********

    private static final int SCREEN_X = 512, SCREEN_Y = 512;
    private static final int MILLIS_PER_FRAME = 32;
    private static double FOCAL_LENGTH = 256;
    private BufferStrategy strategy; // allows accelerated page flipping
    Calendar loopStart = Calendar.getInstance(); // for keeping maximum fps

    PolyTextureStore store = PolyTextureStore.getInstance(); // stores .pyt's in RAM

    Level level;
    private byte keys = 0;

    protected JPanel upperBar;
    protected JPanel lowerBar;
    protected JPanel rightBar;
    protected JPanel leftBar;

    // Default Constructor
    // *******************
    // Purpose: Constructs the program's window and canvas and sets up the world.

    public Screen() {

        JFrame container = new JFrame("Cube World Game");    // create new frame to contain canvas

        JPanel panel = (JPanel)container.getContentPane();                 // get panel
        panel.setPreferredSize(new Dimension(SCREEN_X + 32, SCREEN_Y + 32)); // set size
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        upperBar = new JPanel();
        upperBar.setPreferredSize(new Dimension(544, 16));
        upperBar.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        upperBar.setFocusable(false);

        lowerBar = new JPanel();
        lowerBar.setPreferredSize(new Dimension(544, 16));
        lowerBar.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        lowerBar.setFocusable(false);

        rightBar = new JPanel();
        rightBar.setPreferredSize(new Dimension(16, 512));
        rightBar.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        rightBar.setFocusable(false);

        leftBar = new JPanel();
        leftBar.setPreferredSize(new Dimension(16, 512));
        leftBar.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        leftBar.setFocusable(false);

        setBounds(0, 0, SCREEN_X, SCREEN_Y);   // set up canvas bounds
        setIgnoreRepaint(true);                         // no repaints, paints self

        panel.add(upperBar);
        panel.add(leftBar);
        panel.add(this); // add canvas to panel
        panel.add(rightBar);
        panel.add(lowerBar);
        container.setPreferredSize(new Dimension(SCREEN_X + 38, SCREEN_Y + 58));
        container.pack();                                         // arrange stuff
        container.setResizable(false);                            // no resizing
        container.setVisible(true);                               // make visible
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit on close

        addKeyListener(new KeyInputHandler());      // listen for keyboard events

        requestFocus(); // request focus so key events are triggered

        createBufferStrategy(2);        // create buffering strategy that allows
        strategy = getBufferStrategy(); // AWT to manage accelerated graphics

        setup();

        loop(); // begin the loop
    }

    // Setup Method
    // ************
    // Purpose: Sets up the program.

    private void setup() {

        ArrayList<Room> rooms = new ArrayList<Room>();

        for(int i = -3; i <= 3; i++) {

            for(int j = -3; j <= 3; j++) {

                if(i == 0 && j != 0) {

                    rooms.add(new Room(i, j, 0, "", "", "modern/stlmp000", "modern/stlmp000", "", "modern/flr001", store));
                }
                else if(i != 0 && j == 0) {

                    rooms.add(new Room(i, j, 0, "", "", "", "", "", "modern/flr001", store));
                }
                else if(i == 0 && j == 0) {

                    rooms.add(new Room(i, j, 0, "", "", "", "", "", "modern/flr001", store));
                }
                else if((i == 3 || i == -3 || i == 1 || i == -1) || (j == 3 || j == -3)) {

                    rooms.add(new Room(i, j, 0, "modern/grass000e", "modern/grass000e", "modern/grass000e", "modern/grass000e", "", "modern/grass000", store));
                }
                else if((i == 2 || i == -2) && j == 2) {

                    rooms.add(new Room(i, j, 0, "modern/bench000", "", "", "", "", "modern/flr000", store));
                }
                else if((i == 2 || i == -2) && j == -2) {

                    rooms.add(new Room(i, j, 0, "", "modern/bench000", "", "", "", "modern/flr000", store));
                }
                else {
                    
                    rooms.add(new Room(i, j, 0, "", "", "", "", "", "modern/flr000", store));
                }
            }
        }

        for(int i = -3; i <= 3; i++) {

            if(i == 0) {

                rooms.add(new Room(i, 4, 0, "", "", "", "", "", "modern/flr001", store));
                rooms.add(new Room(i, 4, 1, "modern/ext000t", "", "", "", "", "", store));
                rooms.add(new Room(i, 5, 0, "modern/ext000d", "", "modern/ext000b", "modern/ext000b", "modern/ext000c", "modern/flr001", store));
            }
            else {

                rooms.add(new Room(i, 4, 0, "modern/ext000b", "", "", "", "", "modern/flr001", store));
                rooms.add(new Room(i, 4, 1, "modern/ext000t", "", "", "", "", "", store));
            }

            rooms.add(new Room(i, -4, 0, "", "modern/ext000b", "", "", "", "modern/flr001", store));
            rooms.add(new Room(i, -4, 1, "", "modern/ext000t", "", "", "", "", store));

            rooms.add(new Room(4, i, 0, "", "", "modern/ext000b", "", "", "modern/flr001", store));
            rooms.add(new Room(4, i, 1, "", "", "modern/ext000t", "", "", "", store));

            rooms.add(new Room(-4, i, 0, "", "", "", "modern/ext000b", "", "modern/flr001", store));
            rooms.add(new Room(-4, i, 1, "", "", "", "modern/ext000t", "", "", store));
        }

        rooms.add(new Room(4, 4, 0, "modern/ext000b", "", "modern/ext000b", "", "", "modern/flr001", store));
        rooms.add(new Room(4, 4, 1, "modern/ext000t", "", "modern/ext000t", "", "", "", store));

        rooms.add(new Room(-4, -4, 0, "", "modern/ext000b", "", "modern/ext000b", "", "modern/flr001", store));
        rooms.add(new Room(-4, -4, 1, "", "modern/ext000t", "", "modern/ext000t", "", "", store));

        rooms.add(new Room(4, -4, 0, "", "modern/ext000b", "modern/ext000b", "", "", "modern/flr001", store));
        rooms.add(new Room(4, -4, 1, "", "modern/ext000t", "modern/ext000t", "", "", "", store));

        rooms.add(new Room(-4, 4, 0, "modern/ext000b", "", "", "modern/ext000b", "", "modern/flr001", store));
        rooms.add(new Room(-4, 4, 1, "modern/ext000t", "", "", "modern/ext000t", "", "", store));

        for(int i = -5; i <= 5; i++) {

            if(Math.abs(i) % 2 == 1) {

                rooms.add(new Room(i, 6, 2, "modern/ext000b", "", "modern/ext000b", "modern/ext000b", "", "", store));
                rooms.add(new Room(i, 6, 3, "modern/ext000t", "", "modern/ext000t", "modern/ext000t", "", "", store));

                rooms.add(new Room(i, -6, 2, "", "modern/ext000b", "modern/ext000b", "modern/ext000b", "", "", store));
                rooms.add(new Room(i, -6, 3, "", "modern/ext000t", "modern/ext000t", "modern/ext000t", "", "", store));

                rooms.add(new Room(6, i, 2, "modern/ext000b", "modern/ext000b", "modern/ext000b", "", "", "", store));
                rooms.add(new Room(6, i, 3, "modern/ext000t", "modern/ext000t", "modern/ext000t", "", "", "", store));

                rooms.add(new Room(-6, i, 2, "modern/ext000b", "modern/ext000b", "", "modern/ext000b", "", "", store));
                rooms.add(new Room(-6, i, 3, "modern/ext000t", "modern/ext000t", "", "modern/ext000t", "", "", store));
            }
            else {

                rooms.add(new Room(i, 5, 2, "modern/ext000b", "", "", "", "", "", store));
                rooms.add(new Room(i, 5, 3, "modern/ext000t", "", "", "", "", "", store));

                rooms.add(new Room(i, -5, 2, "", "modern/ext000b", "", "", "", "", store));
                rooms.add(new Room(i, -5, 3, "", "modern/ext000t", "", "", "", "", store));

                rooms.add(new Room(5, i, 2, "", "", "modern/ext000b", "", "", "", store));
                rooms.add(new Room(5, i, 3, "", "", "modern/ext000t", "", "", "", store));

                rooms.add(new Room(-5, i, 2, "", "", "", "modern/ext000b", "", "", store));
                rooms.add(new Room(-5, i, 3, "", "", "", "modern/ext000t", "", "", store));
            }
        }

        level = new Level(rooms, new Color(40,130,200), 2048, 4096);
    }

    // Loop Method
    // ***********
    // Purpose: Invokes the parts of the program many times a second.

    private void loop() {

        draw();
        
        while (true) {

            //draw();

            interpretKeys();
        }
    }

    // Draw Method
    // ***********
    // Purpose: Draws the screen and sleeps until a frame time has passed.

    private void draw() {

        Graphics g = strategy.getDrawGraphics();
        
        level.draw(g, SCREEN_X, SCREEN_Y, FOCAL_LENGTH);

        g.dispose();
        strategy.show();

        // sleep when we have not passed the minimum time per loop
        // until the point at which we will have passed it, or if
        // we have passed it, go to next iteration immediately

        long millisSinceFrame = Calendar.getInstance().getTimeInMillis() - loopStart.getTimeInMillis();

        if(millisSinceFrame < MILLIS_PER_FRAME) {

            try { Thread.sleep(MILLIS_PER_FRAME - millisSinceFrame); }
            catch (Exception e) {}
        }

        loopStart = Calendar.getInstance();    // record loop start time
                                               // to determine sleep logic
    }

    // Walk Method
    // ***********
    // Purpose: Moves the camera distance units over a certain number frames.

    private void walk(double distance, int steps) {

        if(distance < 0) { if(!level.goForward()) return; }
        else { if(!level.goBackward()) return; }

        // the amount to increment
        Point3d increment = new Point3d(0, 0, distance / steps);

        // incremental translate
        for(int i = 0; i < steps; i++) {

            for(int j = 0; j < level.rooms.size(); j++) {

                for(int k = 0; k < 6; k++) {

                    level.rooms.get(j).faces[k].plusEquals(increment);
                }
            }

            draw();
        }
    }
    
    // Turn Method
    // ***********
    // Purpose: Turns the camera radian degrees over a certain number frames.

    private void turn(double radians, int steps) {

        if(radians < 0) level.turnRight();
        else level.turnLeft();

        Point3d rot = new Point3d(0, radians / steps, 0); // the increment

        // pre-calculate sines and cosines
        double sx = Math.sin(rot.x);
        double cx = Math.cos(rot.x);
        double sy = Math.sin(rot.y);
        double cy = Math.cos(rot.y);
        double sz = Math.sin(rot.z);
        double cz = Math.cos(rot.z);

        // incremental rotate
        for(int i = 0; i < steps; i++) {

            for(int j = 0; j < level.rooms.size(); j++) {

                for(int k = 0; k < 6; k++) {

                    level.rooms.get(j).faces[k].rotateEqualsPrecalc(sx, cx, sy, cy, sz, cz);
                }
            }

            draw();
        }
    }

    // KeyInputHandler Class
    // *********************
    // Purpose: Sets key bitfield, depending on what keys are pressed/released.

    private class KeyInputHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

                if(e.getKeyCode() == KeyEvent.VK_UP ||
                   e.getKeyCode() == KeyEvent.VK_W) keys |= 1;

                if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
                   e.getKeyCode() == KeyEvent.VK_D) keys |= 2;

                if(e.getKeyCode() == KeyEvent.VK_DOWN ||
                   e.getKeyCode() == KeyEvent.VK_S) keys |= 4;

                if(e.getKeyCode() == KeyEvent.VK_LEFT ||
                   e.getKeyCode() == KeyEvent.VK_A) keys |= 8;
        }

        @Override
        public void keyReleased(KeyEvent e) {

            if(e.getKeyCode() == KeyEvent.VK_UP ||
               e.getKeyCode() == KeyEvent.VK_W) keys &= ~1;

            if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
               e.getKeyCode() == KeyEvent.VK_D) keys &= ~2;

            if(e.getKeyCode() == KeyEvent.VK_DOWN ||
               e.getKeyCode() == KeyEvent.VK_S) keys &= ~4;

            if(e.getKeyCode() == KeyEvent.VK_LEFT ||
               e.getKeyCode() == KeyEvent.VK_A) keys &= ~8;
        }
    }

    // Interpret Keys Method
    // *********************
    // Purpose: Takes appropriate action based on user's key input (if action
    //          is needed).

    private void interpretKeys() {

        if(keys != 0) { // if there is input

            if((keys & 1) != 0) {   // forward

                walk(-512, 30);
            }
            if((keys & 2) != 0) {   // right

                turn(-Math.PI / 2, 20);
            }
            if((keys & 4) != 0) {   // back

                walk(512, 30);
            }
            if((keys & 8) != 0) {   // left

                turn(Math.PI / 2, 20);
            }
        }
        else {

            try { Thread.sleep(MILLIS_PER_FRAME); }
            catch (Exception e) {}
        }
    }

    // Main Function
    // *************
    // Purpose: Constructs an Engine3dFrame.

    public static void main (String argv[]) {

        Screen frame = new Screen();
    }
}