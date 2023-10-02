package com.microservicio.serviceproduct.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
@Table(name = "tbl_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // nos permite hacer nuevas instancias de esta clase, este reemplazaria el = new produtc()
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** EL MAPEO DE LA VALIDACIONES
     * Validar los campos
     * Validar que el campo (name) NO SEA VACIO al momento de ingresar el dato a la variable
     */
    @NotEmpty(message = "El nombre no debe ser vacío")
    private String name;
    private String description;

    /**
     * Validar que el stock sea mayor a cero
     */
    @Positive(message = "El stock debe ser mayor que cero")
    private Double stock;
    private Double price;
    private String status;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP) // mappear la fecha y hora
    private Date createAt; // fecha de creacion

    /**
     * La categoria no sea nula, ya que es un objeto y no una variable
     */
    @NotNull(message = "La categoria no puede ser vacia")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Category category;

}