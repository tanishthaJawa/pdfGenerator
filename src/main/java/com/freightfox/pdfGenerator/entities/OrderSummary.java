package com.freightfox.pdfGenerator.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "orderSummary")
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String seller;

    private String sellerGstin;
    private String sellerAddress;
    private String buyer;
    private String buyerGstin;

    private String buyerAddress;

    @OneToMany(
            mappedBy = "orderSummary",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)

   private List<Item> items;


}
