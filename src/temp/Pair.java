package temp;

import java.util.Objects;

/**
 * Pair class.
 * <p/>
 * Someone explain me why Java doesn't have this basic class in the STL.
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class Pair<L, R> {

    private L left;
    private R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.left);
        hash = 37 * hash + Objects.hashCode(this.right);
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
        final Pair<?, ?> other = (Pair<?, ?>) obj; // wildcard avoids unchecked cast
        if (!other.left.equals(this.left) || !right.equals(this.right)) {
            return false;
        }
        return true;
    }
}
