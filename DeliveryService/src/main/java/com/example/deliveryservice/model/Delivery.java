package com.example.deliveryservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Delivery
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        private int order_id;

        private String delivery_status;

}
