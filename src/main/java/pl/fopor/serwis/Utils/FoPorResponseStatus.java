package pl.fopor.serwis.Utils;

public class FoPorResponseStatus {
    Boolean isError;
    String message;

    public FoPorResponseStatus(Boolean isError, String message) {
        this.isError = isError;
        this.message = message;
    }
}