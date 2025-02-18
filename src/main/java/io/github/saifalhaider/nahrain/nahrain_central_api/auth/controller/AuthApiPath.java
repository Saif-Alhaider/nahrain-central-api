package io.github.saifalhaider.nahrain.nahrain_central_api.auth.controller;

import lombok.Getter;

public enum AuthApiPath {
    REGISTER("register"),
    LOGIN("login"),
    REFRESH_TOKEN("refreshtoken");

    public static final String BASE = "/auth"; // ✅ Compile-time constant
    public final String path;
    public final String subPath;

    AuthApiPath(String subPath) {
        this.subPath = subPath;
        this.path = BASE + '/' + subPath; // ✅ Works because it's inside the constructor
    }
}

