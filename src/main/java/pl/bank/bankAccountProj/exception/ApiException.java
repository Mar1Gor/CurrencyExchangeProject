package pl.bank.bankAccountProj.exception;

public class ApiException extends RuntimeException {
    private String errorCode;
    private String message;

    private Object errorDetails;

    public ApiException(String errorCode, String message, Object errorDetails) {
        this.errorCode = errorCode;
        this.message = message;
        this.errorDetails = errorDetails;
    }

    public ApiException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
