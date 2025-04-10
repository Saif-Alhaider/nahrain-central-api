package io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage;

import lombok.Getter;

@Getter
public enum StageType {
    FIRST("First Stage"),
    SECOND("Second Stage"),
    THIRD("Third Stage"),
    FOURTH("Fourth Stage");

    private final String displayName;

    StageType(String displayName) {
        this.displayName = displayName;
    }
}
