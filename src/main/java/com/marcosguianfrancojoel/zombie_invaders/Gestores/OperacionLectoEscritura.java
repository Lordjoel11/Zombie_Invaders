package com.marcosguianfrancojoel.zombie_invaders.Gestores;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class OperacionLectoEscritura {

    public static void createFile(String filename)
    {
        File file = new File(filename);
        try {

            PrintWriter salida = new PrintWriter(file);

            salida.close();

        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        System.out.printf("Se creo el archivo correctamente.");
    }


    public static void overwriteFile (String fileName, String content)
    {
        File file = new File(fileName);

        try {
            PrintWriter salida = new PrintWriter(file);
            salida.println(content);

            salida.close();
            System.out.printf("Archivo reescrito correctamente.");
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void addwriteFile (String fileName, String content)
    {
        File file = new File(fileName);

        try{
            PrintWriter salida = new PrintWriter(new FileWriter(file, true));

            salida.println(content);
            salida.close();
            System.out.printf("Se agrego contenido al archivo.");
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void readFile(String fileName){
        File file = new File(fileName);

        try {

            BufferedReader entrada = new BufferedReader(new FileReader(file));
            String lineaActual = null;

            while ((lineaActual = entrada.readLine()) != null)
            {
                System.out.println(lineaActual);
            }
            entrada.close();
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFileToString(String fileName){
        File file = new File(fileName);

        try {

            BufferedReader entrada = new BufferedReader(new FileReader(file));
            String lineaActual = null;

            while ((lineaActual = entrada.readLine()) != null)
            {
                System.out.println(lineaActual);
            }
            entrada.close();
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file.toString();
    }

    public static void grabarJSON(String nombreArchivo, JSONArray jsonArray)
    {
        try {
            FileWriter fileWriter = new FileWriter(nombreArchivo);
            fileWriter.write(jsonArray.toString(4));
            fileWriter.close();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
