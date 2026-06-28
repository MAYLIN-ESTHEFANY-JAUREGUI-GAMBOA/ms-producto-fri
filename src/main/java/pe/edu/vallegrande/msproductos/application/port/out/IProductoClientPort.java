package pe.edu.vallegrande.msproductos.application.port.out;

import pe.edu.vallegrande.msproductos.domain.model.Producto;
import reactor.core.publisher.Mono;

public interface IProductoClientPort {
    Mono<Producto> obtenerProductoPorId(Long id);
    Mono<Boolean> verificarProductoDisponible(Long id);
}
