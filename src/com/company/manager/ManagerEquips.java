package com.company.manager;

import com.company.model.Equip;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;

public class ManagerEquips{
    static Equip[] equips = new Equip[100];
    static int MAXNOM = 12;
    static int MAXID = 4;

    public static Equip inscriureEquip(String nom) {

        try {
            FileChannel fc = FileChannel.open(FileSystems.getDefault().getPath("equips.txt"),CREATE, WRITE);
            long length = fc.size();
            fc.position(length);
            fc.write(ByteBuffer.wrap(nom.getBytes()));
            fc.position(length + MAXNOM);

            ByteBuffer byteBuffer = ByteBuffer.allocate(MAXID);
            byteBuffer.putInt(0,(int) (length/ (MAXID + MAXNOM)) + 1);
            fc.write(byteBuffer);
            fc.position(length+MAXNOM+MAXID);
            fc.close();
            Equip equip = new Equip(nom);
            equip.id= (int) (length/ (MAXID+MAXNOM)) +1;
            return equip;

        } catch (IOException x) {
            System.out.println("I/O Exception : " + x);
        }
        return null;
    }

//---------------------------------------------------------------------------------------------------------------------------------------

    public static Equip obtenirEquip(int id){
        try {
            FileChannel fc = FileChannel.open(FileSystems.getDefault().getPath("equips.txt"),CREATE, READ);
            long posicion = (MAXNOM + MAXID) * (id - 1);

                fc.position(posicion);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(MAXNOM);
                    fc.read(byteBuffer);
                    String nom = new String(byteBuffer.array(), Charset.forName("UTF-8"));
                    Equip equip = new Equip(nom);
                    equip.id = id;
                    fc.close();
                    return equip;

        } catch (IOException x) {
            System.out.println("I/O Exception : " + x);
        }
        return null;
    }


//---------------------------------------------------------------------------------------------------------------------------------------


    public static Equip obtenirEquip(String nom){
        try {
            FileChannel fc = FileChannel.open(FileSystems.getDefault().getPath("equips.txt"),CREATE, READ);
            long length = fc.size();
            long posicion = 0;

            while(posicion < length) {
                fc.position(posicion);

                ByteBuffer byteBuffer = ByteBuffer.allocate(MAXNOM);
                fc.read(byteBuffer);
                String nombreLeido = new String(byteBuffer.array(), Charset.forName("UTF-8"));
                int longitud = nom.length();
                String comparacion = nombreLeido.substring(0,longitud);

                if(comparacion.toLowerCase().equals(nom.toLowerCase())){

                    ByteBuffer byteBuffer2 = ByteBuffer.allocate(MAXID);
                    fc.position(posicion+MAXNOM);
                    fc.read(byteBuffer2);
                    int idLeido = byteBuffer2.getInt(0);
                    Equip equip = new Equip(nom);
                    equip.id = idLeido;
                    fc.close();
                    return equip;
                }
                posicion += MAXNOM+MAXID;
            }

        } catch (IOException x) {
            System.out.println("I/O Exception : " + x);
        }
        return null;

    }


    //---------------------------------------------------------------------------------------------------------------------------------------
    public static String obtenirNomEquip(int id){
        try {
            FileChannel fc = FileChannel.open(FileSystems.getDefault().getPath("equips.txt"),CREATE, READ);

            long posicion = (MAXNOM + MAXID) * (id - 1);

            fc.position(posicion);
            ByteBuffer byteBuffer = ByteBuffer.allocate(MAXNOM);
            fc.read(byteBuffer);
            String nom = new String(byteBuffer.array(), Charset.forName("UTF-8"));
            fc.close();

            return nom;

        } catch (IOException x) {
            System.out.println("I/O Exception : " + x);
        }
        return null;
    }


//---------------------------------------------------------------------------------------------------------------------------------------

    public static Equip[] obtenirLlistaEquips(){
        Equip[] llistaEquips = new Equip[obtenirNumeroEquips()];
        int contador = 0;
        long posicion = 0;

        try {
            FileChannel fc = FileChannel.open(FileSystems.getDefault().getPath("equips.txt"),CREATE, READ);
            long length = fc.size();
            while(posicion < length) {
                fc.position(posicion);
                ByteBuffer byteBuffer = ByteBuffer.allocate(MAXNOM);
                fc.read(byteBuffer);
                String nombreLeido = new String(byteBuffer.array(), Charset.forName("UTF-8"));

                if(nombreLeido.charAt(0)!=' ') {

                    ByteBuffer byteBuffer2 = ByteBuffer.allocate(MAXID);
                    fc.position(posicion + MAXNOM);
                    fc.read(byteBuffer2);
                    int idLeido = byteBuffer2.getInt(0);
                    Equip equip = new Equip(nombreLeido);
                    equip.id = idLeido;
                    llistaEquips[contador] = equip;
                    contador++;
                }

                posicion += MAXNOM+MAXID;
            }

            fc.close();
        } catch (IOException x) {
            System.out.println("I/O Exception : " + x);
        }

        return llistaEquips;
    }


//---------------------------------------------------------------------------------------------------------------------------------------


