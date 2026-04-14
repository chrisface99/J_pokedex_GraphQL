package com.pokedex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {
    private int id;
    private String name;
    private int height;
    private int weight;
    @JsonProperty("base_experience")
    private int baseExperience;
    private List<TypeSlot> types;
    private List<StatSlot> stats;
    private List<AbilitySlot> abilities;
    private Sprites sprites;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
    public int getBaseExperience() { return baseExperience; }
    public void setBaseExperience(int baseExperience) { this.baseExperience = baseExperience; }
    public List<TypeSlot> getTypes() { return types; }
    public void setTypes(List<TypeSlot> types) { this.types = types; }
    public List<StatSlot> getStats() { return stats; }
    public void setStats(List<StatSlot> stats) { this.stats = stats; }
    public List<AbilitySlot> getAbilities() { return abilities; }
    public void setAbilities(List<AbilitySlot> abilities) { this.abilities = abilities; }
    public Sprites getSprites() { return sprites; }
    public void setSprites(Sprites sprites) { this.sprites = sprites; }

    public String getOfficialArtworkUrl() {
        try { return sprites.getOther().getOfficialArtwork().getFrontDefault(); }
        catch (NullPointerException e) { return sprites != null ? sprites.getFrontDefault() : ""; }
    }

    public String getPrimaryType() {
        if (types != null && !types.isEmpty()) return types.get(0).getType().getName();
        return "normal";
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TypeSlot {
        private int slot;
        private TypeInfo type;
        public int getSlot() { return slot; }
        public void setSlot(int slot) { this.slot = slot; }
        public TypeInfo getType() { return type; }
        public void setType(TypeInfo type) { this.type = type; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TypeInfo {
        private String name;
        private String url;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StatSlot {
        @JsonProperty("base_stat") private int baseStat;
        private int effort;
        private StatInfo stat;
        public int getBaseStat() { return baseStat; }
        public void setBaseStat(int baseStat) { this.baseStat = baseStat; }
        public int getEffort() { return effort; }
        public void setEffort(int effort) { this.effort = effort; }
        public StatInfo getStat() { return stat; }
        public void setStat(StatInfo stat) { this.stat = stat; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StatInfo {
        private String name;
        private String url;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AbilitySlot {
        @JsonProperty("is_hidden") private boolean hidden;
        private int slot;
        private AbilityInfo ability;
        public boolean isHidden() { return hidden; }
        public void setHidden(boolean hidden) { this.hidden = hidden; }
        public int getSlot() { return slot; }
        public void setSlot(int slot) { this.slot = slot; }
        public AbilityInfo getAbility() { return ability; }
        public void setAbility(AbilityInfo ability) { this.ability = ability; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AbilityInfo {
        private String name;
        private String url;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sprites {
        @JsonProperty("front_default") private String frontDefault;
        @JsonProperty("front_shiny") private String frontShiny;
        @JsonProperty("back_default") private String backDefault;
        private OtherSprites other;
        public String getFrontDefault() { return frontDefault; }
        public void setFrontDefault(String v) { this.frontDefault = v; }
        public String getFrontShiny() { return frontShiny; }
        public void setFrontShiny(String v) { this.frontShiny = v; }
        public String getBackDefault() { return backDefault; }
        public void setBackDefault(String v) { this.backDefault = v; }
        public OtherSprites getOther() { return other; }
        public void setOther(OtherSprites other) { this.other = other; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OtherSprites {
        @JsonProperty("official-artwork") private OfficialArtwork officialArtwork;
        public OfficialArtwork getOfficialArtwork() { return officialArtwork; }
        public void setOfficialArtwork(OfficialArtwork v) { this.officialArtwork = v; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OfficialArtwork {
        @JsonProperty("front_default") private String frontDefault;
        @JsonProperty("front_shiny") private String frontShiny;
        public String getFrontDefault() { return frontDefault; }
        public void setFrontDefault(String v) { this.frontDefault = v; }
        public String getFrontShiny() { return frontShiny; }
        public void setFrontShiny(String v) { this.frontShiny = v; }
    }
}
