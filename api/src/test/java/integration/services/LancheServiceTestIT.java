package integration.services;

import model.Ingrediente;
import model.utils.IngredienteUtils;
import org.assertj.core.data.Percentage;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static model.Ingrediente.*;
import static org.assertj.core.api.Assertions.assertThat;

public class LancheServiceTestIT extends TestCommon {

    private static final Percentage PERCENTAGE = Percentage.withPercentage(0.01);

    private Function<List<Ingrediente>, List<String>> toStrList = (x) -> x.stream().map(Ingrediente::getName).collect(toList());

    @Test
    public void shouldBeAbleToGetPrice() {

        List<Ingrediente> ingredientes = asList(HAMBURGER, OVO, BACON);

        ResponseEntity<Double> price = mckingBurguerClient.getPrice(toStrList.apply(ingredientes));

        assertThat(price.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(price.getBody()).isNotNull();

        Optional.ofNullable(price.getBody())
                .ifPresent(body -> assertThat(BigDecimal.valueOf(body)).isEqualTo(IngredienteUtils.getTotalPrice(ingredientes)));
    }

    @Test
    public void shouldBeAbleToGetRawPrice() {

        List<Ingrediente> ingredientes = asList(ALFACE, HAMBURGER, HAMBURGER, OVO, QUEIJO);

        ResponseEntity<Double> price = mckingBurguerClient.getRawPrice(toStrList.apply(ingredientes));

        assertThat(price.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(price.getBody()).isNotNull();

        Optional.ofNullable(price.getBody())
                .ifPresent(body -> assertThat(BigDecimal.valueOf(body)).isEqualTo(IngredienteUtils.getTotalPrice(ingredientes)));
    }

    @Test
    public void shouldBeAbleToGetDicountForRuleLight() {
        List<Ingrediente> ingredientes = asList(ALFACE, HAMBURGER, HAMBURGER, OVO, QUEIJO);

        ResponseEntity<Double> price = mckingBurguerClient.getPrice(toStrList.apply(ingredientes));

        assertThat(price.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(price.getBody()).isNotNull();

        BigDecimal totalPrice = IngredienteUtils.getTotalPrice(ingredientes);

        BigDecimal priceAfterDiscount = BigDecimal.valueOf(price.getBody());

        assertThat(priceAfterDiscount)
                .isCloseTo(totalPrice.subtract(totalPrice.multiply(BigDecimal.valueOf(0.1))), PERCENTAGE);
    }

    @Test
    public void shouldBeAbleToGetDicountForRuleMuitaCarne() {
        List<Ingrediente> ingredientes = asList(ALFACE, BACON, HAMBURGER, HAMBURGER, HAMBURGER, OVO, QUEIJO);

        ResponseEntity<Double> price = mckingBurguerClient.getPrice(toStrList.apply(ingredientes));

        assertThat(price.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(price.getBody()).isNotNull();

        BigDecimal totalPrice = IngredienteUtils.getTotalPrice(ingredientes);

        BigDecimal priceAfterDiscount = BigDecimal.valueOf(price.getBody());

        assertThat(priceAfterDiscount)
                .isCloseTo(totalPrice.subtract(HAMBURGER.getPrice()), PERCENTAGE);
    }

    @Test
    public void shouldBeAbleToGetDicountForRuleMuitoQueijo() {
        List<Ingrediente> ingredientes = asList(ALFACE, BACON, QUEIJO, QUEIJO, QUEIJO, OVO);

        ResponseEntity<Double> price = mckingBurguerClient.getPrice(toStrList.apply(ingredientes));

        assertThat(price.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(price.getBody()).isNotNull();

        BigDecimal totalPrice = IngredienteUtils.getTotalPrice(ingredientes);

        BigDecimal priceAfterDiscount = BigDecimal.valueOf(price.getBody());

        assertThat(priceAfterDiscount)
                .isCloseTo(totalPrice.subtract(QUEIJO.getPrice()), PERCENTAGE);
    }

    @Test
    public void shouldBeAbleToGetDicountForAllRules() {
        List<Ingrediente> ingredientes = asList(ALFACE, HAMBURGER, HAMBURGER, HAMBURGER, OVO, QUEIJO, QUEIJO, QUEIJO);
        ResponseEntity<Double> price = mckingBurguerClient.getPrice(toStrList.apply(ingredientes));

        assertThat(price.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(price.getBody()).isNotNull();

        BigDecimal totalPrice = IngredienteUtils.getTotalPrice(ingredientes);

        BigDecimal priceAfterDiscount = BigDecimal.valueOf(price.getBody());

        assertThat(priceAfterDiscount)
                .isCloseTo(totalPrice.subtract(HAMBURGER.getPrice()).subtract(QUEIJO.getPrice()).subtract(totalPrice.multiply(BigDecimal.valueOf(0.1))), PERCENTAGE);
    }

}
