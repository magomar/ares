package ares.application.shared.gui.laf;

import javax.swing.plaf.ColorUIResource;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public interface ColorPalette {
    static final ColorUIResource BLACK = new ColorUIResource(0x0);
    static final ColorUIResource WHITE = new ColorUIResource(0xffffff);

    // SAND AND DESERT COLORS ==================================================
    //    White Sand (Pantone TPX 13-0002) (Hex: #DCD7D4) (RGB: 220, 215, 212)
    //    DESERT SAND (Crayola) (US Army Camouflage color) (Hex: #EDC9AF) (RGB: 237, 201, 175)
    //    Sandy Brown (web color) (Hex: #F4A460) (RGB: 244, 164, 96)
    //    Earth Yellow (US Army Camouflage color) (Hex: #E1A95F) (RGB: 229, 169, 95)
    //    Sand (ISCC-NBS) (US Army Camouflage color) (Hex: #C2B280) (RGB: 194, 178, 128)
    //    Desert (ISCC-NBS) (Hex: #C19A6B) (RGB: 193, 154, 107)
    //    Sand Dune (Drab) (Mode Beige) (ISCC-NBS) (Hex: #967117) (RGB: 150, 113, 23)
    //    Field Drab (US Army Camouflage color) (Hex: #6C541e) (RGB: 108, 84, 30)
    static final ColorUIResource DESERT_SAND = new ColorUIResource(237, 201, 175);
    static final ColorUIResource EARTH_YELLOW = new ColorUIResource(229, 169, 95);
    static final ColorUIResource SAND = new ColorUIResource(194, 178, 128);
    static final ColorUIResource DESERT = new ColorUIResource(193, 154, 107);
    static final ColorUIResource SAND_DUNE = new ColorUIResource(150, 113, 23);
    static final ColorUIResource FIELD_DRAB = new ColorUIResource(108, 84, 30);
    // OLIVE COLORS =============================================================
    //    Light Earls Green (Earls Green Light (Xona.com color list)) (Hex: #E8E08E) (RGB: 232, 224, 142)
    //    Earls Green (Xona.com color list) (Hex: #C9B93B) (RGB: 201, 185, 59)
    //    Light Olivetone (Olivetone Light (Xona.com color list)) (Hex: #B8B654) (RGB: 184, 182, 84)
    //    Olivine (PerBang.dk) (Maerz and Paul) (Hex: #9AB973) (RGB: 154, 185, 115)
    //    Camouflage Green (PerBang.dk) (Hex: #78866B) (RGB: 120, 134, 107)
    //    Olive Drab (web color) (Hex: #6B8E23) (RGB: 107, 142, 35)
    //    OLIVE (web color) (Hex: #808000) (RGB: 128, 128, 0)
    //    Olivetone (Xona.com color list) (Hex: #716E10) (RGB: 113, 110, 16)
    //    Dark Olive Green (web color) (Hex: #556B2F) (RGB: 85, 107, 47)
    //    Black Olive (Olive RAL 6015) (Hex: #3B3C36) (RGB: 59, 60, 54)
    static final ColorUIResource LIGHT_OLIVETONE = new ColorUIResource(184,182,84);
    static final ColorUIResource CAMOUFLAGE_GREEN = new ColorUIResource(120,134,107);
    static final ColorUIResource OLIVE_DRAG = new ColorUIResource(107,142,35);
    static final ColorUIResource OLIVE = new ColorUIResource(128,128,0);
    static final ColorUIResource OLIVETONE = new ColorUIResource(113,110,16);
    static final ColorUIResource DARK_OLIVE_GREEN = new ColorUIResource(85,107,47);
    // TAN ==================================================================
    // BLUES =================================================================
    // GRAYS =================================================================
    static final ColorUIResource GRAY_224 = new ColorUIResource(224, 224, 224);
    static final ColorUIResource GRAY_192 = new ColorUIResource(192, 192, 192);
    static final ColorUIResource GRAY_160 = new ColorUIResource(160, 160, 160);
    static final ColorUIResource GRAY_128 = new ColorUIResource(128, 128, 128);
    static final ColorUIResource GRAY_96 = new ColorUIResource(96, 96, 96);
    static final ColorUIResource GRAY_64 = new ColorUIResource(64, 64, 64);
    static final ColorUIResource GRAY_32 = new ColorUIResource(32, 32, 32);
    // OTHER COLORS =========================================================
    static final ColorUIResource ARMY_YELLOW = new ColorUIResource(255, 203, 5);
    static final ColorUIResource ARMY_GREEN = new ColorUIResource(0x7B8738);

}
