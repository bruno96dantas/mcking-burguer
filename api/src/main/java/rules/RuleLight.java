package rules;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import model.Ingrediente;
import promotion.IngredienteContext;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
public class RuleLight extends Rule {

    private final Ingrediente BACON = Ingrediente.BACON;
    private final Double PERCENTAGE_OF_DISCOUNT = 0.1;

    @Builder
    public RuleLight() {
        super(Ingrediente.ALFACE);
    }

    @Override
    public Double getDiscount(IngredienteContext context) {
        /* find bacon in context, if present it has a value */
        Integer baconQty = context.getEntry(BACON)
                .map(Map.Entry::getValue)
                .orElse(0);
        log.debug("Bacon quantity: " + baconQty);
        /* find alface in context, if present, it will check baconQty if it's greater then 0, if yes this rule is not valid. */
        /* otherwise, we will return a 10% of discount */
        return context.getEntry(getTargetIngrediente())
                .map(entry -> {
                            if (baconQty > 0) {
                                log.debug("Discount not applicable because we have {} instead of zero ", baconQty);
                                return 0.0;
                            }
                            return context.getTotalPrice().multiply(BigDecimal.valueOf(PERCENTAGE_OF_DISCOUNT)).doubleValue();
                        }
                )
                /* If targetIngrediente is not in the context, zero discount */
                .orElse(0.0);
    }
}
