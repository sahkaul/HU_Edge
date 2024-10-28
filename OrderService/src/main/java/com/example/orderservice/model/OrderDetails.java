package com.example.orderservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.aspectj.weaver.ast.Or;

@Entity
@Data
public class OrderDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int detailsId;

   private int restId;

   private int dish_Id;

   private int cuisineId;

   @ManyToOne
   @JoinColumn(name = "order_id_fk")
   @JsonIgnore
   private Orders order;
}
