package models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "nombre_cliente", length = 64)
    private String nombre_cliente;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "estado")
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "carta_id")
    private Carta carta;

    public Pedido() {
    }

    public Pedido(int id, String nombre_cliente, Date fecha, Boolean estado, Carta carta) {
        this.id = id;
        this.nombre_cliente = nombre_cliente;
        this.fecha = fecha;
        this.estado = estado;
        this.carta = carta;
    }

    public Carta getCarta() {
        return carta;
    }

    public void setCarta(Carta carta) {
        this.carta = carta;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nombre_cliente = " + nombre_cliente + ", " +
                "fecha = " + fecha + ", " +
                "estado = " + estado + ", " +
                "carta = " + carta + ")";
    }
}
