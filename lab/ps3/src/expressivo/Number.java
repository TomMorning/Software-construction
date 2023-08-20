package expressivo;

class Number extends PrimitiveExpression {
    private final double value;

    /**
     * Create a new Number expression.
     *
     * @param value numeric value of the expression
     */
    public Number(double value) {
        this.value = value;
    }

    /**
     * Get the numeric value of this expression.
     *
     * @return numeric value
     */
    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Number)) {
            return false;
        }

        Number thatNumber = (Number) thatObject;
        // 使用一个小的范围来比较两个double，以便处理浮点不准确性。
        return Math.abs(this.value - thatNumber.value) < 1e-9;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}