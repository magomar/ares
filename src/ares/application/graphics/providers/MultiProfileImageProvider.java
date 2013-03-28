package ares.application.graphics.providers;

import ares.application.graphics.GraphicsProfile;
import ares.io.FileIO;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class MultiProfileImageProvider<K extends Enum<K> & GraphicsProfile, P extends GraphicsProvider<K>> implements GraphicsProvider<K> {

    private final Map<K, ImageProvider> provider;

    public MultiProfileImageProvider(P provider, ImageProviderType providerType, Class<K> profileClass, int rows, int columns) {
        this.provider = new EnumMap<>(profileClass);
        for (K profile : profileClass.getEnumConstants()) {
            ImageProvider sp = providerType.getImageProvider(provider.getFilename(), rows, columns, profile);
            this.provider.put(profile, sp);
        }
    }

    @Override
    public String getFilename() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BufferedImage getImage(K profile, int row, int column, FileIO fileSystem) {
        return provider.get(profile).getImage(row, column, fileSystem);
    }

    @Override
    public BufferedImage getImage(K profile, int index, FileIO fileSystem) {
        return provider.get(profile).getImage(index, fileSystem);
    }

    @Override
    public BufferedImage getImage(K profile, FileIO fileSystem) {
        return provider.get(profile).getImage(fileSystem);
    }

    @Override
    public BufferedImage getFullImage(K profile, FileIO fileSystem) {
       return provider.get(profile).getFullImage(fileSystem);
    }

    @Override
    public Dimension getFullImageDimension(K profile) {
        return provider.get(profile).getFullImageDimension();
    }

    @Override
    public String getFilename(K profile) {
        return provider.get(profile).getFilename();
    }
   
}
