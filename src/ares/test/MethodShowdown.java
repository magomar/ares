package ares.test;

import ares.platform.engine.algorithms.pathfinding.heuristics.DistanceCalculator;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple class made for method testing purposes
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class MethodShowdown {

    public static final int NREP = 10;
    public static final int TALLA = 100;
    public static final double NANOSECOND = 1.0e-9;

    public void init(int nrep, int talla) {

        // Final message to be printed
        String message;

        // Time variables
        double startTime, averageTime, totalTime = 0, instanceTime, maxInstanceTime, minInstanceTime;

        // Methods to be tested
        LinkedList<Method> methods = new LinkedList<>();

        // Create custom collection        
        Collection<Point> args = fillCollection(talla);

        // Methods must be accsessibles
        try {
            Method m1 = DistanceCalculator.class.getMethod("euclidean", Point.class, Point.class);
            m1.setAccessible(true); // this doesn't seem to work
            methods.add(m1);
            methods.add(DistanceCalculator.class.getMethod("deltaDistance", Point.class, Point.class));
            methods.add(DistanceCalculator.class.getMethod("deltaBitwiseDistance", Point.class, Point.class));
            methods.add(DistanceCalculator.class.getMethod("heinian", Point.class, Point.class));
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(MethodShowdown.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Header
        System.out.print("NREP: " + Integer.toString(nrep));
        System.out.println(" - TALLA: " + Integer.toString(talla));

        // Total number of instances to calculate
        double size = args.size() * args.size(); // * nrep;

        // Large distance
        //Point close = new Point(0, 0); Point far = new Point(1000, 1000);

        //invoke method needs an object from the invoked class
//        DistanceCalculator dc = new DistanceCalculator();
        DistanceCalculator dc = DistanceCalculator.DELTA;
        for (Method test : methods) {
            // Pair of points with most expensive instace time
            Point[] maxPoints = {new Point(), new Point()};
            // Same with min instace time
            Point[] minPoints = {new Point(), new Point()};

            totalTime = 0;
            maxInstanceTime = 0;
            minInstanceTime = Long.MAX_VALUE;

            //for (int n = 0; n < nrep; n++) {
            // Nested loops without restrictions, same distance will be processed twice
            // + from p1 to p2 and p2 to p1
            for (Point p1 : args) {
                for (Point p2 : args) {
                    startTime = System.nanoTime();
                    try {

                        // Method call
                        test.invoke(dc, p1, p2);


                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        // If we got here, either our arguments classes are wrong or we forgot to use a valid object
                        Logger.getLogger(MethodShowdown.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    instanceTime = System.nanoTime() - startTime;
                    if (maxInstanceTime - instanceTime < 0) {
                        maxInstanceTime = instanceTime;
                        maxPoints[0] = p1;
                        maxPoints[1] = p2;
                    } else if (minInstanceTime - instanceTime > 0) {
                        minInstanceTime = instanceTime;
                        minPoints[0] = p1;
                        minPoints[1] = p2;
                    }
                    totalTime += instanceTime;
                }
            }
            //}
            averageTime = (totalTime / size);

            message = String.format(
                    "Method[%s]:\n\tTotal time:%2f\n\tAverage time:%2f\n\tInstance time[max,min]: [%f,%f]\n\tCosts[most,least]: {%s,%s}\t{%s,%s}",
                    test.getName(), totalTime, averageTime, maxInstanceTime, minInstanceTime, maxPoints[0], maxPoints[1], minPoints[0], minPoints[1]);

            System.out.println(message);
        }
    }

    /**
     * Fills the collection with params to be tested
     *
     * @param talla
     * @return
     */
    private static Collection<Point> fillCollection(int talla) {

        LinkedList<Point> ll = new LinkedList<>();
        for (int i = 0; i < talla; i++) {
            for (int j = 0; j < talla; j++) {
                ll.add(new Point(i, j));
            }
        }

        return ll;
    }

    /**
     * Converts nanoseconds into seconds
     *
     * @param nano
     * @return
     */
    private static double nanoToSecond(double nano) {
        return nano * NANOSECOND;
    }

    /**
     * This is showdown!
     *
     * @param args
     */
    public static void main(String[] args) {
        new MethodShowdown().init(NREP, TALLA);
    }
}
