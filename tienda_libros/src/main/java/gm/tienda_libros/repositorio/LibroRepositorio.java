package gm.tienda_libros.repositorio;

import gm.tienda_libros.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface LibroRepositorio extends JpaRepository<Libro, Integer> {
}
