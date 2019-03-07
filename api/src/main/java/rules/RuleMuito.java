package rules;

import model.Ingrediente;
import promotion.IngredienteContext;

public class RuleMuito extends Rule {

    private final int MINIMUM_PORTION = 3;

    public RuleMuito(Ingrediente targetIngredienteName) {
        super(targetIngredienteName);
    }

    /**
     * Eu divido a quantidade do ingrediente e divido pela porção minima para eu ter o desconto.
     * Examplo:  5/3 = 1.6 => 1   ou  6/3 = 2 => 2
     * Com esse valor, eu o multiplico pelo valor para saber quanto desconto terei
     *
     * @return Total de desconto para essa Rule caso seja o target
     */
    @Override
    public Double getDiscount(IngredienteContext context) {
        /* find entity name in context*/
        return context.getEntry(getTargetIngrediente())
                .map(entry -> {
                    /* get get target entity qty */
                    Integer targetIngredientQty = entry.getValue();
                    /* get ingrediente */
                    Ingrediente ingrediente = entry.getKey();
                    /* int variable will always round down, so 5/3 = 1.66 = 1 */
                    int i = targetIngredientQty / MINIMUM_PORTION;
                    return i * ingrediente.getPrice().doubleValue();
                })
                /* if no match, return zero discount */
                .orElse(0.0);
    }

    public static class RuleMuitaCarne extends RuleMuito {
        public RuleMuitaCarne() {
            super(Ingrediente.HAMBURGER);
        }
    }

    public static class RuleMuitoQueijo extends RuleMuito {

        public RuleMuitoQueijo() {
            super(Ingrediente.QUEIJO);
        }
    }

}
