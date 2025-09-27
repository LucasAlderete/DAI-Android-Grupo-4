package com.example.dai_android_grupo_4.data.api.model;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
    @SerializedName("timestamp")
    private String timestamp;
    
    @SerializedName("status")
    private int status;
    
    @SerializedName("error")
    private String error;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("trace")
    private String trace;

    public ErrorResponse() {}

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }
    
    /**
     * Extrae el mensaje de error específico del trace
     * @return El mensaje de error específico o el error general
     */
    public String getSpecificErrorMessage() {
        if (trace != null && trace.contains("RuntimeException: ")) {
            // Extraer el mensaje después de "RuntimeException: "
            int startIndex = trace.indexOf("RuntimeException: ") + "RuntimeException: ".length();
            int endIndex = trace.indexOf("\r\n", startIndex);
            if (endIndex == -1) {
                endIndex = trace.indexOf("\n", startIndex);
            }
            if (endIndex == -1) {
                endIndex = trace.length();
            }
            return trace.substring(startIndex, endIndex).trim();
        }
        
        // Si no hay trace específico, usar el mensaje o error general
        if (message != null && !message.isEmpty()) {
            return message;
        }
        
        return error != null ? error : "Error desconocido";
    }
}
