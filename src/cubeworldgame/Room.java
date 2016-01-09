package cubeworldgame;

import java.awt.Graphics;
import java.awt.Color;

// Room Class
// **********
// Purpose: Stores up to 6 Face3d's, representing the surfaces of a cubic room.
//          Also stores the location, where 1 room unit = 512 world units.

public class Room implements Comparable {

    // Attributes
    // **********

    public int x, y, z;
    public Face3d [] faces = new Face3d [6]; // NSEWUD

    // Default Constructor
    // *******************
    // Purpose: Creates a zero room with no faces.

    public Room () {

        x = 0;
        y = 0;
        z = 0;
    }

    // Faceless Constructor
    // ********************
    // Purpose: Creates a room at given position with no faces.

    public Room (int x, int y, int z) {

        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Full Constructor
    // ****************
    // Purpose: Creates a room at given position with given textures. Textures
    //          can be "", in which case they will not be drawn. Textures must
    //          already be in the texture store (you must load them first). In
    //          addition, this method must be provided the store to load from.

    public Room (int x, int y, int z, String north, String south, String east,
                 String west, String up, String down, PolyTextureStore store) {

        this.x = x;
        this.y = y;
        this.z = z;

        // set up faces

        if(!north.isEmpty())
        this.faces[0] = new Face3d(

            new Point3d(-256 + (x * 512), 256 + (z * 512), 256 + (y * 512)),
            new Point3d(256 + (x * 512), 256 + (z * 512), 256 + (y * 512)),
            new Point3d(256 + (x * 512), -256 + (z * 512), 256 + (y * 512)),
            new Point3d(-256 + (x * 512), -256 + (z * 512), 256 + (y * 512)),
            store.loadGet(north)
        );
        else
        this.faces[0] = new Face3d(

            new Point3d(-256 + (x * 512), 256 + (z * 512), 256 + (y * 512)),
            new Point3d(256 + (x * 512), 256 + (z * 512), 256 + (y * 512)),
            new Point3d(256 + (x * 512), -256 + (z * 512), 256 + (y * 512)),
            new Point3d(-256 + (x * 512), -256 + (z * 512), 256 + (y * 512))
        );

        if(!south.isEmpty())
        this.faces[1] = new Face3d(

            new Point3d(256 + (x * 512), 256 + (z * 512), -256 + (y * 512)),
            new Point3d(-256 + (x * 512), 256 + (z * 512), -256 + (y * 512)),
            new Point3d(-256 + (x * 512), -256 + (z * 512), -256 + (y * 512)),
            new Point3d(256 + (x * 512), -256 + (z * 512), -256 + (y * 512)),
            store.loadGet(south)
        );
        else
        this.faces[1] = new Face3d(

            new Point3d(256 + (x * 512), 256 + (z * 512), -256 + (y * 512)),
            new Point3d(-256 + (x * 512), 256 + (z * 512), -256 + (y * 512)),
            new Point3d(-256 + (x * 512), -256 + (z * 512), -256 + (y * 512)),
            new Point3d(256 + (x * 512), -256 + (z * 512), -256 + (y * 512))
        );

        if(!east.isEmpty())
        this.faces[2] = new Face3d(

            new Point3d(256 + (x * 512), 256 + (z * 512), 256 + (y * 512)),
            new Point3d(256 + (x * 512), 256 + (z * 512), -256 + (y * 512)),
            new Point3d(256 + (x * 512), -256 + (z * 512), -256 + (y * 512)),
            new Point3d(256 + (x * 512), -256 + (z * 512), 256 + (y * 512)),
            store.loadGet(east)
        );
        else
        this.faces[2] = new Face3d(

            new Point3d(256 + (x * 512), 256 + (z * 512), 256 + (y * 512)),
            new Point3d(256 + (x * 512), 256 + (z * 512), -256 + (y * 512)),
            new Point3d(256 + (x * 512), -256 + (z * 512), -256 + (y * 512)),
            new Point3d(256 + (x * 512), -256 + (z * 512), 256 + (y * 512))
        );

        if(!west.isEmpty())
        this.faces[3] = new Face3d(

            new Point3d(-256 + (x * 512), 256 + (z * 512), -256 + (y * 512)),
            new Point3d(-256 + (x * 512), 256 + (z * 512), 256 + (y * 512)),
            new Point3d(-256 + (x * 512), -256 + (z * 512), 256 + (y * 512)),
            new Point3d(-256 + (x * 512), -256 + (z * 512), -256 + (y * 512)),
            store.loadGet(west)
        );
        else
        this.faces[3] = new Face3d(

            new Point3d(-256 + (x * 512), 256 + (z * 512), -256 + (y * 512)),
            new Point3d(-256 + (x * 512), 256 + (z * 512), 256 + (y * 512)),
            new Point3d(-256 + (x * 512), -256 + (z * 512), 256 + (y * 512)),
            new Point3d(-256 + (x * 512), -256 + (z * 512), -256 + (y * 512))
        );

        if(!up.isEmpty())
        this.faces[4] = new Face3d(

            new Point3d(-256 + (x * 512), 256 + (z * 512), -256 + (y * 512)),
            new Point3d(256 + (x * 512), 256 + (z * 512), -256 + (y * 512)),
            new Point3d(256 + (x * 512), 256 + (z * 512), 256 + (y * 512)),
            new Point3d(-256 + (x * 512), 256 + (z * 512), 256 + (y * 512)),
            store.loadGet(up)
        );
        else
        this.faces[4] = new Face3d(

            new Point3d(-256 + (x * 512), 256 + (z * 512), -256 + (y * 512)),
            new Point3d(256 + (x * 512), 256 + (z * 512), -256 + (y * 512)),
            new Point3d(256 + (x * 512), 256 + (z * 512), 256 + (y * 512)),
            new Point3d(-256 + (x * 512), 256 + (z * 512), 256 + (y * 512))
        );

        if(!down.isEmpty())
        this.faces[5] = new Face3d(

            new Point3d(-256 + (x * 512), -256 + (z * 512), 256 + (y * 512)),
            new Point3d(256 + (x * 512), -256 + (z * 512), 256 + (y * 512)),
            new Point3d(256 + (x * 512), -256 + (z * 512), -256 + (y * 512)),
            new Point3d(-256 + (x * 512), -256 + (z * 512), -256 + (y * 512)),
            store.loadGet(down)
        );
        else
        this.faces[5] = new Face3d(

            new Point3d(-256 + (x * 512), -256 + (z * 512), 256 + (y * 512)),
            new Point3d(256 + (x * 512), -256 + (z * 512), 256 + (y * 512)),
            new Point3d(256 + (x * 512), -256 + (z * 512), -256 + (y * 512)),
            new Point3d(-256 + (x * 512), -256 + (z * 512), -256 + (y * 512))
        );
    }

    // Copy Constructor
    // ****************
    // Purpose: Creates a deep copy of a room.

    public Room(Room other) {

        this.x = other.x;
        this.y = other.y;
        this.z = other.z;

        for(int i = 0; i < faces.length; i++) {

            if(other.faces[i] != null) {

                this.faces[i] = new Face3d(other.faces[i]);
            }
        }
    }

    // GetCenter Method
    // ****************
    // Purpose: Returns the center point of the room. Room must have down face
    //          for this to work.

    public Point3d getCenter() {

        return faces[4].getCenter().plus(faces[5].getCenter()).divideBy(2);
    }

    // Draw Method
    // ***********
    // Purpose: Draws the room.

    public void draw(   Graphics g, int SCREEN_X, int SCREEN_Y,
                        double FOCAL_LENGTH, Color fogColor,
                        double fogStartDist, double fogEndDist) {

        double fogAmount = 0;
        double dist = new Point3d().distance(getCenter());

        if(dist >= fogStartDist) {

            fogAmount = (dist - fogStartDist) / (fogEndDist - fogStartDist);
        }

        // perform sorting but do not disturb order

        Face3d [] sortedFaces = new Face3d[6];

        for(int i = 0; i < 6; i++) {

            sortedFaces[i] = faces[i];
        }

        Utility.insertionSort(sortedFaces);

        // draw each, properly sorted
        
        for(int i = 0; i < 6; i++) {

            sortedFaces[i].draw(  g, SCREEN_X, SCREEN_Y, FOCAL_LENGTH, fogColor,
                            fogAmount);
        }
    }

    // CompareTo Method
    // ****************
    // Purpose: For sorting by the distance from the origin.

    public int compareTo(Object other) {

        Room otherRoom = (Room)other;

        if(this.getCenter().distance(new Point3d()) <
            otherRoom.getCenter().distance(new Point3d())) return 1;
        else return -1;
    }
}
