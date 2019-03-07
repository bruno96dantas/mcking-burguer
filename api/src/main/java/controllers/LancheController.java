package controllers;

import model.Ingrediente;
import model.Lanche;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.LancheService;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/lanches")
public class LancheController {

    @Autowired
    private LancheService lancheService;


    @CrossOrigin
    @GetMapping("/cardapio")
    public ResponseEntity<List<Lanche>> getLanchesInCardapio() {
        return ResponseEntity.ok(lancheService.getLanchesInCardapio());
    }

    @CrossOrigin
    @GetMapping("/ingredientes")
    public ResponseEntity<List<Ingrediente>> getIngredientes() {
        return ResponseEntity.ok(lancheService.getIngredientes());
    }

    /* get lan*/
    @CrossOrigin
    @PostMapping("/price")
    public ResponseEntity<BigDecimal> getLanchePrice(@RequestBody List<String> ingredientes) {

        List<Ingrediente> ingredientesEnum = ingredientes.stream().map(Ingrediente::create).collect(toList());

        return ResponseEntity.ok(lancheService.calculatePrice(ingredientesEnum));
    }

    /* get lan*/
    @CrossOrigin
    @PostMapping("/price/raw")
    public ResponseEntity<BigDecimal> getRawPrice(@RequestBody List<String> ingredientes) {

        List<Ingrediente> ingredientesEnum = ingredientes.stream().map(Ingrediente::create).collect(toList());

        return ResponseEntity.ok(lancheService.getOriginalIngredientesPrice(ingredientesEnum));
    }
}
