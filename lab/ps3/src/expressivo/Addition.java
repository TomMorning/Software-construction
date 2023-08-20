package expressivo;

/**
 * An immutable class representing an addition operation in an expression.
 */
public class Addition implements Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Create a new Addition expression.
     *
     * @param left the left expression
     * @param right the right expression
     */
    public Addition(Expression left, Expression right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Expressions cannot be null");
        }
        this.left = left;
        this.right = right;
    }

    /**
     * Get the left expression.
     *
     * @return left expression
     */
    public Expression getLeft() {
        return left;
    }

    /**
     * Get the right expression.
     *
     * @return right expression
     */
    public Expression getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " + " + right.toString() + ")";
    }

    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Addition)) {
            return false;
        }

        Addition thatAddition = (Addition) thatObject;
        return this.left.equals(thatAddition.left) && this.right.equals(thatAddition.right);
    }

    @Override
    public int hashCode() {
        return left.hashCode() + right.hashCode(); // simple addition to combine hashes, can be enhanced
    }
}
