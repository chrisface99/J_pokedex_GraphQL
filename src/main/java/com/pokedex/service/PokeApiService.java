package com.pokedex.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pokedex.model.Pokemon;
import com.pokedex.model.PokemonListResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PokeApiService {

    private static final String BASE_URL = "https://pokeapi.co/api/v2";
    private final RestTemplate restTemplate;

    public PokeApiService() {
        this.restTemplate = new RestTemplate();
    }

    @Cacheable(value = "pokemonList", key = "#offset + '-' + #limit")
    public PokemonListResponse getPokemonList(int offset, int limit) {
        String url = BASE_URL + "/pokemon?offset=" + offset + "&limit=" + limit;
        return restTemplate.getForObject(url, PokemonListResponse.class);
    }

    @Cacheable(value = "pokemon", key = "#nameOrId")
    public Pokemon getPokemon(String nameOrId) {
        try {
            String url = BASE_URL + "/pokemon/" + nameOrId.toLowerCase();
            return restTemplate.getForObject(url, Pokemon.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
    }

    public Pokemon getPokemonById(int id) {
        return getPokemon(String.valueOf(id));
    }

    public List<Pokemon> getPokemonByType(String type) {
        try {
            String url = BASE_URL + "/type/" + type.toLowerCase();
            TypeResponse typeResponse = restTemplate.getForObject(url, TypeResponse.class);
            List<Pokemon> pokemonList = new ArrayList<>();
            if (typeResponse != null && typeResponse.getPokemonList() != null) {
                int count = 0;
                for (TypeResponse.TypePokemon tp : typeResponse.getPokemonList()) {
                    if (count >= 20) break;
                    if (tp.getPokemonRef() != null) {
                        Pokemon p = getPokemon(tp.getPokemonRef().getName());
                        if (p != null) pokemonList.add(p);
                        count++;
                    }
                }
            }
            return pokemonList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TypeResponse {
        @JsonProperty("pokemon")
        private List<TypePokemon> pokemonList;

        public List<TypePokemon> getPokemonList() { return pokemonList; }
        public void setPokemonList(List<TypePokemon> pokemonList) { this.pokemonList = pokemonList; }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class TypePokemon {
            @JsonProperty("pokemon")
            private PokemonRef pokemonRef;

            public PokemonRef getPokemonRef() { return pokemonRef; }
            public void setPokemonRef(PokemonRef pokemonRef) { this.pokemonRef = pokemonRef; }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class PokemonRef {
            private String name;
            private String url;

            public String getName() { return name; }
            public void setName(String name) { this.name = name; }
            public String getUrl() { return url; }
            public void setUrl(String url) { this.url = url; }
        }
    }
}
