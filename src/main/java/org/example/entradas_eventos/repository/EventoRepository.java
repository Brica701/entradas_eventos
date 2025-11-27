package org.example.entradas_eventos.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.entradas_eventos.model.Evento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
@Slf4j
@Repository
public class EventoRepository {
    private JdbcTemplate jdbcTemplate;

    public EventoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Evento> getAll() {
        List<Evento> eventos = jdbcTemplate.query("""
                
                        Select * from evento
   
                    """,
                (rs, rowNum)-> new Evento(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        //rs.getTimestamp("fecha_hora").toLocalDateTime(),
                        rs.getObject("fecha_hora", LocalDateTime.class),
                        rs.getString("lugar"),
                        rs.getBigDecimal("precio_base"),
                        rs.getBigDecimal("recargo_grada"),
                        rs.getBigDecimal("recargo_vip"))
        );

        log.info("Devueltos {}",eventos);
        return eventos;
    }

    /**
     *
     * @param id
     * @return
     * @throws org.springframework.dao.DataAccessException de tipo runtime - si no se encuentra el id
     */
    public Evento findById(Long id) {

        return jdbcTemplate.queryForObject("""
                select * from evento where id = ?
                """, (rs, rowNum) -> Evento.builder()
                        .id(rs.getLong("id"))
                        .nombre(rs.getString("nombre"))
                        .descripcion(rs.getString("descripcion"))
                        .fecha_hora(rs.getObject("fecha_hora", LocalDateTime.class))
                        .lugar(rs.getString("lugar"))
                        .precio_base(rs.getBigDecimal("precio_base"))
                        .recargo_grada(rs.getBigDecimal("recargo_grada"))
                        .recargo_vip(rs.getBigDecimal("recargo_vip"))
                        .build()
                , id
        );



    }
}