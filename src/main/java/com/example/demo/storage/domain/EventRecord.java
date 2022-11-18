package com.example.demo.storage.domain;

import java.time.LocalDateTime;

public record EventRecord<R>(
    R id,
    String title,
    LocalDateTime startDate
) {

}