    public static Equip[] buscarEquipsPerNom(String nom){
        Equip[] llistaEquips = new Equip[obtenirNumeroEquipsPerNom(nom)];

        int posicion = 0;
        int contador =0;
        try {
            FileChannel fc = FileChannel.open(FileSystems.getDefault().getPath("equips.txt"),CREATE, READ);
            long length = fc.size();
            while(posicion < length) {
                fc.position(posicion);
                ByteBuffer byteBuffer = ByteBuffer.allocate(MAXNOM);
                fc.read(byteBuffer);
                String nombreLeido = new String(byteBuffer.array(), Charset.forName("UTF-8"));
                String comparacion = nombreLeido.substring(0, nombreLeido.length());

                if(comparacion.contains(nom)){

                    ByteBuffer byteBuffer2 = ByteBuffer.allocate(MAXID);
                    fc.position(posicion+MAXNOM);
                    fc.read(byteBuffer2);
                    int idLeido = byteBuffer2.getInt(0);
                    Equip equip = new Equip(obtenirNomEquip(idLeido));
                    equip.id = idLeido;
                    fc.close();
                    llistaEquips[contador] =equip;
                    contador++;
                }
                posicion += MAXNOM+MAXID;
            }
        } catch (IOException x) {
            System.out.println("I/O Exception : " + x);
        }
        return llistaEquips;
    }


//---------------------------------------------------------------------------------------------------------------------------------------


    public static boolean existeixEquip(String nom){
        int posicion = 0;
        try {
            FileChannel fc = FileChannel.open(FileSystems.getDefault().getPath("equips.txt"),CREATE, READ);
            long length = fc.size();

            while(posicion < length) {
                fc.position(posicion);
                ByteBuffer byteBuffer = ByteBuffer.allocate(MAXNOM);
                fc.read(byteBuffer);
                String nombreLeido = new String(byteBuffer.array(), Charset.forName("UTF-8"));
                int longitud = nom.length();
                String comparacion = nombreLeido.substring(0,longitud);

                if(comparacion.equals(nom)){
                    return true;
                }
                posicion += MAXNOM+MAXID;
            }
            fc.close();
        } catch (IOException x) {
            System.out.println("I/O Exception : " + x);
        }

        return false;
    }


//---------------------------------------------------------------------------------------------------------------------------------------


    public static void modificarNomEquip(int id, String nouNom) {
        long pos = (MAXNOM + MAXID) * (id - 1);
        String nombre;
        if (nouNom.length() > MAXNOM) {
            nombre = nouNom.substring(0, MAXNOM);
        } else {
            nombre = nouNom;
            for (int i = 0; i < MAXNOM - nouNom.length(); i++) {
                nombre += " ";
            }

        }

        try {
            FileChannel fc = FileChannel.open(FileSystems.getDefault().getPath("equips.txt"), READ, WRITE, CREATE);
            fc.position(pos);

            fc.write((ByteBuffer.wrap(nombre.getBytes())));
            fc.close();

        } catch (IOException x) {
            System.out.println("I/O Exception : " + x);
        }

    }

//---------------------------------------------------------------------------------------------------------------------------------------


    public static void esborrarEquip(int id){
        long pos = (MAXNOM + MAXID) * (id - 1);

        try {
            FileChannel fc = FileChannel.open(FileSystems.getDefault().getPath("equips.txt"), READ, WRITE, CREATE);
            fc.position(pos);
            String cambio = "";
            for (int i = 0; i < MAXID + MAXNOM; i++) {
                cambio += " ";
            }
            fc.write((ByteBuffer.wrap(cambio.getBytes())));
            fc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

//  ---------------------------------------------------------------------------------------------------------------------------------------


    public static int obtenirUltimIdEquip(){
        int maxId = 0;
        try {
            FileChannel fc = FileChannel.open(FileSystems.getDefault().getPath("equips.txt"), WRITE, CREATE);
            return (int) fc.size() / (MAXNOM + MAXID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return maxId;
    }


//---------------------------------------------------------------------------------------------------------------------------------------


    public static int obtenirNumeroEquips(){
        int contador =0;
        int posicion = 0;
        try {
            FileChannel fc = FileChannel.open(FileSystems.getDefault().getPath("equips.txt"),CREATE, READ);
            long length = fc.size();

            while(posicion < length) {
                fc.position(posicion);
                ByteBuffer byteBuffer = ByteBuffer.allocate(MAXNOM);
                fc.read(byteBuffer);
                String nombreLeido = new String(byteBuffer.array(), Charset.forName("UTF-8"));

                if(nombreLeido.charAt(0)!=' '){
                    contador++;
                }
                posicion += MAXNOM+MAXID;
            }
        } catch (IOException e) {
            System.out.println("I/O Exception : " + e);
        }

        return contador;
    }



// ---------------------------------------------------------------------------------------------------------------------------------------


    public static int obtenirNumeroEquipsPerNom(String nom){
        int contador =0;
        try {
            FileChannel fc = FileChannel.open(FileSystems.getDefault().getPath("equips.txt"),CREATE, READ);
            long length = fc.size();
            int posicion = 0;


            while(posicion < length) {
                fc.position(posicion);
                ByteBuffer byteBuffer = ByteBuffer.allocate(MAXNOM);
                fc.read(byteBuffer);
                String nombreLeido = new String(byteBuffer.array(), Charset.forName("UTF-8"));
                int longitud = nom.length();
                String comparacion = nombreLeido.substring(0, longitud);

                if(comparacion.contains(nom)){

                    contador++;
                }
                posicion += MAXNOM+MAXID;
            }
        } catch (IOException e) {
            System.out.println("I/O Exception : " + e);
        }

        return contador;
    }
}
