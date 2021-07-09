package pl.fopor.serwis.Utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class FoPorSearchResultModel {
    @JsonProperty("srTitle")
    private final String srTitle;
    @JsonProperty("srType")
    private final String srType;
    @JsonProperty("srDatetime")
    private final LocalDateTime srDatetime;

    public FoPorSearchResultModel(String srTitle, String srType, LocalDateTime srDatetime) {
        this.srTitle = srTitle;
        this.srType = srType;
        this.srDatetime = srDatetime;
    }

    public String getTitle() {
        return this.srTitle;
    }

    public String getType() {
        return this.srType;
    }

    public LocalDateTime getDatetime() {
        return this.srDatetime;
    }

    public String toString() {
        return this.srTitle;
    }
}
