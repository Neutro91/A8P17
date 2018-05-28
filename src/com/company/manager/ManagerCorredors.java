package com.company.manager;

import com.company.model.Corredor;
import com.company.model.Equip;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ManagerCorredors {
    static Corredor[] corredors = new Corredor[100];


    public static Corredor inscriureCorredor(String nom, Equip equip) throws IOException {
        if(equip == null){
            return null;
        }

        FileWriter fileWriter = new FileWriter("corredors.txt", true);
        fileWriter.write(nom + ":");
        fileWriter.write(equip.id + ":");
        fileWriter.write((obtenirUltimIdCorredor()+1) + "\n");
        fileWriter.close();

        Corredor corredor = new Corredor(nom, equip.id);
        corredor.id = obtenirUltimIdCorredor() + 1;

        return corredor;

    }

    //---------------------------------------------------------------------------------------------------------------------------------------
    public static Corredor obtenirCorredor(int id) throws FileNotFoundException {
        try{
            BufferedReader fileReader = new BufferedReader(new FileReader("corredors.txt"));
            String lineaCorredor ;
            while ((lineaCorredor= fileReader.readLine()) != null ) {
                String[] partes = lineaCorredor.split(":");
                if (id == Integer.parseInt(partes[2])) {
                    Corredor corredor = new Corredor(partes[0],Integer.parseInt(partes[2]));
                    corredor.id=id;
                    fileReader.close();
                    return corredor;
                }
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();

        }catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //---------------------------------------------------------------------------------------------------------------------------------------
    public static Corredor[] obtenirLlistaCorredors(){
        Corredor[] llistaCorredors = new Corredor[obtenirNumeroCorredors()];

        int i =0;
        try{
            BufferedReader fileReader = new BufferedReader(new FileReader("corredors.txt"));
            String lineaCorredor ;

            while ((lineaCorredor=fileReader.readLine()) != null ) {
                String[] partes = lineaCorredor.split(":");
                Corredor corredor = new Corredor(partes[0], Integer.parseInt(partes[1]));
                corredor.id = Integer.parseInt(partes[2]);
                llistaCorredors[i] = corredor;
                i++;
            }


        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }

        return llistaCorredors;
    }

//---------------------------------------------------------------------------------------------------------------------------------------

    public static Corredor[] buscarCorredorsPerNom(String nom){
        Corredor[] llistaCorredors = new Corredor[obtenirNumeroCorredorsPerNom(nom)];
        int i = 0;
        try{
            BufferedReader fileReader = new BufferedReader(new FileReader("corredors.txt"));
            String lineaCorredor ;

            while ((lineaCorredor=fileReader.readLine()) != null ) {
                String[] partes = lineaCorredor.split(":");
                if(partes[0].toLowerCase().contains(nom.toLowerCase())){
                    Corredor corredor = new Corredor(partes[0], Integer.parseInt(partes[1]));
                    corredor.id = Integer.parseInt(partes[2]);
                    llistaCorredors[i] = corredor;
                    i++;
                }

            }


        }catch(IOException e) {
            e.printStackTrace();
        }


        return llistaCorredors;

    }

    //---------------------------------------------------------------------------------------------------------------------------------------
    public static boolean existeixCorredor(String nom){
        try{
            BufferedReader fileReader = new BufferedReader(new FileReader("corredors.txt"));
            String lineaCorredor ;
            while ((lineaCorredor= fileReader.readLine()) != null ) {
                String[] partes = lineaCorredor.split(":");

                if (nom.toLowerCase().equals(partes[0].toLowerCase())) {
                    return true;
                }
            }
            fileReader.close();

        }catch(IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    //---------------------------------------------------------------------------------------------------------------------------------------
    public static void modificarNomCorredor(int id, String nouNom){
        try{

            BufferedReader fileReader = new BufferedReader(new FileReader("corredors.txt"));
            FileWriter fileWriter = new FileWriter("corredors2.txt", true);
            String lineaCorredor ;

            while ((lineaCorredor = fileReader.readLine()) != null ) {
                String[] partes = lineaCorredor.split(":");

                if (id == Integer.parseInt(partes[2])) {

                    fileWriter.write(nouNom+":");
                    fileWriter.write(partes[1]+ ":");
                    fileWriter.write(partes[2]+ "\n");

                    fileWriter.flush();

                } else{
                    fileWriter.write(partes[0]+":");
                    fileWriter.write(partes[1]+ ":");
                    fileWriter.write(partes[2]+ "\n");
                }

            }
            fileWriter.close();
            fileReader.close();

            Files.move(FileSystems.getDefault().getPath("corredors2.txt"),
                    FileSystems.getDefault().getPath("corredors.txt"),
                    REPLACE_EXISTING);
        }catch(FileNotFoundException e) {
            e.printStackTrace();

        }catch(IOException e) {
            e.printStackTrace();
        }


    }
//---------------------------------------------------------------------------------------------------------------------------------------

    public static void modificarEquipCorredor(int id, Equip nouEquip){
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("corredors.txt"));
            FileWriter fileWriter = new FileWriter("corredors2.txt", true);

            String linea;
            while((linea = fileReader.readLine()) != null){
                String[] partes = linea.split(":");
                if(Integer.parseInt(partes[2]) == id){
                    fileWriter.write(partes[0] +":");
                    fileWriter.write(nouEquip.id+ ":");
                    fileWriter.write(partes[2] + "\n");
                }else{
                    fileWriter.write(partes[0] + ":");
                    fileWriter.write(partes[1] + ":");
                    fileWriter.write(partes[2] + "\n");;
                }
            }
            fileWriter.close();
            fileReader.close();

            Files.move(FileSystems.getDefault().getPath("corredors2.txt"), FileSystems.getDefault().getPath("corredors.txt"), REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //---------------------------------------------------------------------------------------------------------------------------------------
    public static void esborrarCorredor(int id) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("corredors.txt"));
            FileWriter fileWriter = new FileWriter("corredors2.txt", true);

            String linea;
            while ((linea = fileReader.readLine()) != null) {
                String[] partes = linea.split(":");
                if (Integer.parseInt(partes[2]) != id) {
                    fileWriter.write(partes[0] + ":");
                    fileWriter.write(partes[1] + ":");
                    fileWriter.write(partes[2] + "\n");

                }
            }
            fileWriter.close();
            fileReader.close();

            Files.move(FileSystems.getDefault().getPath("corredors2.txt"), FileSystems.getDefault().getPath("corredors.txt"), REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//---------------------------------------------------------------------------------------------------------------------------------------

    public static int obtenirUltimIdCorredor(){

        int elmasgrande=0;

        try{
            BufferedReader fileReader = new BufferedReader(new FileReader("corredors.txt"));
            String lineaCorredor ;
            while ((lineaCorredor= fileReader.readLine()) != null ) {
                String[] partes = lineaCorredor.split(":");

                if (Integer.parseInt(partes[2]) > elmasgrande) {
                    elmasgrande = Integer.parseInt(partes[2]);
                }
            }
            fileReader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return elmasgrande;
    }
//---------------------------------------------------------------------------------------------------------------------------------------

    public static int obtenirNumeroCorredors() {

        int count = 0;
        String lineaCorredor;
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("corredors.txt"));
            while ((lineaCorredor = fileReader.readLine()) != null) {
                count++;

            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return count;

    }
//---------------------------------------------------------------------------------------------------------------------------------------

    public static int obtenirNumeroCorredorsPerNom(String nom){
        int count = 0;
        try{
            BufferedReader fileReader = new BufferedReader(new FileReader("corredors.txt"));
            String lineaCorredor ;

            while ((lineaCorredor=fileReader.readLine()) != null ) {
                String[] partes = lineaCorredor.split(":");
                if(partes[0].toLowerCase().contains(nom.toLowerCase())) {
                    count ++;
                }
            }
        }catch(IOException e) {
            e.printStackTrace();
        }


        return count;
    }
}
