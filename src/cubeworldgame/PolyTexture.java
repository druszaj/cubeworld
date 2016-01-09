package cubeworldgame;

import java.io.*;

// PolyTexture Class
// *****************
// Purpose: Stores the name and data of a PolyTexture.

public class PolyTexture {
    
        // Attributes
        // **********
    
        public String name;
        public String data;
        
        // Constructor
        // ***********
        
        PolyTexture(String name, String filepath) {
            
                this.name = name;

                String internal = InternalFiles.get(filepath);
                if(internal != null) {

                    data = new String(internal);
                    data = data.trim();
                }
                else {

                    System.out.println("Attempting to load texture from disk.");

                    try {

                        FileReader fr = new FileReader(new File(filepath));

                        char [] cbuf = new char[1 << 16]; // 2^16 max chars in file
                        fr.read(cbuf);
                        data = new String(cbuf);
                        data = data.trim();
                        fr.close();
                    }
                    catch(FileNotFoundException ex) {

                        System.out.println("FileNotFoundException : " + ex);
                    }

                    catch(IOException ioe) {

                        System.out.println("IOException : " + ioe);
                    }
                }
        }
}
