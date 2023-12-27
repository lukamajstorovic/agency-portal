package com.lmajstorovic.agencyportal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "divisions")
public class Division {
   @Id
   private UUID id = UUID.randomUUID();
   private Integer position;
   private String name;
   private Boolean deleted = false;
   @Column(name = "created_at")
   private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
   
   public Division(
      Integer order,
      String name
   ) {
      this.position = order;
      this.name = name;
   }
   
}
