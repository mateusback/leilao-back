package com.mateus_back.leilao.common;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class ActionResult {
    private int statusCode;
    private String message;
    private Object data;

    public ActionResult(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ActionResult(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public static ResponseEntity<ActionResult> returnSuccess(String message, Object data) {
        return ResponseEntity.ok(new ActionResult(200, message, data));
    }

    public static ResponseEntity<ActionResult> returnCreated(String message) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ActionResult(201, message));
    }

    public static ResponseEntity<ActionResult> returnError(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ActionResult(500, message));
    }

    public static ResponseEntity<ActionResult> returnDefinedError(String message, int statusCode) {
        return ResponseEntity.status(statusCode)
                .body(new ActionResult(statusCode, message));
    }
}
