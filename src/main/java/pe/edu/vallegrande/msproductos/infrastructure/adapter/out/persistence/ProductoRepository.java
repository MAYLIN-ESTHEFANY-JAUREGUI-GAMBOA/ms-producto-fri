package pe.edu.vallegrande.msproductos.infrastructure.adapter.out.persistence;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.msproductos.domain.model.Producto;

@Repository
public interface ProductoRepository extends R2dbcRepository<Producto, Long> {
}
