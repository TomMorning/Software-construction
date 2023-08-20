package expressivo;

/**
 * A base class for polynomial expressions.
 */
public abstract class PrimitiveExpression implements Expression {


    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object thatObject);

    @Override
    public abstract int hashCode();

}
