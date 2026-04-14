package com.pokedex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonListResponse {
    private int count;
    private String next;
    private String previous;
    private List<PokemonEntry> results;

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
    public String getNext() { return next; }
    public void setNext(String next) { this.next = next; }
    public String getPrevious() { return previous; }
    public void setPrevious(String previous) { this.previous = previous; }
    public List<PokemonEntry> getResults() { return results; }
    public void setResults(List<PokemonEntry> results) { this.results = results; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PokemonEntry {
        private String name;
        private String url;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }

        public int getId() {
            String[] parts = url.split("/");
            return Integer.parseInt(parts[parts.length - 1]);
        }
    }
}
