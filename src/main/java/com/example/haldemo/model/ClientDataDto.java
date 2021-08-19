package com.example.haldemo.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClientDataDto {
    private String partnerId;
    private String partnerFullName;
    private String language;
}
