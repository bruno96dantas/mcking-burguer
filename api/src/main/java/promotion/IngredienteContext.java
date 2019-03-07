package promotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.Ingrediente;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Builder
@Data
public class IngredienteContext {

    private Map<Ingrediente, Integer> context;

    private BigDecimal totalPrice;

    /**
     * Filtra o contexto pelo ingrediente e retorna a entry
     * Find first estamos buscando pela key do mapa (não existe 2)
     */
    public Optional<Map.Entry<Ingrediente, Integer>> getEntry(Ingrediente ingrediente) {
        return context.entrySet().stream()
                .filter(entry -> entry.getKey().equals(ingrediente))
                .findFirst();
    }
}
