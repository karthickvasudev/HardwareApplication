package com.application.hardwarapplication.modals;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
    private String id;
    private String name;
    private String phoneNumber;
    private String address;
    private String createdOn;
    private String updatedOn;
}
