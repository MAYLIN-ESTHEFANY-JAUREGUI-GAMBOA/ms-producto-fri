package pe.edu.vallegrande.msproductos.infrastructure.adapter.in.rest;

import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.msproductos.application.port.in.IPedidoServicePort;
import pe.edu.vallegrande.msproductos.domain.model.Pedido;
import pe.edu.vallegrande.msproductos.domain.model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoRest {

    private final IPedidoServicePort pedidoServicePort;

    public PedidoRest(IPedidoServicePort pedidoServicePort) {
        this.pedidoServicePort = pedidoServicePort;
    }

    @GetMapping
    public Flux<Pedido> findAll() {
        return pedidoServicePort.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Pedido> findById(@PathVariable Long id) {
        return pedidoServicePort.findById(id);
    }

    @PostMapping
    public Mono<Pedido> save(@RequestBody Pedido pedido) {
        return pedidoServicePort.save(pedido);
    }

    @PutMapping("/{id}")
    public Mono<Pedido> update(@PathVariable Long id, @RequestBody Pedido pedido) {
        return pedidoServicePort.update(id, pedido);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable Long id) {
        return pedidoServicePort.deleteById(id);
    }

    // Endpoint para obtener el producto asociado a un pedido
    @GetMapping("/{id}/producto")
    public Mono<Producto> obtenerProductoDelPedido(@PathVariable Long id) {
        return pedidoServicePort.obtenerProductoDelPedido(id);
    }
}
