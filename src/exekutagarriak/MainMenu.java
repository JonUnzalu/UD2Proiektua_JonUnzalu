/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exekutagarriak;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.sql.*;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import model.MediaType;
import model.Track;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

public class MainMenu {

    public static SessionFactory sf = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        boolean b = true;
        
        TimeUnit.SECONDS.sleep(1);

        System.out.println("------------------------------------------------------------------------");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>ZER EGIN NAHI DUZU<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        System.out.println("(1) Abesti lista osoa ikusi");
        System.out.println("(2) Media type lista osoa ikusi");
        System.out.println("(3) Abesti berri bat sortu (datu batzuk automatikoki sortuko dira)");
        System.out.println("(4) Media type berri bat sartu");
        System.out.println("(5) Media type bat aldatu");
        System.out.println("(6) Abesti bat ezabatu");
        System.out.println("(7) Media type bat ezabatu");
        System.out.println("(8) Media type bakoitzak dauzkan abestiak ikusi");
        System.out.println("(9) Sartutako letratik hasten diren abestiak ikusi");
        System.out.println("(0) Irten");
        System.out.println();

        while (b) {
            int aukera;
            try {
                System.out.print("Sartu zure aukera: ");
                aukera = in.nextInt();
                System.out.println("");

                switch (aukera) {
                    case 1:
                        abestienListaIkusi();
                        break;

                    case 2:
                        mediaTypeIkusi();
                        break;

                    case 3:
                        abestiaSortu();
                        break;

                    case 4:

                        mediaTypeSortu();
                        break;

                    case 5:
                        mediaTypeAaldatu();
                        break;

                    case 6:
                        System.out.print("Ezabatu nahi duzun abestiaren id sartu: ");
                        int abestiaBorratzeko = in.nextInt();
                        abestiaEzabatu(abestiaBorratzeko);
                        break;

                    case 7:
                        System.out.print("Ezabatu nahi duzun media type id sartu: ");
                        int mediaBorratzeko = in.nextInt();
                        mediaTypeEzabatu(mediaBorratzeko);
                        break;

                    case 8:
                        abestiakMediaBakoitzakoIkusi();
                        break;

                    case 9:
                        abestiakLetratik();
                        break;

                    case 0:
                        System.out.println("Agur!");
                        b = false;
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Mesedez sartu baliozko zenbaki bat");
                        break;
                }

            } catch (InputMismatchException ime) {
                System.out.println("Mesedez sartu zenbaki bat");
                in.next();
            }

            System.out.println("\n \n");
            System.out.println("------------------------------------------------------------------------");
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>ZER EGIN NAHI DUZU<<<<<<<<<<<<<<<<<<<<<<<<<<<");

            System.out.println("(1) Abesti lista osoa ikusi");
            System.out.println("(2) Media type lista osoa ikusi");
            System.out.println("(3) Abesti berri bat sortu (datu batzuk automatikoki sortuko dira)");
            System.out.println("(4) Media type berri bat sartu");
            System.out.println("(5) Media type bat aldatu");
            System.out.println("(6) Abesti bat ezabatu");
            System.out.println("(7) Media type bat ezabatu");
            System.out.println("(8) Media type bakoitzak dauzkan abestiak ikusi");
            System.out.println("(9) Sartutako letratik hasten diren abestiak ikusi");
            System.out.println("(0) Irten");
        }

