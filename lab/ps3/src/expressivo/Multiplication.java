package expressivo;

/**
 * An immutable class representing a multiplication operation in an expression.
 */
public class Multiplication implements Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Create a new Multiplication expression.
     *
     * @param left the left expression
     * @param right the right expression
     */
    public Multiplication(Expression left, Expression right) {
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
        return "(" + left.toString() + " * " + right.toString() + ")";
    }

    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Multiplication)) {
            return false;
        }

        Multiplication thatMultiplication = (Multiplication) thatObject;
        return this.left.equals(thatMultiplication.left) && this.right.equals(thatMultiplication.right);
    }

    @Override
    public int hashCode() {
        return left.hashCode() * right.hashCode(); // simple multiplication to combine hashes, can be enhanced
    }
}
