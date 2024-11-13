package com.apuliadigitalmaker.gestionalespese.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public final class ResponseBuilder {

    private static final int successCode = 200;
    private static final int badRequestCode = 400;
    private static final int unauthorizedCode = 401;
    private static final int forbiddenCode = 403;
    private static final int notFoundCode = 404;
    private static final int methodNotAllowedCode = 405;
    private static final int errorCode = 500;

    public static ResponseEntity<?> success(Object data) {
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> response = new HashMap<>();

        response.put("success", true);
        response.put("data", data);

        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(response, headers, successCode);
    }

    public static ResponseEntity<?> notFound(String message) {
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> response = new HashMap<>();

        response.put("success", false);
        response.put("message", message);

        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(response, headers, notFoundCode);
    }

    public static ResponseEntity<?> error() {
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> response = new HashMap<>();

        response.put("success", false);
        response.put("message", "Something went wrong");

        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(response, headers, errorCode);
    }

    public static ResponseEntity<?> errorMessage(String message) {
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> response = new HashMap<>();

        response.put("success", false);
        response.put("message", message);

        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(response, headers, errorCode);
    }



    public static ResponseEntity<?> deleted(String message) {
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> response = new HashMap<>();

        response.put("success", true);
        response.put("message", message);

        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(response, headers, successCode);
    }

    public static ResponseEntity<?> searchResults(Object results, int count) {
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> response = new HashMap<>();

        response.put("success", true);
        response.put("count", count);
        response.put("results", results);

        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(response, headers, successCode);
    }

    public static ResponseEntity<?> badRequest(String message) {
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> response = new HashMap<>();

        response.put("success", false);
        response.put("message", message);

        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(response, headers, badRequestCode);
    }

    public static ResponseEntity<?> badCredentials() {
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> response = new HashMap<>();

        response.put("success", false);
        response.put("message", "Invalid username or password");

        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(response, headers, unauthorizedCode);
    }

    public static ResponseEntity<?> authSuccess(String jwt) {
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> response = new HashMap<>();

        JwtUtil jwtUtil = new JwtUtil();

        response.put("success", true);
        response.put("token", jwt);
        response.put("username", jwtUtil.extractUsernameAndId(jwt));

        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(response, headers, successCode);
    }
}
