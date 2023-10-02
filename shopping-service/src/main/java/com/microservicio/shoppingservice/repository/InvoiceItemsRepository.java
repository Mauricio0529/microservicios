package com.microservicio.shoppingservice.repository;

import com.microservicio.shoppingservice.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemsRepository extends JpaRepository<InvoiceItem,Long> {

}