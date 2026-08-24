package com.agrigo.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO {
    private Long id;
    private Long userId;
    private String storeName;
    private String description;
    private String location;
    private String phone;
    private String email;
    private String address;
}
