package cubeworldgame;

import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;

// Face3d Class
// ************
// Purpose: Stores 4 Point3d's, a polygon list, and a visibility flag,
//          representing a textured quadrangle (sometimes triangle) in 3d space.

public class Face3d implements Comparable {
    
        // Attributes
        // **********
    
        public Point3d [] corners = new Point3d[4]; // the 4 corner points
        public ArrayList<Poly3d> polys = new ArrayList<Poly3d>(); // list of polys of face
        public boolean visible; // stores whether polygon is visible or not
        
        // Single Color Tri Constructor
        // ****************************
        // Purpose: Creates a triangle face with one color throughout.

        public Face3d(Point3d nw, Point3d ne, Point3d sesw, Color clr) {
            
                corners[0] = new Point3d(nw);
                corners[1] = new Point3d(ne);
                corners[2] = new Point3d(sesw);
                corners[3] = new Point3d(sesw);
                
                polys.add(new Poly3d(corners, clr));
                
                updateVisibility();
        }

        // Invisible Quad Constructor
        // **************************
        // Purpose: Creates a quadrangle face with that is invisible.

        public Face3d(Point3d nw, Point3d ne, Point3d se, Point3d sw) {

                corners[0] = new Point3d(nw);
                corners[1] = new Point3d(ne);
                corners[2] = new Point3d(se);
                corners[3] = new Point3d(sw);

                updateVisibility();
        }
        
        // Single Color Quad Constructor
        // *****************************
        // Purpose: Creates a quadrangle face with one color throughout.

        public Face3d(Point3d nw, Point3d ne, Point3d se, Point3d sw, Color clr) {
            
                corners[0] = new Point3d(nw);
                corners[1] = new Point3d(ne);
                corners[2] = new Point3d(se);
                corners[3] = new Point3d(sw);
                
                polys.add(new Poly3d(corners, clr));
                
                updateVisibility();
        }
        
        // Textured Quad Constructor
        // *************************
        // Purpose: Creates a quadrangle face with a texture.

        public Face3d(Point3d nw, Point3d ne, Point3d se, Point3d sw, PolyTexture pyt) {
            
                corners[0] = new Point3d(nw);
                corners[1] = new Point3d(ne);
                corners[2] = new Point3d(se);
                corners[3] = new Point3d(sw);
                
                loadPolyTexture(pyt);

                updateVisibility();
        }

        // Copy Constructor
        // ****************
        // Purpose: Creates a deep copy of another Face3d.

        public Face3d(Face3d other) {

                for(int i = 0; i < other.corners.length; i++) {

                    corners[i] = new Point3d(other.corners[i]);
                }

                for(int i = 0; i < other.polys.size(); i++) {

                    polys.add(new Poly3d(other.polys.get(i)));
                }

                visible = other.visible;
        }
        
        // Get Center Method
        // *****************
        // Purpose: Returns the center (the average of corners).
        
        public Point3d getCenter() {
            
                return corners[0].plus(corners[1]).plus(corners[2]).plus(corners[3]).divideBy(4);
        }
        
        // Get Dist Method
        // ***************
        // Purpose: Returns the distance from the origin.
        
        public double getDist() {

                return new Point3d().distance(corners[0].plus(corners[2]).divideBy(2));
        }
        
        // Update Visibility Method
        // ************************
        // Purpose: Updates face's visibility flag.
        
        public boolean updateVisibility() {
            
            /*
                visible = false;
                
                for(int i = 0; i < polys.size(); i++) {
                    
                    if(polys.get(i).visible) visible = true;
                }
                
                return visible;*/
            
                visible = false;
                
                for(int i = 0; i < corners.length; i++) {
                    
                    if(corners[i].z > 0.1) visible = true;
                }
                
                return visible;
        }
        
        public Point3d texturePoint2FacePoint(int x, int y) {
            
                Point3d deltaTop = corners[1].minus(corners[0]);
                Point3d topPoint = corners[0].plus(deltaTop.divideBy(512).times(x));
                Point3d deltaBottom = corners[2].minus(corners[3]);
                Point3d bottomPoint = corners[3].plus(deltaBottom.divideBy(512).times(x));
                Point3d deltaLine = bottomPoint.minus(topPoint);
                Point3d result = topPoint.plus(deltaLine.divideBy(512).times(y));
                return result;
        }
        
