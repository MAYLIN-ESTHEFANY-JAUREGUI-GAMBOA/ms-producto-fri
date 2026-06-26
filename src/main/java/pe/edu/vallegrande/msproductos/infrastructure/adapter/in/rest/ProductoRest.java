package pe.edu.vallegrande.msproductos.infrastructure.adapter.in.rest;

import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.msproductos.application.port.in.IProductoServicePort;
import pe.edu.vallegrande.msproductos.domain.model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoRest {

    private final IProductoServicePort productoServicePort;

    public ProductoRest(IProductoServicePort productoServicePort) {
        this.productoServicePort = productoServicePort;
    }

    @GetMapping
    public Flux<Producto> findAll() {
        return productoServicePort.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Producto> findById(@PathVariable Long id) {
        return productoServicePort.findById(id);
    }

    @PostMapping
    public Mono<Producto> save(@RequestBody Producto producto) {
        return productoServicePort.save(producto);
    }

    @PutMapping("/{id}")
    public Mono<Producto> update(@PathVariable Long id, @RequestBody Producto producto) {
        return productoServicePort.update(id, producto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable Long id) {
        return productoServicePort.deleteById(id);
    }
}
