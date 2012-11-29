package ares.engine.knowledge;

/**
 * Level of information gathered by one force from a given location or unit
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum KnowledgeCategory {

    NONE(0, 19),
    POOR(20, 49),
    GOOD(50, 79),
    COMPLETE(80, 100);
    private KnowledgeCategory next;
    private KnowledgeCategory prev;
    private final double lowerBound;
    private final double upperBound;

    static {
        NONE.next = POOR;
        POOR.next = GOOD;
        GOOD.next = COMPLETE;
        COMPLETE.next = COMPLETE;
        COMPLETE.prev = GOOD;
        GOOD.prev = POOR;
        POOR.prev = NONE;
        NONE.prev = NONE;
    }

    private KnowledgeCategory(double lowerBound, double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public KnowledgeCategory getNext() {
        return next;
    }

    public KnowledgeCategory getPrev() {
        return prev;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

//    public static KnowledgeCategory getCategory(double value) {
//        int numCategories = values().length;
//        double categorySize = KnowledgeLevel.RANGE / numCategories;
//        int catIndex = (int) ((value+1) / categorySize) - 1;
//        return values()[catIndex];
//    }
    
//    public static KnowledgeCategory max(KnowledgeCategory kl1, KnowledgeCategory kl2) {
//        return (kl1.compareTo(kl2) > 0 ? kl1 : kl2);
//    }
//
//    public static KnowledgeCategory min(KnowledgeCategory kl1, KnowledgeCategory kl2) {
//        return (kl1.compareTo(kl2) > 0 ? kl2 : kl1);
//    }
}
