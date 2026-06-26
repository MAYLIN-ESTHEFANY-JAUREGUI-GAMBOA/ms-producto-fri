package pe.edu.vallegrande.msproductos.service;

import org.springframework.stereotype.Service;
import pe.edu.vallegrande.msproductos.application.port.in.IProductoServicePort;
import pe.edu.vallegrande.msproductos.application.port.out.IProductoRepositoryPort;
import pe.edu.vallegrande.msproductos.domain.model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoService implements IProductoServicePort {

    private final IProductoRepositoryPort productoRepositoryPort;

    public ProductoService(IProductoRepositoryPort productoRepositoryPort) {
        this.productoRepositoryPort = productoRepositoryPort;
    }

    @Override
    public Flux<Producto> findAll() {
        return productoRepositoryPort.findAll();
    }

    @Override
    public Mono<Producto> findById(Long id) {
        return productoRepositoryPort.findById(id);
    }

    @Override
    public Mono<Producto> save(Producto producto) {
        return productoRepositoryPort.save(producto);
    }

    @Override
    public Mono<Producto> update(Long id, Producto producto) {
        return productoRepositoryPort.update(id, producto);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return productoRepositoryPort.deleteById(id);
    }
}
