package snl2fl.req.expressions;

import snl2fl.req.visitor.ExpressionVisitor;

/**
 * The Class NumberExpression.
 *
 * @author Simone Vuotto
 * creation date  =  02/09/15.
 */
public class NumberExpression extends Expression{
    
    /** The value. */
    final float value;

    /**
     * Instantiates a new number expression.
     *
     * @param value the value
     */
    public NumberExpression(float value){
        this.value = value;
    }

    /* (non-Javadoc)
     * @see snl2fl.req.expressions.Expression#value()
     */
    @Override
    boolean value() {
        return false;
        //throw new Exception("Not a boolean expression");
    }

    /* (non-Javadoc)
     * @see snl2fl.req.expressions.Expression#accept(snl2fl.req.visitor.ExpressionVisitor)
     */
    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visitNumberExpression(this);
    }

    /**
     * Float value.
     *
     * @return the float
     */
    public float floatValue(){ return  value; }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
