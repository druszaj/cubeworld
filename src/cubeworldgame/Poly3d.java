package cubeworldgame;

import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;

// Poly3d Class
// ************
// Purpose: Stores 3 or more Point3d's, a color, and a visibility flag,
//          representing a polygon in 3d space.

public class Poly3d {

        // Attributes
        // **********
    
        public Point3d [] points; // the points that make up the polygon
        public Color color; // the color of the polygon
        
        // pre-allocated arrays for drawing: increase memory usage but save cpu
        int [] xPoints;
        int [] yPoints;
        
        // Default Constructor
        // *******************
        // Purpose: Creates a white zero triangle.

        public Poly3d() {
            
                points = new Point3d[3];
                
                for(int i = 0; i < points.length; i++) {
                    
                    points[i] = new Point3d();
                }

                xPoints = new int[points.length];
                yPoints = new int[points.length];
                
                color = Color.WHITE;
        }
        
        // Allocated Constructor
        // *********************
        // Purpose: Creates a white zero polygon with number of points n.

        public Poly3d(int n) {
            
                points = new Point3d[n];
                
                for(int i = 0; i < points.length; i++) {
                    
                    points[i] = new Point3d();
                }

                xPoints = new int[points.length];
                yPoints = new int[points.length];
                
                color = Color.WHITE;
        }
        
        // Loaded Constructor
        // ******************
        // Purpose: Creates a polygon using an array of Point3d's and a color.

        public Poly3d(Point3d [] pts, Color clr) {
            
                points = new Point3d[pts.length];
                
                for(int i = 0; i < points.length; i++) {
                    
                    points[i] = new Point3d(pts[i]);
                }

                xPoints = new int[points.length];
                yPoints = new int[points.length];
                
                color = clr;
        }
        
        // Loaded Constructor 2
        // ********************
        // Purpose: Creates a polygon using an ArrayList of Point3d's and a color.

        public Poly3d(ArrayList<Point3d> pts, Color clr) {
            
                points = new Point3d[pts.size()];
                
                for(int i = 0; i < points.length; i++) {
                    
                    points[i] = new Point3d(pts.get(i));
                }

                xPoints = new int[points.length];
                yPoints = new int[points.length];
                
                color = clr;
        }
        
        // Copy Constructor
        // ****************
        // Purpose: Creates a polygon by deep copying another polygon.

        public Poly3d(Poly3d other) {
            
                points = new Point3d[other.points.length];
                
                for(int i = 0; i < points.length; i++) {
                    
                    points[i] = new Point3d(other.points[i]);
                }

                xPoints = new int[points.length];
                yPoints = new int[points.length];
                
                color = other.color;
        }
        
        // Get Center Method
        // *****************
        // Purpose: Returns the center (the average of all points).
        
        public Point3d getCenter() {
            
                Point3d center = new Point3d();
            
                for(int i = 0; i < points.length; i++) {
                    
                    center.plusEquals(points[i]);
                }
                
                center.divideByEquals(points.length);
                
                return center;
        }
        
        // Negate Method
        // ********************
        // Purpose: Returns negated polygon.
        
        public Poly3d negative() {
            
                Poly3d result = new Poly3d(this);
                
                for(int i = 0; i < points.length; i++) {
                    
                    result.points[i].negativeEquals();
                }
                
                return result;
        }
        
        // Negate-Assign Method
        // ********************
        // Purpose: Negates and assigns to polygon, then returns it.
        
        public Poly3d negativeEquals() {
            
                for(int i = 0; i < points.length; i++) {
                    
                    points[i].negativeEquals();
                }
                
                return this;
        }
        
        // Add Point3d Method
        // ******************
        // Purpose: Returns the polygon plus a point.
        
        public Poly3d plus(Point3d other) {
            
                Poly3d result = new Poly3d(this);
                
                for(int i = 0; i < points.length; i++) {
                    
                    result.points[i].plusEquals(other);
                }
                
                return result;
        }
        
