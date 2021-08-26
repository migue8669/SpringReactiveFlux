package co.com.ias.capacitacion.controller;

import co.com.ias.capacitacion.models.documents.Producto;
import co.com.ias.capacitacion.models.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller()
public class ProductoController {
    @Autowired
    private ProductoRepository productoRepository;
     private static  final Logger log= LoggerFactory.getLogger(ProductoController.class);
    @GetMapping({"/listar",""})
    public String listar(Model model) {
        Flux<Producto> productoFlux = productoRepository.findAll().map(producto -> {
            producto.setNombre((producto.getNombre().toUpperCase()));
            return producto;
        });
        productoFlux.subscribe(producto -> log.info(producto.getNombre()));

        model.addAttribute("producto ", productoFlux);
        model.addAttribute("titulo", "listado de productos");
        return "listar";
    }

    @GetMapping("/listar-dataDrive")
    public String listarDataDrive(Model model) {
        Flux<Producto> productoFlux = productoRepository.findAll().map(producto -> {
            producto.setNombre((producto.getNombre().toUpperCase()));
            return producto;
        }).delayElements(Duration.ofSeconds(1));/*control de contra flujo*/
        productoFlux.subscribe(producto -> log.info(producto.getNombre()));

        model.addAttribute("producto ", new ReactiveDataDriverContextVariable(productoFlux,2));
        model.addAttribute("titulo", "listado de productos");
        return "listar";
    }

    @GetMapping("/listar-full")
    public String listarFull(Model model) {
        Flux<Producto> productoFlux = productoRepository.findAll().map(producto -> {
            producto.setNombre((producto.getNombre().toUpperCase()));
            return producto;
        }).repeat(5000);

        model.addAttribute("producto ", productoFlux);
        model.addAttribute("titulo", "listado de productos");
        return "listar";
    }
    @GetMapping("/listar-chunked")
    public String listarChunked(Model model) {
        Flux<Producto> productoFlux = productoRepository.findAll().map(producto -> {
            producto.setNombre((producto.getNombre().toUpperCase()));
            return producto;
        }).repeat(5000);

        model.addAttribute("producto ", productoFlux);
        model.addAttribute("titulo", "listado de productos");
        return "listar-chunked";
    }
}
