package com.company;

import com.company.manager.ManagerCorredors;
import com.company.manager.ManagerEquips;
import com.company.model.Corredor;
import com.company.model.Equip;

import java.io.IOException;

public class MainTest {


    public static void main(String[] args) throws IOException {

        //ManagerCorredors2.inscriureCorredor("pepe",new Equip("CorredoresX"));
        //Corredor corredor =ManagerCorredors2.obtenirCorredor(1003);
        //System.out.println(corredor.id+" " + corredor.nom + 2 + " " + corredor.idEquip );

       // System.out.println(ManagerCorredors2.existeixCorredor("Manolo"));


        //System.out.println(ManagerCorredors2.obtenirUltimIdCorredor());

       // ManagerCorredors2.modificarNomCorredor(1005, "pepeLuis");

        //---------------------------------------------------------------------------------------

        //ManagerCorredors2.obtenirNumeroCorredors();

        //ManagerCorredors2.obtenirLlistaCorredors();
        //ManagerCorredors2.buscarCorredorsPerNom("Pepe");
        // Equip equip = ManagerEquips2.inscriureEquip("AAAA");
        System.out.println(ManagerEquips.obtenirNumeroEquips());

    }
}
