package org.example.entradas_eventos.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.entradas_eventos.model.CompraEntrada;
import org.example.entradas_eventos.model.Evento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class EventoRepository {

    private final JdbcTemplate jdbcTemplate;

    public EventoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // --- EVENTOS ---

    public List<Evento> listarEventos() {
        log.info("Listando todos los eventos");

        String sql = """
            SELECT id, nombre, descripcion, fecha_hora, lugar, precio_base, recargo_grada, recargo_vip 
            FROM evento
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                Evento.builder()
                        .id(rs.getInt("id"))
                        .nombre(rs.getString("nombre"))
                        .descripcion(rs.getString("descripcion"))
                        .fechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime())
                        .lugar(rs.getString("lugar"))
                        .precioBase(rs.getBigDecimal("precio_base"))
                        .recargoGrada(rs.getBigDecimal("recargo_grada"))
                        .recargoVip(rs.getBigDecimal("recargo_vip"))
                        .build()
        );
    }

    public Evento obtenerPorId(Integer id) {
        String sql = """
        SELECT id, nombre, descripcion, fecha_hora, lugar, precio_base, recargo_grada, recargo_vip 
        FROM evento
        WHERE id = ?
    """;

        List<Evento> eventos = jdbcTemplate.query(sql, (rs, rowNum) ->
                        Evento.builder()
                                .id(rs.getInt("id"))
                                .nombre(rs.getString("nombre"))
                                .descripcion(rs.getString("descripcion"))
                                .fechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime())
                                .lugar(rs.getString("lugar"))
                                .precioBase(rs.getBigDecimal("precio_base"))
                                .recargoGrada(rs.getBigDecimal("recargo_grada"))
                                .recargoVip(rs.getBigDecimal("recargo_vip"))
                                .build(),
                id
        );

        return eventos.isEmpty() ? null : eventos.get(0);
    }

    // --- COMPRAS DE ENTRADAS ---

    public void guardarCompra(CompraEntrada compra) {
        log.info("Guardando compra de entradas: {}", compra);

        String sql = """
            INSERT INTO compra_entrada (
                evento_id, nombre_comprador, email_comprador, zona, numero_entradas,
                precio_unitario, precio_total, fecha_compra
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        jdbcTemplate.update(sql,
                compra.getEventoId(),
                compra.getNombreComprador(),
                compra.getEmailComprador(),
                compra.getZona(),
                compra.getNumeroEntradas(),
                compra.getPrecioUnitario(),
                compra.getPrecioTotal(),
                java.sql.Timestamp.valueOf(compra.getFechaCompra())
        );
    }
}
