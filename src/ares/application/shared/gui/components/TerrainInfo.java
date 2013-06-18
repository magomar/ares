package ares.application.shared.gui.components;

import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.providers.TerrainInfoGraphics;
import ares.application.shared.models.board.TileModel;
import ares.platform.engine.knowledge.KnowledgeCategory;
import ares.platform.scenario.board.Directions;
import ares.platform.scenario.board.Terrain;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class TerrainInfo extends JComponent {
    private TileModel tile;
    /**
     * Image to be rendered on this component
     */
    private BufferedImage globalImage;


    public final void initialize() {
//        globalImage = new BufferedImage(GraphicProperties.getProperty(NonProfiledGraphicProperty.TERRAIN_INFO_WIDTH),
//                GraphicProperties.getProperty(NonProfiledGraphicProperty.TERRAIN_INFO_HEIGHT), BufferedImage.TYPE_INT_ARGB);
        Dimension imageSize = GraphicsModel.INSTANCE.getNonProfiledImageProvider(TerrainInfoGraphics.OPEN).getFullImageDimension();
        globalImage = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_ARGB);
        repaint();
    }

    public void updateTile(TileModel tileModel) {
        this.tile = tileModel;
        initialize();
        Graphics2D g2 = globalImage.createGraphics();
        if (tile != null) {
            paintTile(g2, tile);
        } else {
            paintFrame(g2);
        }
        g2.dispose();
    }

    private void paintFrame(Graphics2D g2) {
        BufferedImage image = GraphicsModel.INSTANCE.getNonProfiledImageProvider(TerrainInfoGraphics.UNKNOWN).getFullImage();
        g2.drawImage(image, 0, 0, this);
        image = GraphicsModel.INSTANCE.getNonProfiledImageProvider(TerrainInfoGraphics.FRAME).getFullImage();
        g2.drawImage(image, 0, 0, this);
        repaint(0, 0, image.getWidth(), image.getHeight());
    }

    /**
     * Paints the terrain of a single tile
     *
     * @param tile
     */
    private void paintTile(Graphics2D g2, TileModel tile) {
        if (!tile.isPlayable()) {
            return;
        }

        //If I don't know anything about it
        if (tile.getKnowledgeCategory() == KnowledgeCategory.NONE) {
            return;
        }

        // First paints the open terrain, any other terrain will be rendered upon it
        BufferedImage terrainImage = GraphicsModel.INSTANCE.getNonProfiledImageProvider(TerrainInfoGraphics.OPEN).getFullImage();

        g2.drawImage(terrainImage, 0, 0, this);

        Map<Terrain, Directions> m = tile.getTerrain();
        for (Map.Entry<Terrain, Directions> entry : m.entrySet()) {
            Terrain terrain = entry.getKey();
            TerrainInfoGraphics terrainInfoGraphics;
            try {
                terrainInfoGraphics = Enum.valueOf(TerrainInfoGraphics.class, terrain.name());
                terrainImage = GraphicsModel.INSTANCE.getNonProfiledImageProvider(terrainInfoGraphics).getFullImage();
                g2.drawImage(terrainImage, 0, 0, this);
            } catch (java.lang.IllegalArgumentException e) {
            }
        }
        BufferedImage image = GraphicsModel.INSTANCE.getNonProfiledImageProvider(TerrainInfoGraphics.FRAME).getFullImage();
        g2.drawImage(image, 0, 0, this);
        repaint(0, 0, terrainImage.getWidth(), terrainImage.getHeight());
    }

    public final void flush() {
        globalImage.flush();
        globalImage = null;
    }

    /**
     * Paints the globalImage if it's not null
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (globalImage != null) {
            g2.drawImage(globalImage, 0, 0, this);
        }
    }
}
