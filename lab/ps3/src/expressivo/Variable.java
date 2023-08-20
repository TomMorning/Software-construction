package expressivo;

/**
 * An immutable class representing a variable in an expression.
 */
public class Variable extends PrimitiveExpression {

    private final String name;

    /**
     * Create a new Variable expression.
     *
     * @param name the name of the variable
     */
    public Variable(String name) {
        if (name == null || name.isEmpty() || !name.chars().allMatch(Character::isLetter)) {
            throw new IllegalArgumentException("Invalid variable name");
        }
        this.name = name;
    }

    /**
     * Get the name of this variable.
     *
     * @return variable name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Variable)) {
            return false;
        }

        Variable thatVariable = (Variable) thatObject;
        return this.name.equals(thatVariable.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
