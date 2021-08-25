package co.com.ias.capacitacion;

import co.com.ias.capacitacion.models.documents.Producto;
import co.com.ias.capacitacion.models.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class SpringReactorApplication implements CommandLineRunner {
	private static final Logger log= LoggerFactory.getLogger(SpringReactorApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringReactorApplication.class, args);
	}
@Autowired
private ProductoRepository productoRepository;

	@Autowired
	private ReactiveMongoTemplate reactiveMongoTemplate;

	@Override
	public void run(String... args) throws Exception {
		reactiveMongoTemplate.dropCollection("producto").subscribe();
		Flux.just(
				new Producto("Sony Camara HD Digital", 177.89),
				new Producto("mango iPod", 46.89),
				new Producto("Sony Notebook", 846.89),
				new Producto("Hewlett Packard Multifuncional", 200.89),
				new Producto("Bianchi Bicicleta", 70.89),
				new Producto("HP Notebook Omen 17", 2500.89),
				new Producto("Mica Cómoda 5 Cajones", 150.89),
				new Producto("TV Sony Bravia OLED 4K Ultra HD", 2255.89)

		).flatMap(producto -> {
			producto.setCreateAt(new
					Date());
			return  productoRepository.save(producto); }).subscribe(producto->log.info("insert "+producto.getId()+" "+producto.getNombre()));
	}
}
