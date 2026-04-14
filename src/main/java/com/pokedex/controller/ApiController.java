package com.pokedex.controller;

import com.pokedex.model.Pokemon;
import com.pokedex.service.PokeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private PokeApiService pokeApiService;

    @GetMapping("/pokemon/{nameOrId}")
    public ResponseEntity<Pokemon> getPokemon(@PathVariable String nameOrId) {
        Pokemon pokemon = pokeApiService.getPokemon(nameOrId);
        if (pokemon == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pokemon);
    }

    @GetMapping("/pokemon")
    public ResponseEntity<?> listPokemon(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "20") int limit) {
        return ResponseEntity.ok(pokeApiService.getPokemonList(offset, limit));
    }
}
