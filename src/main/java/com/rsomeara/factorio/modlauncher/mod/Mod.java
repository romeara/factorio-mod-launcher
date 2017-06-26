package com.rsomeara.factorio.modlauncher.mod;

import java.util.Objects;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class Mod {

    private final String name;

    private final boolean enabled;

    @JsonCreator
    public Mod(@JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "enabled", required = true) boolean enabled) {
        this.name = Objects.requireNonNull(name);
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(),
                isEnabled());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof Mod) {
            Mod compare = (Mod) obj;

            result = Objects.equals(compare.getName(), getName())
                    && Objects.equals(compare.isEnabled(), isEnabled());
        }

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).omitNullValues()
                .add("name", getName())
                .add("enabled", isEnabled())
                .toString();
    }

}
