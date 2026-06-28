package pe.edu.vallegrande.msproductos.application.port.in;

import pe.edu.vallegrande.msproductos.domain.model.Pedido;
import pe.edu.vallegrande.msproductos.domain.model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPedidoServicePort {
    Flux<Pedido> findAll();
    Mono<Pedido> findById(Long id);
    Mono<Pedido> save(Pedido pedido);
    Mono<Pedido> update(Long id, Pedido pedido);
    Mono<Void> deleteById(Long id);
    
    // Método para obtener producto asociado a un pedido
    Mono<Producto> obtenerProductoDelPedido(Long pedidoId);
}
