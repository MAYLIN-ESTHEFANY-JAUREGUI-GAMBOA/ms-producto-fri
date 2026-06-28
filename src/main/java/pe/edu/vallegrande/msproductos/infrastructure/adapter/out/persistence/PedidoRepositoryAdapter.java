package pe.edu.vallegrande.msproductos.infrastructure.adapter.out.persistence;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.msproductos.application.port.out.IPedidoRepositoryPort;
import pe.edu.vallegrande.msproductos.domain.model.Pedido;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PedidoRepositoryAdapter implements IPedidoRepositoryPort {

    private final PedidoRepository pedidoRepository;

    public PedidoRepositoryAdapter(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public Flux<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public Mono<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public Mono<Pedido> save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Mono<Pedido> update(Long id, Pedido pedido) {
        pedido.setId(id);
        return pedidoRepository.save(pedido);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return pedidoRepository.deleteById(id);
    }
}