        // Load PolyTexture Method
        // ***********************
        // Purpose: Loads a PolyTexture onto the face.
        
        public void loadPolyTexture(PolyTexture pyt) {
            
                ArrayList<Point3d> currentPoints = new ArrayList<Point3d>();

                String [] separated = pyt.data.split("<polygon\\s*|\\s*/>"); // split around polygon tag

                ArrayList<String> spolys = new ArrayList<String>();

                for(int i = 0; i < separated.length; i++) {

                    // builds arraylist in spoly of string entries with the form
                    // points="320,224 320,288 336,256" fill="rgb(255,255,255)"
                    if(separated[i].matches("^points=\".*"))
                        spolys.add(separated[i].trim());
                }

                for(int i = 0; i < spolys.size(); i++) {

                    String [] pointsandfill = spolys.get(i).split("\" fill=\"rgb\\(");

                    for(int j = 0; j < pointsandfill.length; j++) {

                        // builds array of strings in pointsandfill of the form
                        // 0,0 512,0 512,512 0,512
                        // 255,255,255
                        pointsandfill[j] = pointsandfill[j].replaceAll("points=\"|\\)\"", "");

                        if(j % 2 == 0) { // even element

                            // split by spaces
                            // into string array coords where elements have form 512,64
                            String [] coords = pointsandfill[j].split(" ");

                            for(int k = 0; k < coords.length; k++) {

                                String [] ptstmp = coords[k].split(",");

                                currentPoints.add(texturePoint2FacePoint(
                                        Integer.parseInt(ptstmp[0]), Integer.parseInt(ptstmp[1])));
                            }
                        }
                        else { // odd element

                            String [] rgb = pointsandfill[j].split(",");

                            polys.add(new Poly3d(currentPoints,
                                    new Color(  Integer.parseInt(rgb[0]),
                                                Integer.parseInt(rgb[1]),
                                                Integer.parseInt(rgb[2]),
                                                255)));
                            
                            currentPoints.clear();
                        }
                    }
                }

                return;
        }
        
        // Add-Assign Point3d Method
        // *************************
        // Purpose: Adds and assigns to face, then returns it.
        
        public Face3d plusEquals(Point3d other) {
            
                for(int i = 0; i < polys.size(); i++) {
                    
                    polys.get(i).plusEquals(other);
                    //polys.get(i).updateVisibility();
                }
                
                for(int i = 0; i < corners.length; i++) {
                    
                    corners[i].plusEquals(other);
                }
                
                updateVisibility();
                
                return this;
        }
        
        // Subtract-Assign Point3d Method
        // ******************************
        // Purpose: Subtracts from and assigns to face, then returns it.
        
        public Face3d minusEquals(Point3d other) {
            
                return plusEquals(other.negative());
        }
        
        // Rotate-Assign Around Origin Method
        // **********************************
        // Purpose: Rotates face around origin, returns this.
        
        public Face3d rotateEquals(Point3d rot) {
            
                // pre-calculate sines and cosines
                double sx = Math.sin(rot.x);
                double cx = Math.cos(rot.x);
                double sy = Math.sin(rot.y);
                double cy = Math.cos(rot.y);
                double sz = Math.sin(rot.z);
                double cz = Math.cos(rot.z);
                
                for(int i = 0; i < polys.size(); i++) {
                    
                    Poly3d poly = polys.get(i);
                    
                    for(int j = 0; j < poly.points.length; j++) {
            
                        // rotation around x
                        double xy = cx*poly.points[j].y - sx*poly.points[j].z;
                        double xz = sx*poly.points[j].y + cx*poly.points[j].z;

                        // rotation around y
                        double yz = cy*xz - sy*poly.points[j].x;
                        double yx = sy*xz + cy*poly.points[j].x;

                        // rotation around z
                        double zx = cz*yx - sz*xy;
                        double zy = sz*yx + cz*xy;

                        // apply changes
                        poly.points[j].set(zx, zy, yz);
                    }
                    
                    //poly.updateVisibility();
                }
                
                for(int j = 0; j < corners.length; j++) {
            
                        // rotation around x
                        double xy = cx*corners[j].y - sx*corners[j].z;
                        double xz = sx*corners[j].y + cx*corners[j].z;

                        // rotation around y
                        double yz = cy*xz - sy*corners[j].x;
                        double yx = sy*xz + cy*corners[j].x;

                        // rotation around z
                        double zx = cz*yx - sz*xy;
                        double zy = sz*yx + cz*xy;

                        // apply changes
                        corners[j].set(zx, zy, yz);
                }
                
                updateVisibility();
                
                // return result
                return this;
        }
        