        // Add-Assign Point3d Method
        // *************************
        // Purpose: Adds and assigns to polygon, then returns it.
        
        public Poly3d plusEquals(Point3d other) {
            
                for(int i = 0; i < points.length; i++) {
                    
                    points[i].plusEquals(other);
                }
                
                return this;
        }
        
        // Subtract Point3d Method
        // ***********************
        // Purpose: Returns the polygon minus a point.
        
        public Poly3d minus(Point3d other) {
            
                Poly3d result = new Poly3d(this);
                
                for(int i = 0; i < points.length; i++) {
                    
                    result.points[i].minusEquals(other);
                }
                
                return result;
        }
        
        // Subtract-Assign Point3d Method
        // ******************************
        // Purpose: Subtracts from and assigns to polygon, then returns it.
        
        public Poly3d minusEquals(Point3d other) {
            
                for(int i = 0; i < points.length; i++) {
                    
                    points[i].minusEquals(other);
                }
                
                return this;
        }
        
        // Multiply Single Method
        // **********************
        // Purpose: Returns the polygon times a single.
        
        public Poly3d times(double n) {
            
                Poly3d result = new Poly3d(this);
                
                for(int i = 0; i < points.length; i++) {
                    
                    result.points[i].timesEquals(n);
                }
                
                return result;
        }
        
        // Multiply-Assign Single Method
        // *****************************
        // Purpose: Multiplies and assigns single to polygon, then returns it.
        
        public Poly3d timesEquals(double n) {
            
                for(int i = 0; i < points.length; i++) {
                    
                    points[i].timesEquals(n);
                }
                
                return this;
        }
        
        // Multiply Point3d Method
        // ***********************
        // Purpose: Returns the polygon times a point.
        
        public Poly3d times(Point3d other) {
            
                Poly3d result = new Poly3d(this);
                
                for(int i = 0; i < points.length; i++) {
                    
                    result.points[i].timesEquals(other);
                }
                
                return result;
        }
        
        // Multiply-Assign Point3d Method
        // ******************************
        // Purpose: Multiplies and assigns to polygon, then returns it.
        
        public Poly3d timesEquals(Point3d other) {
            
                for(int i = 0; i < points.length; i++) {
                    
                    points[i].timesEquals(other);
                }
                
                return this;
        }
        
        // Divide Single Method
        // *********************
        // Purpose: Returns the polygon divided by a single.
        
        public Poly3d divideBy(double n) {
            
                Poly3d result = new Poly3d(this);
                
                for(int i = 0; i < points.length; i++) {
                    
                    result.points[i].divideByEquals(n);
                }
                
                return result;
        }
        
        // Divide-Assign Single Method
        // ***************************
        // Purpose: Divides with and assigns single to polygon, then returns it.
        
        public Poly3d divideByEquals(double n) {
            
                for(int i = 0; i < points.length; i++) {
                    
                    points[i].divideByEquals(n);
                }
                
                return this;
        }
        
        // Divide Point3d Method
        // *********************
        // Purpose: Returns the polygon quotient.
        
        public Poly3d divideBy(Point3d other) {
            
                Poly3d result = new Poly3d(this);
                
                for(int i = 0; i < points.length; i++) {
                    
                    result.points[i].divideByEquals(other);
                }
                
                return result;
        }
        
        // Divide-Assign Point3d Method
        // ****************************
        // Purpose: Divides and assigns to polygon, then returns it.
        
        public Poly3d divideByEquals(Point3d other) {
            
                for(int i = 0; i < points.length; i++) {
                    
                    points[i].divideByEquals(other);
                }
                
                return this;
        }
        
        // Rotate Around Origin Method
        // ***************************
        // Purpose: Returns the polygon rotated around the origin.
        
