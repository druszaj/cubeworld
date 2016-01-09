package cubeworldgame;

import java.awt.Graphics;

// Point3d Class
// *************
// Purpose: Stores 3 numbers, can be used as a 3d point or vector.

public class Point3d {
    
        // Attributes
        // **********
    
        public double x, y, z;
        
        // Default Constructor
        // *******************
        // Purpose: Creates a zero point.
        
        public Point3d() {
            
                this.x = 0;
                this.y = 0;
                this.z = 0;
        }
        
        // Loaded Constructor
        // ******************
        // Purpose: Creates a point using 3 double inputs.

        public Point3d(double x, double y, double z) {
            
                this.x = x;
                this.y = y;
                this.z = z;
        }
        
        // Copy Constructor
        // ****************
        // Purpose: Creates a point using another point.

        public Point3d(Point3d other) {
            
                this.x = other.x;
                this.y = other.y;
                this.z = other.z;
        }
        
        // Set Method
        // **********
        // Purpose: Sets to 3 input doubles.
        
        public void set(double x, double y, double z) {
            
                this.x = x;
                this.y = y;
                this.z = z;
        }

        // Distance Method
        // ***************
        // Purpose: Return the distance between this point and another.

        public double distance(Point3d other) {

            return Math.sqrt(Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2)
                    + Math.pow(other.z - z, 2));
        }
        
        // Negate Method
        // ********************
        // Purpose: Returns negated point.
        
        public Point3d negative() {
            
                Point3d result = new Point3d(this);
                result.x = -result.x;
                result.y = -result.y;
                result.z = -result.z;
                return result;
        }
        
        // Negate-Assign Method
        // ********************
        // Purpose: Negates and assigns to point, then returns it.
        
        public Point3d negativeEquals() {
            
                x = -x;
                y = -y;
                z = -z;
                return this;
        }
        
        // Add Point3d Method
        // ******************
        // Purpose: Returns the point sum.
        
        public Point3d plus(Point3d other) {
            
                Point3d result = new Point3d(this);
                result.x += other.x;
                result.y += other.y;
                result.z += other.z;
                return result;
        }
        
        // Add-Assign Point3d Method
        // *************************
        // Purpose: Adds and assigns to point, then returns it.
        
        public Point3d plusEquals(Point3d other) {
            
                x += other.x;
                y += other.y;
                z += other.z;
                return this;
        }
        
        // Subtract Point3d Method
        // ***********************
        // Purpose: Returns the point difference.
        
        public Point3d minus(Point3d other) {
            
                Point3d result = new Point3d(this);
                result.x -= other.x;
                result.y -= other.y;
                result.z -= other.z;
                return result;
        }
        
        // Subtract-Assign Point3d Method
        // ******************************
        // Purpose: Subtracts from and assigns to point, then returns it.
        
        public Point3d minusEquals(Point3d other) {
            
                x -= other.x;
                y -= other.y;
                z -= other.z;
                return this;
        }
        
        // Multiply Single Method
        // **********************
        // Purpose: Returns the point times a single.
        
        public Point3d times(double n) {
            
                Point3d result = new Point3d(this);
                result.x *= n;
                result.y *= n;
                result.z *= n;
                return result;
        }
        
        // Multiply-Assign Single Method
        // *****************************
        // Purpose: Multiplies and assigns single to point, then returns it.
        
        public Point3d timesEquals(double n) {
            
                x *= n;
                y *= n;
                z *= n;
                return this;
        }
        
        // Multiply Point3d Method
        // ***********************
        // Purpose: Returns the point product.
        
        public Point3d times(Point3d other) {
            
                Point3d result = new Point3d(this);
                result.x *= other.x;
                result.y *= other.y;
                result.z *= other.z;
                return result;
        }
        
        // Multiply-Assign Point3d Method
        // ******************************
        // Purpose: Multiplies and assigns point to point, then returns it.
        
        public Point3d timesEquals(Point3d other) {
            
                x *= other.x;
                y *= other.y;
                z *= other.z;
                return this;
        }
        
        // Divide Single Method
        // *********************
        // Purpose: Returns the point divided by a single.
        
        public Point3d divideBy(double n) {
            
                Point3d result = new Point3d(this);
                result.x /= n;
                result.y /= n;
                result.z /= n;
                return result;
        }
        
        // Divide-Assign Single Method
        // ***************************
        // Purpose: Divides with and assigns single to point, then returns it.
        
        public Point3d divideByEquals(double n) {
            
                x /= n;
                y /= n;
                z /= n;
                return this;
        }
        
        // Divide Point3d Method
        // *********************
        // Purpose: Returns the point quotient.
        
        public Point3d divideBy(Point3d other) {
            
                Point3d result = new Point3d(this);
                result.x /= other.x;
                result.y /= other.y;
                result.z /= other.z;
                return result;
        }
        
        // Divide-Assign Point3d Method
        // ****************************
        // Purpose: Divided with and assigns point to point, then returns it.
        
        public Point3d divideByEquals(Point3d other) {
            
                x /= other.x;
                y /= other.y;
                z /= other.z;
                return this;
        }
        
        // Cross Product Point3d Method
        // ****************************
        // Purpose: Returns the cross product (the vector perpendicular to the
        //          two vectors).
        
        public Point3d crossProduct(Point3d other) {
            
                Point3d result = new Point3d();
                result.x = y*other.z - z*other.y;
                result.y = z*other.x - x*other.z;
                result.z = x*other.y - y*other.x;
                return result;
        }
        
        // Rotate Around Origin Method
        // ***************************
        // Purpose: Returns the point rotated around the origin.
        
        public Point3d rotate(Point3d rot) {
            
                // pre-calculate sines and cosines
                double sx = Math.sin(rot.x);
                double cx = Math.cos(rot.x);
                double sy = Math.sin(rot.y);
                double cy = Math.cos(rot.y);
                double sz = Math.sin(rot.z);
                double cz = Math.cos(rot.z);
            
                // rotation around x
		double xy = cx*y - sx*z;
		double xz = sx*y + cx*z;
                
		// rotation around y
                double yz = cy*xz - sy*x;
		double yx = sy*xz + cy*x;
                
		// rotation around z
		double zx = cz*yx - sz*xy;
		double zy = sz*yx + cz*xy;
                
                // return result
                return new Point3d(zx, zy, yz);
        }
        
        // Rotate-Assign Around Origin Method
        // **********************************
        // Purpose: Rotates point around origin, returns this.
        
        public Point3d rotateEquals(Point3d rot) {
            
                // pre-calculate sines and cosines
                double sx = Math.sin(rot.x);
                double cx = Math.cos(rot.x);
                double sy = Math.sin(rot.y);
                double cy = Math.cos(rot.y);
                double sz = Math.sin(rot.z);
                double cz = Math.cos(rot.z);
            
                // rotation around x
		double xy = cx*y - sx*z;
		double xz = sx*y + cx*z;
                
		// rotation around y
                double yz = cy*xz - sy*x;
		double yx = sy*xz + cy*x;
                
		// rotation around z
		double zx = cz*yx - sz*xy;
		double zy = sz*yx + cz*xy;
                
                // apply changes
                x = zx;
                y = zy;
                z = yz;
                
                // return result
                return this;
        }
        
        // Rotate Around Point3d Method
        // ****************************
        // Purpose: Returns the point rotated around another point.
        
        public Point3d rotate(Point3d rot, Point3d other) {
            
                Point3d result = new Point3d(this);
                result.minusEquals(other);  // shift coordinate plane so other is origin
                result.rotateEquals(rot);   // rotate with origin rotation
                result.plusEquals(other);   // shift it back to normal
                return result;
        }
        
        // Rotate-Assign Around Point3d Method
        // ***********************************
        // Purpose: Rotates point around other point, returns this.
        
        public Point3d rotateEquals(Point3d rot, Point3d other) {
            
                minusEquals(other);  // shift coordinate plane so other is origin
                rotateEquals(rot);   // rotate with origin rotation
                plusEquals(other);   // shift it back to normal
                return this;
        }
        
        // Draw Method
        // ***********
        // Purpose: Draws a pixel representing the point to the screen, using
        //          whichever color the graphics context currently has selected.
        
        public void draw(Graphics g, int SCREEN_X, int SCREEN_Y, double FOCAL_LENGTH) {
            
                if(z <= 0) return;
            
                int dx, dy;
                double scaleFactor;
                
                if(z <= 1) scaleFactor = FOCAL_LENGTH;
                else scaleFactor = FOCAL_LENGTH / z;
                    
                dx = (SCREEN_X >> 1) + (int)(x * scaleFactor);
                dy = (SCREEN_Y >> 1) - (int)(y * scaleFactor);
            
                g.drawRect(dx, dy, 0, 0);
        }
        
        // To String Method
        // ****************
        // Purpose: Returns this point's string representation.
        
        @Override
        public String toString() {
            
                return "<" + x + ", " + y + ", " + z + ">";
        }
}
