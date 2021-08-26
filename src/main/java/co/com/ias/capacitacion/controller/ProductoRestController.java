package co.com.ias.capacitacion.controller;

import co.com.ias.capacitacion.models.documents.Producto;
import co.com.ias.capacitacion.models.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {
    @Autowired
    private ProductoRepository productoRepository;
    private static  final Logger log= LoggerFactory.getLogger(ProductoController.class);
    @GetMapping
    public Flux<Producto> index(){
        Flux<Producto> productoFlux = productoRepository.findAll().map(producto -> {
            producto.setNombre((producto.getNombre().toUpperCase()));
            return producto;
        }).doOnNext(prod-> log.info(prod.getNombre()));
       return productoFlux;
    }

    @GetMapping("/{id}")
    public Mono<Producto> show(@PathVariable String id){
        Flux<Producto> productoFlux = productoRepository.findAll();
        Mono<Producto> productoMono = productoFlux.filter(producto -> producto.getId().equals(id)).next().doOnNext(prod->log.info(prod.getNombre()));

        return productoMono;
    }
}
