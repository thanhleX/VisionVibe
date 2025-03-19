package com.cx.visionvibebe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class InvalidToken {
    @Id
    private String tokenId;
    private Date expiryTime;
}