        public Poly3d rotate(Point3d rot) {
            
                Poly3d result = new Poly3d(points.length);
            
                // pre-calculate sines and cosines
                double sx = Math.sin(rot.x);
                double cx = Math.cos(rot.x);
                double sy = Math.sin(rot.y);
                double cy = Math.cos(rot.y);
                double sz = Math.sin(rot.z);
                double cz = Math.cos(rot.z);
                
                for(int i = 0; i < points.length; i++) {
            
                    // rotation around x
                    double xy = cx*points[i].y - sx*points[i].z;
                    double xz = sx*points[i].y + cx*points[i].z;

                    // rotation around y
                    double yz = cy*xz - sy*points[i].x;
                    double yx = sy*xz + cy*points[i].x;

                    // rotation around z
                    double zx = cz*yx - sz*xy;
                    double zy = sz*yx + cz*xy;

                    // assign to result
                    result.points[i] = new Point3d(zx, zy, yz);
                }
                
                result.color = color;
                
                return result;
        }
        
        // Rotate-Assign Around Origin Method
        // **********************************
        // Purpose: Rotates polygon around origin, returns this.
        
        public Poly3d rotateEquals(Point3d rot) {
            
                // pre-calculate sines and cosines
                double sx = Math.sin(rot.x);
                double cx = Math.cos(rot.x);
                double sy = Math.sin(rot.y);
                double cy = Math.cos(rot.y);
                double sz = Math.sin(rot.z);
                double cz = Math.cos(rot.z);
                
                for(int i = 0; i < points.length; i++) {
            
                    // rotation around x
                    double xy = cx*points[i].y - sx*points[i].z;
                    double xz = sx*points[i].y + cx*points[i].z;

                    // rotation around y
                    double yz = cy*xz - sy*points[i].x;
                    double yx = sy*xz + cy*points[i].x;

                    // rotation around z
                    double zx = cz*yx - sz*xy;
                    double zy = sz*yx + cz*xy;

                    // apply changes
                    points[i] = new Point3d(zx, zy, yz);
                }

                // return result
                return this;
        }
        
        // Rotate Around Point3d Method
        // ****************************
        // Purpose: Returns the polygon rotated around a point.
        
        public Poly3d rotate(Point3d rot, Point3d pt) {
            
                Poly3d result = new Poly3d(this);
                result.minusEquals(pt);  // shift coordinate plane so other is origin
                result.rotateEquals(rot);   // rotate with origin rotation
                result.plusEquals(pt);   // shift it back to normal

                return result;
        }
        
        // Rotate-Assign Around Point3d Method
        // ***********************************
        // Purpose: Rotates point around other point, returns this.
        
        public Poly3d rotateEquals(Point3d rot, Point3d pt) {
            
                minusEquals(pt);  // shift coordinate plane so other is origin
                rotateEquals(rot);   // rotate with origin rotation
                plusEquals(pt);   // shift it back to normal

                return this;
        }
        
        // Draw Method
        // ***********
        // Purpose: Draws the polygon to the screen, if it is visible.
        
        public void draw(   Graphics g, int SCREEN_X, int SCREEN_Y,
                            double FOCAL_LENGTH, Color blendColor,
                            double blendPercent) {
                
                double scaleFactor;
                
                for(int i = 0; i < points.length; i++) {
                    
                    if(points[i].z <= 1) scaleFactor = FOCAL_LENGTH;
                    else scaleFactor = FOCAL_LENGTH / (points[i].z);
                    
                    xPoints[i] = (SCREEN_X >> 1) + (int)(points[i].x * scaleFactor);
                    yPoints[i] = (SCREEN_Y >> 1) - (int)(points[i].y * scaleFactor);
                }

                if(blendPercent <= 0.01) {
                    
                    g.setColor(color);
                }
                else {

                    int red = (int)(color.getRed() * (1 - blendPercent) +
                                    blendColor.getRed() * blendPercent);
                    int green = (int)(color.getGreen() * (1 - blendPercent) +
                                    blendColor.getGreen() * blendPercent);
                    int blue = (int)(color.getBlue() * (1 - blendPercent) +
                                    blendColor.getBlue() * blendPercent);

                    g.setColor(new Color(red, green, blue));
                }
                
                g.fillPolygon(xPoints, yPoints, xPoints.length);
        }
}
