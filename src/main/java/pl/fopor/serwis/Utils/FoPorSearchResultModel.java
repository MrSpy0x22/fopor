package pl.fopor.serwis.Utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class FoPorSearchResultModel {
    @JsonProperty("srTitle")
    private final String srTitle;
    @JsonProperty("srType")
    private final String srType;
    @JsonFormat(pattern="HH:mm:ss, dd/MM/yyyy")
    @JsonProperty("srDatetime")
    private final LocalDateTime srDatetime;
    @JsonProperty("srLink")
    private final String srLink;

    public FoPorSearchResultModel(String srTitle, String srType, LocalDateTime srDatetime, String srLink) {
        this.srTitle = srTitle;
        this.srType = srType;
        this.srDatetime = srDatetime;
        this.srLink = srLink;
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
