package com.freightfox.pdfGenerator.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freightfox.pdfGenerator.entities.OrderSummary;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name ="item")
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String quantity;

    private Double amount;

    private Double rate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private OrderSummary orderSummary;

}

