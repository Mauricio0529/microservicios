package com.microservicio.serviceproduct;

import com.microservicio.serviceproduct.models.Category;
import com.microservicio.serviceproduct.models.Product;
import com.microservicio.serviceproduct.repository.ProductRepository;
import com.microservicio.serviceproduct.service.ProductService;
import com.microservicio.serviceproduct.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

/**
 * TEST PARA CAPA DE SERVICE.
 * EL ACCESO A LOS DATOS NO SE USARA LA BASE DE DATOS, SI NO DATOS MOCKEADOS
 *
 * https://stackoverflow.com/questions/33546124/mockito-given-versus-when
 *
 * https://refactoring.guru/es/design-patterns/singleton
 *
 */

@SpringBootTest
public class ProductServiceMockTest {

    @Mock
    private ProductRepository productRepository;

    //@InjectMocks
    private ProductService productService;

    /**
     * MOCKIAMOS LOS DATOS.
     * @BeforeEach: ESTE METODO SE EJECUTARA DE PRIMERAS.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        productService = new ProductServiceImpl(productRepository);
        /**
         * producto mockeado
         */
        Product product = Product.builder()
                .name("computador")
                .category(Category.builder().id(1L).build()) //le pasamos el id de la categoria
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("120.99"))
                .status("creado")
                .createAt(new Date())
                .build();

        /** BUSCAR UN PRODUCTO POR ID */
       Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

       /** Actualizar Stock de un producto*/
       Mockito.when(productRepository.save(product))
               .thenReturn(product);
    }

    /**
     * VALIDAR LA BUSQUEDA DEL PRODUCTO
     */

    @Test
    public void whenValidGetId_ThenReturnProduct() {
        Product found = productService.getProduct(1L);
        Assertions.assertThat(found.getName()).isEqualTo("computador");
        //Assertions.assertEquals("", "");

    }

    /**
     * VERIFICAR LA LOGICA DE NEGOCIO DEL STOCK
     */

    @Test
    public void whenValidUpdateStock_ThenReturnNewStock() {
        Optional<Product> newStock = productService.updateStock(1L, Double.parseDouble("10"));
        /**
         * .isEqualTo(20), es el valor que se espera que retorne
         */
        Assertions.assertThat(newStock.get().getStock()).isEqualTo(20);

        //System.out.println(newStock.get().getStock());
    }




}