        in.close();

    }

    private static Connection connect() {
        String url = "jdbc:mysql://localhost/chinook";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, "root", "root");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "- " + e.getErrorCode());
        }

        return conn;
    }

    private static void mediaTypeSortu() throws InterruptedException {
        Session saioa = null;
        try {
            Scanner in = new Scanner(System.in);
            MediaType m = new MediaType();

            System.out.print("Sartu MediaType berriaren izena: ");
            String izena = in.nextLine();
            m.setName(izena);

            saioa = sf.openSession();
            saioa.beginTransaction();
            saioa.save(m);

            saioa.getTransaction().commit();
            System.out.println("Mediatype berria ondo gorde da");
        } catch (InputMismatchException ime) {
            System.out.println("Ezin izan da MediaType sortu, mesedez hurrengorako eskatzen den datu mota sartu");
        } finally {
            saioa.close();
            TimeUnit.SECONDS.sleep(1);
        }
    }

    private static void mediaTypeAaldatu() throws InterruptedException {
        Session saioa = null;
        try {
            Scanner in = new Scanner(System.in);
            MediaType m = new MediaType();

            System.out.print("Sartu nola deituko duzun MediaType berria: ");
            String izena = in.nextLine();
            m.setName(izena);

            System.out.print("Sartu aldatuko duzun MediaTypen Id: ");
            int id = in.nextInt();
            m.setMediatypeid(id);

            saioa = sf.openSession();
            saioa.beginTransaction();
            saioa.update(m);

            saioa.getTransaction().commit();
            System.out.println("Mediatypea ondo aldatu da");
        } catch (InputMismatchException ime) {
            System.out.println("Ezin izan da MediaType aldatu, mesedez hurrengorako eskatzen den datu mota sartu");
        } catch (PersistenceException ce) {
            System.out.println("Ezin izan da MediaType aldatu, mesedez hurrengorako existitzen ID bat sartu");
        } finally {
            saioa.close();
            TimeUnit.SECONDS.sleep(1);
        }
    }

    private static void mediaTypeIkusi() {

        Session saioa = sf.openSession();
        saioa.beginTransaction();

        System.out.println("---------------------------------------------");
        System.out.printf("%14s %30s", "MEDIATYPE ID", "NAME");
        System.out.println("\n---------------------------------------------");

        List result = saioa.createQuery("from MediaType").list(); // HQL deitzen dan lengoaia idatziko dugu Querya
        for (MediaType mt : (List<MediaType>) result) {
            System.out.format("%14s %30s",
                    mt.getMediatypeid(), mt.getName());
            System.out.println();
        }
        saioa.getTransaction().commit();
        saioa.close();
    }

    private static void mediaTypeEzabatu(int mediaID) {
        Session saioa = null;
        MediaType ik = null;
        Transaction tx = null;

        try {
            saioa = sf.openSession();
            tx = saioa.beginTransaction();
            ik = (MediaType) saioa.load(MediaType.class, mediaID);
            saioa.delete(ik);
            tx.commit();
            System.out.println("Aukeratu duzun media type ezabatu da");
        } catch (EntityNotFoundException onfe) {
            System.out.println("Ez dago ID hori duen Mediarik");
        } catch (HibernateException e) {
            System.out.println("Hibernate errore bat gertatu da");
        } finally {
            saioa.close();
        }
    }

    private static void abestienListaIkusi() {
        Session saioa = sf.openSession();
        saioa.beginTransaction();

        System.out.println("-----------------------------------------------------------------------------------"
                + "------------------------------------------------");
        System.out.printf("%9s %14s %105s", "TRACK ID", "MEDIATYPE ID", "NAME");
        System.out.println("\n---------------------------------------------------------------------------------"
                + "--------------------------------------------------");

        List result = saioa.createQuery("from Track").list(); // HQL deitzen dan lengoaia idatziko dugu Querya
        for (Track t : (List<Track>) result) {
            System.out.format("%9s %14s %105s",
                    t.getTrackid(), t.getMediatypeid(), t.getName());
            System.out.println();
        }
        saioa.getTransaction().commit();
        saioa.close();
    }

    private static void abestiaSortu() throws InterruptedException {

        Session saioa = null;
        try {
            Scanner in = new Scanner(System.in);
            Track t = new Track();
            t.setAlbumid(6);
            t.setGenreid(3);
            t.setComposer("Vivaldi");
            t.setMilliseconds(343719);
            t.setBytes(4331779);
            t.setUnitprice(0.99);

            System.out.print("Sartu abestiaren izena: ");
            String izena = in.nextLine();
            t.setName(izena);

            System.out.print("Sartu mediaTypen Id: ");
            int ida = in.nextInt();
            t.setMediatypeid(ida);

            System.out.println("");

            saioa = sf.openSession();
            saioa.beginTransaction();
            saioa.save(t);
            saioa.getTransaction().commit();
            System.out.println("Abestia ondo gehitu da");
        } catch (InputMismatchException ime) {
            System.out.println("Mesedez hurrengorako eskatzen den datu mota sartu");
        } catch (PersistenceException ce) {
            System.out.println("Ezin izan da abestia sortu, mesedez hurrengorako existitzen den media ID bat sartu");
        } finally {
            saioa.close();
            TimeUnit.SECONDS.sleep(1);
        }
    }

    private static void abestiaEzabatu(int abestiaID) {
        Session saioa = null;
        Track ik = null;
        Transaction tx = null;

        try {
            saioa = sf.openSession();
            tx = saioa.beginTransaction();
            ik = (Track) saioa.load(Track.class, abestiaID);
            saioa.delete(ik);
            tx.commit();
            System.out.println("Aukeratu duzun abestia ezabatu da");
        } catch (HibernateException e) {
            System.out.println("Hibernate errore bat gertatu da");
        } catch (EntityNotFoundException ene) {
            System.out.println("Ez dago ID hori duen abestirik");
        } finally {
            saioa.close();
        }
    }

    private static void abestiakMediaBakoitzakoIkusi() {
        Scanner in = new Scanner(System.in);

        String table = "track";
        String table2 = "mediatype";
        String column = "MediaTypeId";

        System.out.print("Sartu mediaren ID, bere abestiak ikusteko: ");
        int zein = in.nextInt();
        String sql2 = "SELECT * FROM " + table + " INNER JOIN " + table2 + " ON " + table + "." + column + " = " + table2 + "." + column + " WHERE " + table2 + "." + column + " = " + zein + ";";
        
        String sql = "SELECT * FROM " + table + " WHERE MediaTypeId = " + zein + ";";
        //    String sql2 ="SELECT * FROM Customers WHERE (CustomerName LIKE 'A%') AND (Country = 'Mexico');";
        try ( Connection conn = connect();  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql2)) {
            if (rs.next() == true) {
                System.out.println("-----------------------------------------------------------------------------------"
                        + "----------------------------------------------------------------");
                System.out.printf("%9s %30s %105s", "TRACK ID", "MEDIATYPE NAME", " TRACK NAME");
                System.out.println("\n---------------------------------------------------------------------------------"
                        + "------------------------------------------------------------------");

                while (rs.next()) {
                    System.out.format("%9s %30s %105s", rs.getInt("track.TrackId"), rs.getString("mediatype.Name"), rs.getString("track.Name"));
                    System.out.println();
                }
            } else{
                System.out.println("Sartu duzun mediaType ez dauzka abestirik edo ez da existitzen");
            }
        }catch(InputMismatchException ime){
            System.out.println("Eskatzen den datu mota sartu mesedez");
        }catch(SQLException sqe){
            
        }

    }

    private static void abestiakLetratik() {
        Scanner in = new Scanner(System.in);

        String table = "track";
        String column = "Name";

        System.out.print("Sartu zein letratik hasten diren abestiak ikusi nahi dituzu: ");
        char zein = in.next().toUpperCase().charAt(0);

        String sql ="SELECT * FROM Track WHERE Name LIKE '" + zein + "%';";
        
        try ( Connection conn = connect();  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next() == true) {
                System.out.println("-----------------------------------------------------------------------------------"
                        + "------------------------------------------------");
                System.out.printf("%9s %14s %105s", "TRACK ID", "MEDIATYPE ID", "NAME");
                System.out.println("\n---------------------------------------------------------------------------------"
                        + "--------------------------------------------------");

                while (rs.next()) {
                    System.out.format("%9s %14s %105s", rs.getInt("TrackId"), rs.getString("MediaTypeId"), rs.getString("Name"));
                    System.out.println();
                }
            } else{
                System.out.println("Sartu duzun letra ez dauzka abestirik edo ez da letra bat");
            }
        }catch(InputMismatchException ime){
            System.out.println("Eskatzen den datu mota sartu mesedez");
        }catch(SQLException sqe){
            
        }

    }

}
