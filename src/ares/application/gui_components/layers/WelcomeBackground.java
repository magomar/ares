package ares.application.gui_components.layers;

import ares.application.models.ScenarioModel;
import ares.application.models.board.TileModel;
import ares.io.AresPaths;
import ares.platform.util.RandomGenerator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.ref.SoftReference;
import java.util.*;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class WelcomeBackground extends AbstractImageLayer {
    
    private SoftReference<BufferedImage> backgroundImage = new SoftReference<>(null);
    
    private final File wallpapers;
    
    public WelcomeBackground(){
         wallpapers =  new File(AresPaths.GRAPHICS.getPath(),"Background");
         setWelcomeBackground();
    }
    
    public void setWelcomeBackground() {
        if (backgroundImage.get() == null) {
            backgroundImage = new SoftReference<>(loadImage(randomImageFile()));
        }
        globalImage = backgroundImage.get();
    }


    private File randomImageFile(){
        File[] backgrounds = wallpapers.listFiles();
        if(backgrounds==null) return null;
        Random r = new Random();
        Integer index = r.nextInt(); index&=0x7FFFFFFF;index%=backgrounds.length;
        System.out.println("loading: "+index);
        return backgrounds[index];
    }    
    
    
        
    @Override
    protected void createGlobalImage(ScenarioModel s) {
    }
    @Override
    public void paintTile(TileModel t) {
    }
}
