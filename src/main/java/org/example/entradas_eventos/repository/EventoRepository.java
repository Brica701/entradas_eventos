package org.example.entradas_eventos.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.entradas_eventos.model.CompraEntrada;
import org.example.entradas_eventos.model.Evento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
@Slf4j
@Repository
public class EventoRepository {

    JdbcTemplate jdbcTemplate;

    public EventoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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

    public List<Evento> findAll() {
        List<Evento> eventos = jdbcTemplate.query("""
                select * from evento;
                """,
                (rs, rowNum) -> new Evento(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        //rs.getObject("fecha", LocalDateTime.class)
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getString("lugar"),
                        rs.getBigDecimal("precio_base"),
                        rs.getBigDecimal("recargo_grada"),
                        rs.getBigDecimal("recargo_vip")
                )
        );
        return eventos;
    }
    /*
     * @param id
     * @return
     * @throws org.springframework.dao.DataAccessException si no se ecnuenta el id
     *
     * */
    public Evento findById(int id){
        return jdbcTemplate.queryForObject("""
                    select * from evento where id=?;
                   """,(rs, rowNum) -> Evento.builder()
                        .id(rs.getInt("id"))
                        .nombre(rs.getString("nombre"))
                        .descripcion(rs.getString("descripcion"))
                        .fecha(rs.getTimestamp("fecha").toLocalDateTime())
                        .lugar(rs.getString("lugar"))
                        .precioBase(rs.getBigDecimal("precio_base"))
                        .recargoGrada(rs.getBigDecimal("recargo_grada"))
                        .recargoVip(rs.getBigDecimal("recargo_vip"))
                        .build()
                ,id

        );
    }

    public void createEntrada(CompraEntrada compraEntrada){

        String sql = """
                insert into compra_entrada
                (evento_id, nombre_comprador, email_comprador, numero_entrada, precio_unitario, precio_total, fecha_compra)
                values (?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String ids [] = {"id"};

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, ids);
            ps.setInt(1,compraEntrada.getEventoId());
            ps.setString(2,compraEntrada.getNombreComprador());
            ps.setString(3,compraEntrada.getEmailComprador());
            ps.setInt(4,compraEntrada.getNumeroEntrada());
            ps.setBigDecimal(5,compraEntrada.getPrecioUnitario());
            ps.setBigDecimal(6,compraEntrada.getPrecioTotal());
            ps.setTimestamp(7, Timestamp.valueOf(compraEntrada.getFechaCompra()));
            return ps;
        },  keyHolder);
        compraEntrada.setId( keyHolder.getKey().intValue());
    }
}