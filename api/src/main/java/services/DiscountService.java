package services;

import model.Ingrediente;
import model.Lanche;
import org.springframework.stereotype.Component;
import promotion.IngredienteContext;
import rules.Rule;
import rules.RuleLight;
import rules.RuleMuito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static model.utils.IngredienteUtils.getIngredienteQuantity;
import static model.utils.IngredienteUtils.getTotalPrice;

@Component
public class DiscountService {

    private final List<Rule> applicableRules = asList(new RuleLight(), new RuleMuito.RuleMuitoQueijo(), new RuleMuito.RuleMuitaCarne());

    public BigDecimal getDiscount(Lanche lanche) {

        return getDiscount(lanche.getIngredientes());

    }

    /**
     * Essa função pega todas as regras disponivels e testa se o contexto atual da match com a mesma.
     * Caso sim, essa vai retornar um valor BigDecimal que vai ser o valor do desconto.
     * Soma todos os resultados e retornar um disconto total
     */
    public BigDecimal getDiscount(List<Ingrediente> ingredientes) {

        Map<Ingrediente, Integer> contextMap = getIngredienteQuantity(ingredientes);

        BigDecimal totalPrice = getTotalPrice(ingredientes);

        IngredienteContext context = IngredienteContext.builder()
                .context(contextMap)
                .totalPrice(totalPrice)
                .build();

        /* Sum all applicable discounts */
        return applicableRules.stream()
                .map(rule -> rule.getDiscount(context))
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
