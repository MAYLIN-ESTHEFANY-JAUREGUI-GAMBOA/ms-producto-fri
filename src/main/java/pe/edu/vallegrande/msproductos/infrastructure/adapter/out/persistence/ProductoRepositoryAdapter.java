package pe.edu.vallegrande.msproductos.infrastructure.adapter.out.persistence;

import org.springframework.stereotype.Component;
import pe.edu.vallegrande.msproductos.application.port.out.IProductoRepositoryPort;
import pe.edu.vallegrande.msproductos.domain.model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductoRepositoryAdapter implements IProductoRepositoryPort {

    private final ProductoRepository productoRepository;

    public ProductoRepositoryAdapter(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public Flux<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public Mono<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public Mono<Producto> save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Mono<Producto> update(Long id, Producto producto) {
        producto.setId(id);
        return productoRepository.save(producto);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return productoRepository.deleteById(id);
    }
}
