package org.example.entradas_eventos.repository;

import org.example.entradas_eventos.model.CompraEntrada;
import org.example.entradas_eventos.model.Evento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class EventoRepository {

    private final JdbcTemplate jdbcTemplate;

    public EventoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Evento> findAll() {
        return jdbcTemplate.query("SELECT * FROM evento", new RowMapper<Evento>() {
            @Override
            public Evento mapRow(ResultSet rs, int rowNum) throws SQLException {
                Evento e = new Evento();
                e.setId(rs.getInt("id"));
                e.setNombre(rs.getString("nombre"));
                e.setDescripcion(rs.getString("descripcion"));
                e.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                e.setLugar(rs.getString("lugar"));
                e.setPrecioBase(rs.getBigDecimal("precio_base"));
                e.setRecargoGrada(rs.getBigDecimal("recargo_grada"));
                e.setRecargoVip(rs.getBigDecimal("recargo_vip"));
                return e;
            }
        });
    }

    public Evento findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM evento WHERE id = ?",
                (rs, rowNum) -> {
                    Evento e = new Evento();
                    e.setId(rs.getInt("id"));
                    e.setNombre(rs.getString("nombre"));
                    e.setDescripcion(rs.getString("descripcion"));
                    e.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                    e.setLugar(rs.getString("lugar"));
                    e.setPrecioBase(rs.getBigDecimal("precio_base"));
                    e.setRecargoGrada(rs.getBigDecimal("recargo_grada"));
                    e.setRecargoVip(rs.getBigDecimal("recargo_vip"));
                    return e;
                }, id);
    }

    public void createCompra(CompraEntrada c) {
        jdbcTemplate.update(
                "INSERT INTO compra_entrada (evento_id, nombre_comprador, email_comprador, zona, numero_entrada, precio_unitario, precio_total, fecha_compra) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                c.getEventoId(), c.getNombreComprador(), c.getEmailComprador(), c.getZona(),
                c.getNumeroEntradas(), c.getPrecioUnitario(), c.getPrecioTotal(), c.getFechaCompra()
        );
    }

    //AdminController
    public void createEvento(Evento evento) {
        String sql = """
                insert into evento(nombre, descripcion, fecha, lugar, precio_base, 
                          recargo_grada, recargo_vip)
                          values(?, ?, ?, ?, ?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String [] ids = {"id"};

        jdbcTemplate.update(con -> {
                    PreparedStatement pr = con.prepareStatement(sql, ids);
                    pr.setString(1, evento.getNombre());
                    pr.setString(2, evento.getDescripcion());
                    pr.setTimestamp(3, Timestamp.valueOf(evento.getFecha()));
                    pr.setString(4, evento.getLugar());
                    pr.setBigDecimal(5, evento.getPrecioBase());
                    pr.setBigDecimal(6, evento.getRecargoGrada());
                    pr.setBigDecimal(7, evento.getRecargoVip());
                    return pr;
                }
                ,keyHolder);
        evento.setId(keyHolder.getKey().intValue());
    }
    public void eliminarEvento(int eventoId) {

        int rows = jdbcTemplate.update( """
                DELETE FROM compra_entrada WHERE evento_id = ?
                """, eventoId);
        int rowUpdate = jdbcTemplate.update("""
            DELETE FROM evento WHERE id = ?
        """, eventoId);
    }
    public void actualizarEvento(Evento evento) {
        var update = jdbcTemplate.update("""
        update evento set nombre=?, descripcion=?, fecha=?, lugar=?, precio_base=?, 
                          recargo_grada=?, recargo_vip=?
        where id = ?;
        
        """,
                evento.getNombre(),
                evento.getDescripcion(),
                Timestamp.valueOf(evento.getFecha()),
                evento.getLugar(),
                evento.getPrecioBase(),
                evento.getRecargoGrada(),
                evento.getRecargoVip(),
                evento.getId());
    }
}
