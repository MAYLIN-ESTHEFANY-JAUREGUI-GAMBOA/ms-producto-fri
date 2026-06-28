package pe.edu.vallegrande.msproductos.infrastructure.adapter.out.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.msproductos.application.port.out.IProductoClientPort;
import pe.edu.vallegrande.msproductos.domain.model.Producto;
import reactor.core.publisher.Mono;

@Component
public class ProductoClientAdapter implements IProductoClientPort {

    private final WebClient webClient;
    private final String productoServiceUrl;

    public ProductoClientAdapter(WebClient.Builder webClientBuilder,
                                  @Value("${productos.service.url:http://localhost:8081/api/productos}") String productoServiceUrl) {
        this.webClient = webClientBuilder.build();
        this.productoServiceUrl = productoServiceUrl;
    }

    @Override
    public Mono<Producto> obtenerProductoPorId(Long id) {
        return webClient.get()
                .uri(productoServiceUrl + "/" + id)
                .retrieve()
                .bodyToMono(Producto.class);
    }

    @Override
    public Mono<Boolean> verificarProductoDisponible(Long id) {
        return obtenerProductoPorId(id)
                .map(producto -> producto.getActive() != null && producto.getActive())
                .onErrorReturn(false);
    }
}
