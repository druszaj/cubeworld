package cubeworldgame;

import java.awt.Graphics;
import java.awt.Color;

import java.util.*;

// Level Class
// ***********
// Purpose: Stores a group of rooms, and other information needed for level.

public class Level {

    // Attributes
    // **********

    public ArrayList<Room> rooms = new ArrayList<Room>(); // list of rooms
    public Color fogColor;
    public double fogStartDist;
    public double fogEndDist;

    public Room currentRoom;
    public int currentDirection;

    // Loaded Constructor
    // ******************
    // Purpose: Creates a level out of rooms.

    public Level(   ArrayList<Room> rooms, Color fogColor, double fogStartDist,
                    double fogEndDist) {

        // adding rooms
        for(Room room : rooms) {

            this.rooms.add(new Room(room));

            if(room.x == 0 && room.y == 0 && room.z == 0) {
                
                currentRoom = this.rooms.get(this.rooms.size() - 1);
            }
        }

        currentDirection = 0;

        Utility.insertionSort(rooms);

        this.fogColor = fogColor;
        this.fogStartDist = fogStartDist;
        this.fogEndDist = fogEndDist;
    }

    // Find Room Method
    // ****************
    // Purpose: Takes coordinates, and returns the room if found, else null.

    public Room findRoom(int x, int y, int z) {

        for(Room room : rooms) {

            if(room.x == x && room.y == y && room.z == z) return room;
        }

        return null;
    }
    
    // Turn Right Method
    // *****************
    // Purpose: Alters direction.

    public void turnRight() {

        if      (currentDirection == 0) currentDirection = 2;
        else if (currentDirection == 1) currentDirection = 3;
        else if (currentDirection == 2) currentDirection = 1;
        else if (currentDirection == 3) currentDirection = 0;
    }

    // Turn Left Method
    // ****************
    // Purpose: Alters direction.

    public void turnLeft() {

        if      (currentDirection == 0) currentDirection = 3;
        else if (currentDirection == 1) currentDirection = 2;
        else if (currentDirection == 2) currentDirection = 0;
        else if (currentDirection == 3) currentDirection = 1;
    }

    // Go Forward Method
    // *****************
    // Purpose: Alters current room, if way is passable.

    public boolean goForward() {

        if(currentDirection == 0) { // north

            Room room = findRoom(currentRoom.x, currentRoom.y + 1, currentRoom.z);

            if(room != null) {

                if(room.faces[1].isEmpty() && currentRoom.faces[0].isEmpty()) {

                    currentRoom = room;
                    return true;
                }

                return false;
            }

            return false;
        }

        if(currentDirection == 1) { // south

            Room room = findRoom(currentRoom.x, currentRoom.y - 1, currentRoom.z);

            if(room != null) {

                if(room.faces[0].isEmpty() && currentRoom.faces[1].isEmpty()) {

                    currentRoom = room;
                    return true;
                }

                return false;
            }

            return false;
        }

        if(currentDirection == 2) { // east

            Room room = findRoom(currentRoom.x + 1, currentRoom.y, currentRoom.z);

            if(room != null) {

                if(room.faces[3].isEmpty() && currentRoom.faces[2].isEmpty()) {

                    currentRoom = room;
                    return true;
                }

                return false;
            }

            return false;
        }

        if(currentDirection == 3) { // west

            Room room = findRoom(currentRoom.x - 1, currentRoom.y, currentRoom.z);

            if(room != null) {

                if(room.faces[2].isEmpty() && currentRoom.faces[3].isEmpty()) {

                    currentRoom = room;
                    return true;
                }

                return false;
            }

            return false;
        }

        return false;
    }

    // Go Backward Method
    // ******************
    // Purpose: Alters current room, if way is passable.

    public boolean goBackward() {

        if(currentDirection == 0) { // north

            Room room = findRoom(currentRoom.x, currentRoom.y - 1, currentRoom.z);

            if(room != null) {

                if(room.faces[0].isEmpty() && currentRoom.faces[1].isEmpty()) {

                    currentRoom = room;
                    return true;
                }

                return false;
            }

            return false;
        }

        if(currentDirection == 1) { // south

            Room room = findRoom(currentRoom.x, currentRoom.y + 1, currentRoom.z);

            if(room != null) {

                if(room.faces[1].isEmpty() && currentRoom.faces[0].isEmpty()) {

                    currentRoom = room;
                    return true;
                }

                return false;
            }

            return false;
        }

        if(currentDirection == 2) { // east

            Room room = findRoom(currentRoom.x - 1, currentRoom.y, currentRoom.z);

            if(room != null) {

                if(room.faces[2].isEmpty() && currentRoom.faces[3].isEmpty()) {

                    currentRoom = room;
                    return true;
                }

                return false;
            }

            return false;
        }

        if(currentDirection == 3) { // west

            Room room = findRoom(currentRoom.x + 1, currentRoom.y, currentRoom.z);

            if(room != null) {

                if(room.faces[3].isEmpty() && currentRoom.faces[2].isEmpty()) {

                    currentRoom = room;
                    return true;
                }

                return false;
            }

            return false;
        }

        return false;
    }

    // Draw Method
    // ***********
    // Purpose: Draws the level to the screen.

    public void draw(   Graphics g, int SCREEN_X, int SCREEN_Y,
                        double FOCAL_LENGTH) {

        g.setColor(fogColor);
        g.fillRect(0, 0, SCREEN_X, SCREEN_Y);

        Utility.insertionSort(rooms);

        for(Room room : rooms) {

            if(new Point3d().distance(room.getCenter()) <= fogEndDist)
                room.draw(  g, SCREEN_X, SCREEN_Y, FOCAL_LENGTH, fogColor,
                        fogStartDist, fogEndDist);
        }
    }
}
