import models.Carta;
import models.Pedido;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class crudV3ObjectDb {

    private static EntityManagerFactory emf;
    static Date ahora = new Date();
    static java.sql.Date sqlFecha = new java.sql.Date(ahora.getTime());



    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("db.odb");
        menu();
    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("-----BIENVENIDO A SU GESTOR DE PEDIDOS-----");
        System.out.println("Esta es la carta del día:");
        listarCarta();
        System.out.println();
        System.out.println("¿Que desea realizar?");
        System.out.println("1. Crear pedido");
        System.out.println("2. Eliminar pedido");
        System.out.println("3. Marcar como recogido");
        System.out.println("4. Mostrar pedidos pendientes de hoy");
        System.out.println("5. Mostrar carta");
        System.out.println("6. Salir");

        switch (sc.nextInt()) {
            case 1 -> {
                crearPedido();
                menu();
            }
            case 2 -> {
                eliminarPedidoExt();
                menu();
            }
            case 3 -> {
                marcarRecogido();
                menu();
            }
            case 4 -> {
                comandasPendientesHoy();
                menu();
            }
            case 5 -> {
                listarCarta();
                menu();
            }
            default -> System.exit(0);
        }
    }

    private static void comandasPendientesHoy() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Pedido> q = em.createQuery("SELECT p FROM Pedido p where fecha=:fecha and estado=false", Pedido.class);
        q.setParameter("fecha", sqlFecha);
        var pedidos = new ArrayList<Pedido>();
        pedidos = (ArrayList<Pedido>) q.getResultList();
        pedidos.forEach(System.out::println);
        em.close();
    }
//No funciona
    private static void marcarRecogido() {
        Scanner sc = new Scanner(System.in);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT p FROM Pedido p where estado=false", Pedido.class);
        var pedidos = new ArrayList<Pedido>();
        pedidos = (ArrayList<Pedido>) q.getResultList();
        pedidos.forEach(System.out::println);
        System.out.println("¿Que pedido quieres marcar como recogido?");
        q = em.createQuery("SELECT p FROM Pedido p where id=:id", Pedido.class);

        int id = sc.nextInt();
        q.setParameter("id", id);
        Pedido p = (Pedido) q.getSingleResult();
        p.setEstado(true);
        em.persist(p);
        em.getTransaction().commit();
        em.close();
    }

    private static void eliminarPedidoExt() {
        Scanner sc = new Scanner(System.in);
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT p FROM Pedido p", Pedido.class);
        var pedidos = new ArrayList<Pedido>();
        pedidos = (ArrayList<Pedido>) q.getResultList();
        pedidos.forEach(System.out::println);
        System.out.println("¿Que pedido quieres eliminar?");
        em.getTransaction().begin();
        q = em.createQuery("DELETE FROM Pedido p where p.id=:id");
        int id = sc.nextInt();
        q.setParameter("id", id);
        q.executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    private static void crearPedido() {
        Scanner sc = new Scanner(System.in);
        Pedido p = new Pedido();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        System.out.println("Introduce un nombre para tu pedido:");
        p.setNombre_cliente(sc.nextLine());
        System.out.println();
        listarCarta();
        System.out.println("Introduce el ID del producto que desees:");
        Query q = em.createQuery("SELECT c FROM Carta c where id=:id");
        int id = sc.nextInt();
        q.setParameter("id", id);
        p.setCarta((Carta) q.getSingleResult());
        p.setFecha(sqlFecha);
        p.setEstado(false);
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        em.close();
    }

    private static void listarCarta() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Carta> q = em.createQuery("SELECT c FROM Carta c", Carta.class);
        var carta = new ArrayList<Carta>();
        carta = (ArrayList<Carta>) q.getResultList();
        carta.forEach(System.out::println);
        em.close();
    }
}
