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

    //region 2xx
    public static ResponseEntity<ActionResult> returnSuccess(String message, Object data) {
        return ResponseEntity.ok(new ActionResult(200, message, data));
    }

    public static ResponseEntity<ActionResult> returnCreated(String message) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ActionResult(201, message));
    }
    //endregion

    //region 4xx
    public static ResponseEntity<ActionResult> returnBadRequest(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ActionResult(400, message));
    }

    public static ResponseEntity<ActionResult> returnUnauthorized(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ActionResult(401, message));
    }

    public static ResponseEntity<ActionResult> returnNotFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ActionResult(404, message));
    }
    //endregion

}
