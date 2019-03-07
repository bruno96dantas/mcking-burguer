package rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.Ingrediente;
import promotion.IngredienteContext;

@Data
@AllArgsConstructor
public abstract class Rule {

    /**
     * Esse é o campo que vai armazenar qual ingrediente vai ser o alvo.
     * No caso da RuleLight é o ALFACE.
     */
    private Ingrediente targetIngrediente;

    /**
     * Returna o desconto se applicavel ou 0.0
     */
    public abstract Double getDiscount(IngredienteContext context);
}
