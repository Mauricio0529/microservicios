package com.microservicio.serviceproduct;

import com.microservicio.serviceproduct.models.Category;
import com.microservicio.serviceproduct.models.Product;
import com.microservicio.serviceproduct.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

/**
 * TEST CAPA REPOSITORY O PERSISTENCIA
 */

@DataJpaTest
public class ProductRepositoryMockTest {

    @Autowired
    private ProductRepository productRepository;


    // cuando busquemos por categoria me liste los productos de dicha categoria
    @Test
    public void getFindByCategory_ListProduct() {

        /*
        * Nota: en el video registra 3 categorias y tres productos por sql,
        * aqui estamos agregando otro producto
        * */
        // creamos un nuevo producto
        Product product1 = Product.builder()
                .name("computador")
                .category(Category.builder().id(1L).build()) //le pasamos el id de la categoria
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("1240.99"))
                .status("creado")
                .createAt(new Date())
                .build();

        productRepository.save(product1);

        List<Product> lista = productRepository.findByCategory(product1.getCategory()); // 1

        // si el tamaño de la listra es igual a 3,
        // a hecho una buena busqueda
        // si la lista contiene 3 productos, eso significa que esta bien
        Assertions.assertThat(lista.size()).isEqualTo(3);

    }


}
