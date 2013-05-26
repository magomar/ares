/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ares.platform.util;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
import java.util.NavigableMap;
import java.util.TreeMap;

public class ProbabilisticChooser<E> {

    private final NavigableMap<Double, E> optionMap = new TreeMap<>();
    private double totalProbability;

    public ProbabilisticChooser<E> addOption(E choice, double probability) {
        optionMap.put(totalProbability, choice);
        totalProbability += probability;
        return this;
    }

    public E choose() {
        return optionMap.floorEntry(Math.random() * totalProbability).getValue();
    }
}
