package integration.services;

import model.Lanche;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CardapioTestIT extends TestCommon {

    @Test
    public void shouldBeAbleToFetchCardapio(){

        ResponseEntity<List<Lanche>> lanchesInCardapio = mckingBurguerClient.getLanchesInCardapio();

        List<Lanche> lanches = lanchesInCardapio.getBody();

        assertThat(lanches).isNotNull();

        assertThat(lanches.size()).isEqualTo(4);
    }
}
