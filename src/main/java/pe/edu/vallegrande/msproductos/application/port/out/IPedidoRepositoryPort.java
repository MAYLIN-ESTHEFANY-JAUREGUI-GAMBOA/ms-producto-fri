package pe.edu.vallegrande.msproductos.application.port.out;

import pe.edu.vallegrande.msproductos.domain.model.Pedido;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPedidoRepositoryPort {
    Flux<Pedido> findAll();
    Mono<Pedido> findById(Long id);
    Mono<Pedido> save(Pedido pedido);
    Mono<Pedido> update(Long id, Pedido pedido);
    Mono<Void> deleteById(Long id);
}
