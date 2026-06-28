package pe.edu.vallegrande.msproductos.service;

import org.springframework.stereotype.Service;
import pe.edu.vallegrande.msproductos.application.port.in.IPedidoServicePort;
import pe.edu.vallegrande.msproductos.application.port.out.IPedidoRepositoryPort;
import pe.edu.vallegrande.msproductos.application.port.out.IProductoClientPort;
import pe.edu.vallegrande.msproductos.domain.model.Pedido;
import pe.edu.vallegrande.msproductos.domain.model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PedidoService implements IPedidoServicePort {

    private final IPedidoRepositoryPort pedidoRepositoryPort;
    private final IProductoClientPort productoClientPort;

    public PedidoService(IPedidoRepositoryPort pedidoRepositoryPort, IProductoClientPort productoClientPort) {
        this.pedidoRepositoryPort = pedidoRepositoryPort;
        this.productoClientPort = productoClientPort;
    }

    @Override
    public Flux<Pedido> findAll() {
        return pedidoRepositoryPort.findAll();
    }

    @Override
    public Mono<Pedido> findById(Long id) {
        return pedidoRepositoryPort.findById(id);
    }

    @Override
    public Mono<Pedido> save(Pedido pedido) {
        return pedidoRepositoryPort.save(pedido);
    }

    @Override
    public Mono<Pedido> update(Long id, Pedido pedido) {
        return pedidoRepositoryPort.update(id, pedido);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return pedidoRepositoryPort.deleteById(id);
    }

    @Override
    public Mono<Producto> obtenerProductoDelPedido(Long pedidoId) {
        return findById(pedidoId)
                .flatMap(pedido -> productoClientPort.obtenerProductoPorId(pedido.getProductId()));
    }
}
