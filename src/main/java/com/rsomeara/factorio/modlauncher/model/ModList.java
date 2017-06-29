package com.rsomeara.factorio.modlauncher.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;

public class ModList {

    private final List<Mod> mods;

    @JsonCreator
    public ModList(@JsonProperty(value = "mods", required = true) List<Mod> mods) {
        this.mods = Objects.requireNonNull(mods);
    }

    public List<Mod> getMods() {
        return mods;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMods());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof ModList) {
            ModList compare = (ModList) obj;

            result = Objects.equals(compare.getMods(), getMods());
        }

        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).omitNullValues()
                .add("mods", getMods())
                .toString();
    }

    public static ModList fromFile(Path modListPath) throws IOException {
        return new ObjectMapper()
                .readValue(modListPath.toFile(), ModList.class);
    }

}
