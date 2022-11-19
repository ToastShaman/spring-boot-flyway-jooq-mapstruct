package com.example.demo.external;

public record Todo(
    long userId,
    long id,
    String title,
    boolean completed
) {

}
