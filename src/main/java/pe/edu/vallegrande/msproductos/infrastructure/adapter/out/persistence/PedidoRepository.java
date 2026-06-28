package pe.edu.vallegrande.msproductos.infrastructure.adapter.out.persistence;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.msproductos.domain.model.Pedido;

@Repository
public interface PedidoRepository extends R2dbcRepository<Pedido, Long> {
}
