package ares.application.shared.gui.views;

import ares.application.shared.boundaries.viewers.TerrainInfoViewer;
import ares.application.shared.gui.ComponentFactory;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.providers.TerrainInfo;
import ares.application.shared.models.board.TileModel;
import ares.platform.engine.knowledge.KnowledgeCategory;
import ares.platform.scenario.board.Directions;
import ares.platform.scenario.board.Terrain;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class TerrainInfoView extends AbstractView<JPanel> implements TerrainInfoViewer {

    private TileModel tile;
    /**
     * Image to be rendered on this layer
     */
    private BufferedImage globalImage;

    @Override
    public void updateTile(TileModel tileModel) {
        this.tile = tileModel;
        initialize();
        Graphics2D g2 = globalImage.createGraphics();
        // Paint it black!
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, globalImage.getWidth(), globalImage.getHeight());
        paintTile(g2, tile);
        g2.dispose();
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
        BufferedImage terrainImage = GraphicsModel.INSTANCE.getNonProfiledImageProvider(TerrainInfo.OPEN).getFullImage();

        g2.drawImage(terrainImage, 0, 0, contentPane);

        Map<Terrain, Directions> m = tile.getTerrain();
        for (Map.Entry<Terrain, Directions> entry : m.entrySet()) {
            Terrain terrain = entry.getKey();
            TerrainInfo terrainInfo;
            try {
                terrainInfo = Enum.valueOf(TerrainInfo.class, terrain.name());
                terrainImage = GraphicsModel.INSTANCE.getNonProfiledImageProvider(terrainInfo).getFullImage();
                g2.drawImage(terrainImage, 0, 0, contentPane);
            } catch (java.lang.IllegalArgumentException e) {
            }

        }

        contentPane.repaint(0, 0, terrainImage.getWidth(), terrainImage.getHeight());
    }

    @Override
    protected JPanel layout() {
        JPanel content = new JPanel() {
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
        };
        content.setBorder(ComponentFactory.DEFAULT_BORDER);
        return content;
    }

    @Override
    public final void initialize() {
//        globalImage = new BufferedImage(GraphicProperties.getProperty(NonProfiledGraphicProperty.TERRAIN_INFO_WIDTH),
//                GraphicProperties.getProperty(NonProfiledGraphicProperty.TERRAIN_INFO_HEIGHT), BufferedImage.TYPE_INT_ARGB);
        Dimension imageSize = GraphicsModel.INSTANCE.getNonProfiledImageProvider(TerrainInfo.OPEN).getFullImageDimension();
        globalImage = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_ARGB);
        contentPane.repaint();
    }

    @Override
    public final void flush() {
        globalImage.flush();
        globalImage = null;
    }
}
