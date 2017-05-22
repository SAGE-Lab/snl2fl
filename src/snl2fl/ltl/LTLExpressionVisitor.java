package snl2fl.ltl;

import snl2fl.fl.elements.Atom;
import snl2fl.fl.elements.BinaryOperator;
import snl2fl.fl.elements.Formula;
import snl2fl.req.expressions.*;
import snl2fl.req.visitor.ContextBasedVisitor;

/**
 * The Class LTLExpressionVisitor.
 *
 * @author Simone Vuotto
 */
public class LTLExpressionVisitor extends ContextBasedVisitor<LTLContext> implements snl2fl.req.visitor.ExpressionVisitor {

    /** The formula. */
    private Formula formula;

    /**
     * Instantiates a new LTL expression visitor.
     *
     * @param context the context
     */
    public LTLExpressionVisitor(LTLContext context) {
        super(context);
    }

    /**
     * Gets the formula.
     *
     * @return the formula
     */
    public Formula getFormula() { return formula; }

    /* (non-Javadoc)
     * @see snl2fl.req.visitor.ExpressionVisitor#visitBooleanExpression(snl2fl.req.expressions.BooleanExpression)
     */
    @Override
    public void visitBooleanExpression(BooleanExpression exp) {
        exp.getLeftExp().accept(this);
        Formula a = formula;
        exp.getRightExp().accept(this);
        Formula b = formula;
        Formula formula = null;
        switch (exp.getOperator()) {
            case AND:
                formula = new BinaryOperator(a, b, BinaryOperator.Operator.AND);
                break;
            case OR:
                formula = new BinaryOperator(a, b, BinaryOperator.Operator.OR);
                break;
            case XOR:
                formula = new BinaryOperator(a, b, BinaryOperator.Operator.XOR);
                break;
        }
        this.formula = formula;
    }

    /* (non-Javadoc)
     * @see snl2fl.req.visitor.ExpressionVisitor#visitCompareExpression(snl2fl.req.expressions.CompareExpression)
     */
    @Override
    public void visitCompareExpression(CompareExpression exp) {
        Float threshold = null;
        String varName = null;
        if(exp.getLeftExp() instanceof NumberExpression)
            threshold =  ((NumberExpression)exp.getLeftExp()).floatValue();
        if(exp.getLeftExp() instanceof FloatVariableExpression)
            varName = ((FloatVariableExpression)exp.getLeftExp()).getName();
        if(exp.getRightExp() instanceof NumberExpression)
            threshold =  ((NumberExpression)exp.getRightExp()).floatValue();
        if(exp.getRightExp() instanceof FloatVariableExpression)
            varName = ((FloatVariableExpression)exp.getRightExp()).getName();
        if(threshold == null || varName == null)
            return; // Not handled the case of two constants or two variables.
        formula = getContext().getFormula(varName, threshold, exp.getOperator());
    }

    /* (non-Javadoc)
     * @see snl2fl.req.visitor.ExpressionVisitor#visitBooleanVariableExpression(snl2fl.req.expressions.BooleanVariableExpression)
     */
    @Override
    public void visitBooleanVariableExpression(BooleanVariableExpression exp) {
        formula = new Atom(exp.getName());
    }

    /* (non-Javadoc)
     * @see snl2fl.req.visitor.ExpressionVisitor#visitNumberExpression(snl2fl.req.expressions.NumberExpression)
     */
    @Override
    public void visitNumberExpression(NumberExpression exp) {

    }

    /* (non-Javadoc)
     * @see snl2fl.req.visitor.ExpressionVisitor#visitFloatVariableExpression(snl2fl.req.expressions.FloatVariableExpression)
     */
    @Override
    public void visitFloatVariableExpression(FloatVariableExpression exp) {

    }
}