        // Precalculated Rotate-Assign Around Origin Method
        // ************************************************
        // Purpose: Rotates face around origin, returns this. Requires precalculation.
        
        public Face3d rotateEqualsPrecalc(double sx, double cx, double sy, double cy, double sz, double cz) {
            
                for(int i = 0; i < polys.size(); i++) {
                    
                    Poly3d poly = polys.get(i);
                    
                    for(int j = 0; j < poly.points.length; j++) {
            
                        // rotation around x
                        double xy = cx*poly.points[j].y - sx*poly.points[j].z;
                        double xz = sx*poly.points[j].y + cx*poly.points[j].z;

                        // rotation around y
                        double yz = cy*xz - sy*poly.points[j].x;
                        double yx = sy*xz + cy*poly.points[j].x;

                        // rotation around z
                        double zx = cz*yx - sz*xy;
                        double zy = sz*yx + cz*xy;

                        // apply changes
                        poly.points[j].set(zx, zy, yz);
                    }
                    
                    //poly.updateVisibility();
                }
                
                for(int j = 0; j < corners.length; j++) {
            
                        // rotation around x
                        double xy = cx*corners[j].y - sx*corners[j].z;
                        double xz = sx*corners[j].y + cx*corners[j].z;

                        // rotation around y
                        double yz = cy*xz - sy*corners[j].x;
                        double yx = sy*xz + cy*corners[j].x;

                        // rotation around z
                        double zx = cz*yx - sz*xy;
                        double zy = sz*yx + cz*xy;

                        // apply changes
                        corners[j].set(zx, zy, yz);
                }
                
                updateVisibility();
                
                // return result
                return this;
        }
        
        // Rotate-Assign Around Point Method
        // *********************************
        // Purpose: Rotates face around point, returns this.
        
        public Face3d rotateEquals(Point3d rot, Point3d pt) {
            
                minusEquals(pt);  // shift coordinate plane so other is origin
                rotateEquals(rot);   // rotate with origin rotation
                plusEquals(pt);   // shift it back to normal
                return this;
        }

        // Is Empty Method
        // ***************
        // Purpose: Returns true if there are no polygons in the face.

        public boolean isEmpty() {

            if(polys.size() == 0) return true;

            return false;
        }
        
        // Draw Method
        // ***********
        // Purpose: Draws the face to the screen, if it is visible.
        
        public void draw(   Graphics g, int SCREEN_X, int SCREEN_Y,
                            double FOCAL_LENGTH, Color blendColor,
                            double blendPercent) {
                
                // don't draw if you can't see it
                if(!visible || polys.size() == 0) return;

                // calculate 2d points to determine if facing camera
                int [] xPoints = new int[4];
                int [] yPoints = new int[4];
                
                double scaleFactor;
                
                for(int i = 0; i < 4; i++) {
                    
                    if(corners[i].z <= 1) scaleFactor = FOCAL_LENGTH;
                    else scaleFactor = FOCAL_LENGTH / (corners[i].z);
                    
                    xPoints[i] = (SCREEN_X >> 1) + (int)(corners[i].x * scaleFactor);
                    yPoints[i] = (SCREEN_Y >> 1) - (int)(corners[i].y * scaleFactor);
                }
                
                // do not draw unless face is facing camera
                //if(xPoints[0] > xPoints[1] ^ yPoints[2] < yPoints[1] && xPoints[1] > xPoints[2] ^ yPoints[3] < yPoints[2] && xPoints[2] > xPoints[3] ^ yPoints[0] < yPoints[3] && xPoints[3] > xPoints[0] ^ yPoints[1] < yPoints[0]) return;
            
                // is facing, so draw
                for(int i = 0; i < polys.size(); i++) {
                    
                    polys.get(i).draw(g, SCREEN_X, SCREEN_Y, FOCAL_LENGTH, blendColor, blendPercent);
                }
        }
        
        public int compareTo(Object other) {
            
                Face3d otherFace = (Face3d)other;
                
                if(this.getDist() < otherFace.getDist()) return 1;
                else return -1;
        }
}
