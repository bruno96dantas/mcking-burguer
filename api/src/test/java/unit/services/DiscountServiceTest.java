package unit.services;

import model.Cardapio;
import model.Ingrediente;
import model.Lanche;
import org.assertj.core.data.Percentage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import services.DiscountService;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static model.Ingrediente.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DiscountServiceTest {

    private static final Percentage PERCENTAGE = Percentage.withPercentage(0.01);

    private Function<List<Ingrediente>, BigDecimal> valueSupplier = (x) -> x.stream()
            .map(Ingrediente::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    @InjectMocks
    private DiscountService discountService;

    @Test
    public void shouldGeneratePriceAndGetPromotionRuleLight() {
        List<Ingrediente> ingredientes = asList(ALFACE, HAMBURGER, HAMBURGER, OVO, QUEIJO);

        Lanche especial = Lanche.builder()
                .ingredientes(ingredientes)
                .name("especial")
                .build();

        BigDecimal originalPrice = especial.getTotalPrice();

        BigDecimal discount = discountService.getDiscount(especial);

        assertThat(discount)
                .isCloseTo(originalPrice.multiply(BigDecimal.valueOf(0.1)), PERCENTAGE);
        // 10% of discount
    }


    @Test
    public void shouldGeneratePriceAndGetPromotionRuleMuitaCarne() {
        List<Ingrediente> ingredientes = asList(ALFACE, BACON, HAMBURGER, HAMBURGER, HAMBURGER, OVO, QUEIJO);

        Lanche especial = Lanche.builder()
                .ingredientes(ingredientes)
                .name("especial")
                .build();

        BigDecimal originalPrice = especial.getTotalPrice();

        assertThat(originalPrice).isEqualTo(valueSupplier.apply(ingredientes));

        BigDecimal discount = discountService.getDiscount(especial);

        assertThat(discount)
                .isCloseTo(HAMBURGER.getPrice(), PERCENTAGE); // 1 Hamburguer for free


        ingredientes = asList(ALFACE, BACON, HAMBURGER, HAMBURGER, HAMBURGER, HAMBURGER, HAMBURGER, HAMBURGER, OVO, QUEIJO);

        especial.setIngredientes(ingredientes);

        originalPrice = especial.getTotalPrice();

        assertThat(originalPrice).isEqualTo(valueSupplier.apply(ingredientes));

        discount = discountService.getDiscount(especial);

        assertThat(discount)
                .isCloseTo(HAMBURGER.getPrice().multiply(BigDecimal.valueOf(2.0)), PERCENTAGE); // 2 Hamburguer for free

    }

    @Test
    public void shouldGeneratePriceAndGetPromotionRuleMuitOQueijo() {
        List<Ingrediente> ingredientes = asList(ALFACE, BACON, QUEIJO, QUEIJO, QUEIJO, OVO);

        Lanche especial = Lanche.builder()
                .ingredientes(ingredientes)
                .name("especial")
                .build();

        BigDecimal originalPrice = especial.getTotalPrice();

        assertThat(originalPrice).isEqualTo(valueSupplier.apply(ingredientes));

        BigDecimal discount = discountService.getDiscount(especial);

        assertThat(discount)
                .isCloseTo(QUEIJO.getPrice(), PERCENTAGE); // 1 QUEIJO for free

        ingredientes = asList(ALFACE, BACON, QUEIJO, QUEIJO, QUEIJO, QUEIJO, QUEIJO, QUEIJO, OVO);

        especial.setIngredientes(ingredientes);

        originalPrice = especial.getTotalPrice();

        assertThat(originalPrice).isEqualTo(valueSupplier.apply(ingredientes));

        discount = discountService.getDiscount(especial);

        assertThat(discount)
                .isCloseTo(QUEIJO.getPrice().multiply(BigDecimal.valueOf(2.0)), PERCENTAGE); // 2 queijos for free

    }


    @Test
    public void shouldGeneratePriceCorrectlyWithAllPromotions() {
        List<Ingrediente> ingredientes = asList(ALFACE, HAMBURGER, HAMBURGER, HAMBURGER, OVO, QUEIJO, QUEIJO, QUEIJO);

        Lanche especial = Lanche.builder()
                .ingredientes(ingredientes)
                .name("especial")
                .build();

        BigDecimal originalPrice = especial.getTotalPrice();

        assertThat(originalPrice).isEqualTo(valueSupplier.apply(ingredientes));

        BigDecimal promotionPrice = discountService.getDiscount(especial);

        assertThat(promotionPrice)
                .isCloseTo(BigDecimal.ZERO
                        .add(QUEIJO.getPrice())
                        .add(HAMBURGER.getPrice())
                        .add(originalPrice.multiply(BigDecimal.valueOf(0.1))), PERCENTAGE);
    }

    @Test
    public void shouldNotApplyDiscountWhenItsNotNecessary() {

        List<Lanche> lanches = asList(Cardapio.XBACON.getLanche(),
                Cardapio.XBURGER.getLanche(),
                Cardapio.XEGG.getLanche(),
                Cardapio.XEGGBACON.getLanche());

        BigDecimal totalDiscount = lanches.stream()
                .map(discountService::getDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertThat(totalDiscount).isCloseTo(BigDecimal.ZERO, PERCENTAGE);
    }

}
