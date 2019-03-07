package services;

import model.Cardapio;
import model.Ingrediente;
import model.Lanche;
import model.utils.IngredienteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static model.Ingrediente.*;

@Service
public class LancheService {

    @Autowired
    private DiscountService discountService;


    /**
     * Pega o preço total do lanche para depois validar com a quantidade de desconto, se maior, uma situação invalida.
     *
     * @return Preço total substraido pelo total de desconto
     */
    public BigDecimal calculatePrice(Lanche lanche) {

        BigDecimal lanchePrice = lanche.getTotalPrice();

        BigDecimal totalDiscount = discountService.getDiscount(lanche);

        // -1 means that that totalDiscount is greater then lanchePrice
        if (lanchePrice.compareTo(totalDiscount) < 0) {
            throw new RuntimeException("We got more discount than the price");
        }

        return lanchePrice.subtract(totalDiscount);
    }

    public List<Lanche> getLanchesInCardapio() {
        return asList(Cardapio.XBACON.getLanche(),
                Cardapio.XBURGER.getLanche(),
                Cardapio.XEGG.getLanche(),
                Cardapio.XEGGBACON.getLanche());
    }

    public List<Ingrediente> getIngredientes() {
        return asList(ALFACE, HAMBURGER, QUEIJO, BACON, OVO);
    }

    /**
     * @return Raw price without promotion
     */
    public BigDecimal getOriginalIngredientesPrice(List<Ingrediente> ingredientes) {
        return IngredienteUtils.getTotalPrice(ingredientes);
    }

    public BigDecimal calculatePrice(List<Ingrediente> ingredientes) {

        BigDecimal totalPrice = IngredienteUtils.getTotalPrice(ingredientes);

        BigDecimal discount = discountService.getDiscount(ingredientes);

        // -1 means that that totalDiscount is greater then lanchePrice
        if (totalPrice.compareTo(discount) < 0) {
            throw new RuntimeException("We got more discount than the price");
        }

        return totalPrice.subtract(discount);
    }

}
