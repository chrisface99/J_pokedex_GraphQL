package com.pokedex.controller;

import com.pokedex.model.Pokemon;
import com.pokedex.model.PokemonListResponse;
import com.pokedex.service.PokeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PokedexController {

    @Autowired
    private PokeApiService pokeApiService;

    @GetMapping("/")
    public String home(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String type,
            Model model) {

        int pageSize = 24;
        int offset = page * pageSize;

        if (!search.isBlank()) {
            Pokemon pokemon = pokeApiService.getPokemon(search.trim());
            List<Pokemon> results = new ArrayList<>();
            if (pokemon != null) results.add(pokemon);
            model.addAttribute("pokemons", results);
            model.addAttribute("totalPages", 1);
            model.addAttribute("currentPage", 0);
            model.addAttribute("search", search);
            model.addAttribute("type", type);
        } else if (!type.isBlank()) {
            List<Pokemon> pokemons = pokeApiService.getPokemonByType(type);
            model.addAttribute("pokemons", pokemons);
            model.addAttribute("totalPages", 1);
            model.addAttribute("currentPage", 0);
            model.addAttribute("search", search);
            model.addAttribute("type", type);
        } else {
            PokemonListResponse listResponse = pokeApiService.getPokemonList(offset, pageSize);
            List<Pokemon> pokemons = new ArrayList<>();

            if (listResponse != null && listResponse.getResults() != null) {
                for (PokemonListResponse.PokemonEntry entry : listResponse.getResults()) {
                    Pokemon p = pokeApiService.getPokemon(entry.getName());
                    if (p != null) pokemons.add(p);
                }
            }

            int total = listResponse != null ? listResponse.getCount() : 0;
            int totalPages = (int) Math.ceil((double) total / pageSize);

            model.addAttribute("pokemons", pokemons);
            model.addAttribute("totalPages", Math.min(totalPages, 55)); // cap at gen 1-8
            model.addAttribute("currentPage", page);
            model.addAttribute("search", search);
            model.addAttribute("type", type);
        }

        model.addAttribute("types", getTypes());
        return "index";
    }

    @GetMapping("/pokemon/{id}")
    public String pokemonDetail(@PathVariable String id, Model model) {
        Pokemon pokemon = pokeApiService.getPokemon(id);
        if (pokemon == null) {
            return "redirect:/";
        }

        // Get prev/next
        if (pokemon.getId() > 1) {
            model.addAttribute("prevId", pokemon.getId() - 1);
        }
        model.addAttribute("nextId", pokemon.getId() + 1);

        model.addAttribute("pokemon", pokemon);
        return "detail";
    }

    private List<String> getTypes() {
        return List.of(
            "normal", "fire", "water", "electric", "grass", "ice",
            "fighting", "poison", "ground", "flying", "psychic", "bug",
            "rock", "ghost", "dragon", "dark", "steel", "fairy"
        );
    }
}
