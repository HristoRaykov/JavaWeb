package org.softuni.cardealer.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "parts")
public class Part extends BaseEntity {

    private String name;
    private BigDecimal price;
    private Supplier supplier;

    public Part() {
    }

    public Part(String name, BigDecimal price, Supplier supplier) {
        this.name = name;
        this.price = price;
        this.supplier = supplier;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @ManyToOne(targetEntity = Supplier.class)
    @JoinColumn(
            name = "supplier_id",
            referencedColumnName = "id"
    )
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
