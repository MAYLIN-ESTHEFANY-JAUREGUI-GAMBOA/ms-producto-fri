package pe.edu.vallegrande.msproductos.application.port.in;

import pe.edu.vallegrande.msproductos.domain.model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductoServicePort {
    Flux<Producto> findAll();
    Mono<Producto> findById(Long id);
    Mono<Producto> save(Producto producto);
    Mono<Producto> update(Long id, Producto producto);
    Mono<Void> deleteById(Long id);
}
