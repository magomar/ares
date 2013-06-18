package ares.platform.engine.knowledge;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class KnowledgeLevel implements Comparable {

    public static final double MAX_KNOWLEDGE = 100.0;
    public static final double MIN_KNOWLEDGE = 0.0;
    public static final double RANGE = MAX_KNOWLEDGE - MIN_KNOWLEDGE;
    private double value;
    private KnowledgeCategory category;

    //    public KnowledgeLevel(double knowledge) {
//        this.value = knowledge;
//        this.category = KnowledgeCategory.getCategory(value);
//    }
    public KnowledgeLevel(KnowledgeCategory category) {
//        this.value = (category.getUpperBound() + category.getLowerBound()) / 2;
        this.value = category.getUpperBound();
        this.category = category;
    }

    /**
     * Modifies the knowledge level. Depending on the sign of the modifier it can either increase or decrease the level
     * of knowledge, both quantitatively (#knowledge) and qualitatively (#type)
     *
     * @param modifier
     */
    public void modify(double modifier) {
        value += modifier;
        value = Math.min(MAX_KNOWLEDGE, Math.max(MIN_KNOWLEDGE, value));
        while (value > category.getUpperBound()) {
            category = category.getNext();
        }
        while (value < category.getLowerBound()) {
            category = category.getPrev();
        }
    }

    public double getValue() {
        return value;
    }

    public KnowledgeCategory getCategory() {
        return category;
    }

    @Override
    public int compareTo(Object knowledgeLevel) {
        KnowledgeLevel kLevel = (KnowledgeLevel) knowledgeLevel;
        if (this.value == kLevel.value) {
            return 0;
        } else if ((this.value) > kLevel.value) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.value) ^ (Double.doubleToLongBits(this.value) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KnowledgeLevel other = (KnowledgeLevel) obj;
        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return String.format("K = %.2f (%s)", value, category);
        return String.format("(%.2f)", value);
    }
}
