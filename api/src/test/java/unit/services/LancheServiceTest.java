package unit.services;

import model.Cardapio;
import model.Lanche;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.DiscountService;
import services.LancheService;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class LancheServiceTest {


    @Mock
    private DiscountService discountService;

    @InjectMocks
    private LancheService lancheService;

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenDiscountIsGreaterThanValue() {

        Lanche lanche = Cardapio.XBACON.getLanche();

        doReturn(BigDecimal.TEN).when(discountService).getDiscount(eq(lanche));

        lancheService.calculatePrice(lanche);
    }
}
