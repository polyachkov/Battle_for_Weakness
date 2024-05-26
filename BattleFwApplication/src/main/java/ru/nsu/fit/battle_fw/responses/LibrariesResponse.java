package ru.nsu.fit.battle_fw.responses;

import ru.nsu.fit.battle_fw.responses.info.LibraryInfo;

import java.util.List;

public class LibrariesResponse {
    List<LibraryInfo> libraries;

    public LibrariesResponse(List<LibraryInfo> libraries) {
        this.libraries = libraries;
    }

    public List<LibraryInfo> getLibraries() {
        return libraries;
    }

    public void setLibraries(List<LibraryInfo> libraries) {
        this.libraries = libraries;
    }
}
