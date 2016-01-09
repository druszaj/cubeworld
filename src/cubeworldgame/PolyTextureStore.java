package cubeworldgame;

import java.util.ArrayList;

// PolyTextureStore Class
// **********************
// Purpose: Stores polytextures in ram rather than on the hard drive.

public class PolyTextureStore {
    
        // Attributes
        // **********
    
        private ArrayList<PolyTexture> polyTextures = new ArrayList<PolyTexture>();

        // creates single instance of this class
    
	private static PolyTextureStore single = new PolyTextureStore();
        
        // Get Instance Method
        // *******************
        // Purpose: Returns the single instance of this class.
        
	public static PolyTextureStore getInstance() {
            
		return single;
	}
        
        // Load Method
        // ***********
        // Purpose: Load a PolyTexture from hard drive into memory.
        
        public void load(String name) {
            
                String filepath = "PolyTextures/" + name + ".svg";
            
                for(int i = 0; i < polyTextures.size(); i++) {
                    
                    if(polyTextures.get(i).name.equals(name)) {
                        
                        return;
                    }
                }
                
                polyTextures.add(new PolyTexture(name, filepath));
        }
        
        // Get Method
        // **********
        // Purpose: Get a PolyTexture from memory.
        
        public PolyTexture get(String name) {
            
                for(int i = 0; i < polyTextures.size(); i++) {
                    
                    if(polyTextures.get(i).name.equals(name)) {
                        
                        return polyTextures.get(i);
                    }
                }
                
                System.out.println("Error: Tried to getPolyTexture that wasn't in PolyTextureStore.");
                
                return null;
        }

        // LoadGet Method
        // **************
        // Purpose: Load, then get from memory, a PolyTexture.

        public PolyTexture loadGet(String name) {

                load(name);
                return get(name);
        }
}
