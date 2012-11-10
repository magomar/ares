/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ares.model.board;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum Vision {
    RESTRICTED(new double[]{0.50, 0.75, 0.85, 0.85, 0.85}),
    NORMAL(new double[]{0.33, 0.50, 0.75, 0.85, 1.0}),
    OPEN(new double[]{0.11, 0.25, 0.56, 0.72, 1.0});
    private final double[] hitChance;
    
    private Vision(final double[] hitChance) {
        this.hitChance = hitChance;
    }

    public double[] getHitChance() {
        return hitChance;
    }
    
}
