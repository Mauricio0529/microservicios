package com.microservicio.serviceproduct.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.serviceproduct.Exceptions.ErrorMessage;
import com.microservicio.serviceproduct.models.Category;
import com.microservicio.serviceproduct.models.Product;
import com.microservicio.serviceproduct.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Se puede filtrar por categoria o listar sin categoria.
     * Si no se ingresa una categoria lista todos los productos,
     * ya que la categoria no es requeria (required = false) al momento de usar este endpoint
     * @param id Categoria id a listar productos
     * @return Lista de productos
     */
    @GetMapping
    public ResponseEntity<List<Product>> allProduct(@RequestParam(name = "id", required = false) Long id) {

        List<Product> productServiceList = new ArrayList<>();

        if(id == null) {
            productServiceList = productService.getAll();

            if(productServiceList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            productServiceList = productService.getByCategory(Category.builder().id(id).build());
            if(productServiceList.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(productServiceList);
    }

    /**
     * Obtener un producto dado su id
     * @param id Id del producto
     * @return Producto encontrado
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {

        Product product = productService.getProduct(id);

        if(product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product, BindingResult result){

        if (result.hasErrors()){
            // NO SE ESTA MOSTRANDO EL MENSAJE, PERO LA VALIDACION ESTA BIEN.
            //System.out.println("-> " + formatMessageError(result));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Product productCreate =  productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }

    /**
     * formato de mensaje de error
     * @param result Error al validar las variables
     * @return Un JSONSTRING del error de mensaje
     */
   /* private String formatMessageError(BindingResult result) {
        List<Map<String, String>> errorList =  result.getFieldErrors().stream()
                .map(error -> {
                    Map<String, String> err = new HashMap<>();
                    err.put(error.getField(), error.getDefaultMessage());
                    return err;
                }).collect(Collectors.toList());

        ErrorMessage  errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errorList).build();
*/
        /**
         * ESTO ES UN JACKSON
         * CONVERTIR UN OBJECTO (JSON) A JSON STRING
         */
 /*       ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }
*/
    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@RequestParam("id") Long id, @RequestBody Product product) {
        // para buscar el producto en el service
        product.setId(id);
        Product updateProduct = productService.update(product).get();

        if(updateProduct == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("id") Long id) {

        boolean deleteProduct = productService.delete(id);

        if(!deleteProduct) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(deleteProduct);
    }

    /**
     * Actualizar el stock dada el id de un producto
     * @param idProduct Id del producto a buscar
     * @param quantity Cantidad del producto a ingresar al stock
     * @return Producto con el stock actualizado
     */
    @GetMapping(value = "/{idProduct}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable("idProduct") Long idProduct,
                                                      @RequestParam(name = "quantity", required = true) Double quantity) {

        Product updateProduct = productService.updateStock(idProduct, quantity).get();

        if(updateProduct == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }

}