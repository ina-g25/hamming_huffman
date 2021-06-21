/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hamming_huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 *
 */
public class Hamming {

    private static String nombre_archivo,extension_archivo,extension_nuevo_archivo;
  private static int cant_bytes_archivo, tamanio_bloque, cant_bits_control, cant_bits_info;
    
  public static void setConfiguracion(int i,String ext) {
        switch (i) {
            case 0: {
                tamanio_bloque = 16;
                break;
            }
            case 1: {
                tamanio_bloque = 2048;
                break;
            }
            case 2: {
                tamanio_bloque = 16384;
                break;
            }
        }
        extension_nuevo_archivo="."+ext + (i + 1);
        cant_bits_control = (int) (Math.log(tamanio_bloque) / Math.log(2));
        cant_bits_info = tamanio_bloque - cant_bits_control - 1;
    }
  
    public static void setNombre_y_extension_archivo(String nombre) {
        extension_archivo=nombre.substring(nombre.length()-4,nombre.length());
        nombre_archivo = nombre.substring(0, nombre.length() - 4);
    }
   
    public static String getExtension_archivo() {
        return extension_archivo;
    }

    public static String getNombre_archivo() {
        return nombre_archivo;
    }

    public static String getExtension_nuevo_archivo() {
        return extension_nuevo_archivo;
    }

    public static  int getTamanio_bloque() {
        return tamanio_bloque;
    }

    public static  void setTamanio_bloque(int tamanio) {
        tamanio_bloque = tamanio;
    }

    public static  int getCant_bits_control() {
        return cant_bits_control;
    }

    public static  void setCant_bits_control(int cant_bits) {
        cant_bits_control = cant_bits;
    }

    public static  int getCant_bits_info() {
        return cant_bits_info;
    }


    public static  int cantBitsInfo(int ham) {
        int bitsRed = (int) (Math.log(ham) / Math.log(2)); // creo que lo de floor ni hace falta
        return ham - bitsRed - 1;
    }

    public static boolean getBitDeByte(byte b, int pos) {
        return (1 & (b >> pos)) == 1;
    }
  
    public static byte[] toBytes(boolean[] arr) {
        byte[] bytes = new byte[arr.length / 8];
        for (int i = 0; i < bytes.length; i++) {
            for (int posBit = 0; posBit < 8; posBit++) {//por cada bit
                if (arr[i * 8 + posBit]) {//si el true, agrego el bit en la posicion que corresponde del byte
                    bytes[i] |= (128 >> posBit);//quiere decir byetes[i] = bytes[i] | (10000000 >> posBit)
                }
            }
        }
        return bytes;
    }

    public static String toString(boolean[] ar) {
        String aux = "";
        for (int i = 0; i < ar.length; i++) {
            aux += (ar[i]) ? '1' : '0';
        }
        return aux;
    }

    //getIntervaloBits
    public static boolean[] bytesDelBloqueABoolean(byte[] arr, int pos, int fin) {//transforma una porcion de los bytes del archivo a boolean  
        int posByte = 0, posBit = 0, bitActualRetorno = 0;
        boolean retorno[] = new boolean[fin - pos + 1];

        for (; pos <= fin; bitActualRetorno++, pos++) {
            posByte = (int) pos / 8;//calculo en que byte se halla esa posicion 
            posBit = (int) pos - (posByte * 8);//calculo en que bit del byte se encuentra esa posicion
            if (posByte >= arr.length) {
                break;
            }
            retorno[bitActualRetorno] = getBitDeByte(arr[posByte], 7 - posBit);
        }
        return retorno;
    }

    public static String generarHamming(byte[] bytesArchivo) throws FileNotFoundException, IOException {
        String lineaDeBits = "";
        int hasta = getCant_bits_info() - 1, desde = 0, i = 0;
        boolean[][] codificacion_hamming;
        int contador = (int) Math.ceil(bytesArchivo.length * 8.f / getCant_bits_info());
        codificacion_hamming = new boolean[contador][];//cuantos bloques en total voy a crear para proteger el texto
        while (i < contador) {
            boolean bitsInfo[] = Hamming.bytesDelBloqueABoolean(bytesArchivo, desde, hasta);//obteniene los bytes que entran en el bloque y los transforma a binario booleano
            codificacion_hamming[i] = Hamming.getBloqueHamming(bitsInfo, cant_bits_control, tamanio_bloque);//calcula el hamming, añade bits de info y control
            lineaDeBits += new String(Hamming.toBytes(codificacion_hamming[i]));  //añade lo que acaba de codificar

            desde = hasta + 1;
            hasta += getCant_bits_info();
            i++;
        }
        //guardar en archivo HAX
        File archivo2 = new File(nombre_archivo+extension_nuevo_archivo);//creo el nuevo archivo de extension HAX con x=1,2,3
        OutputStream out = new FileOutputStream(archivo2);
        for (boolean[] aux : codificacion_hamming) {
            out.write(Hamming.toBytes(aux));
        }
        out.close();
        return lineaDeBits;
    }

    public static boolean[] getBloqueHamming(boolean[] bitsInfo, int cant_bits_control, int tamanio_bloque) {
        boolean[] caractersBloque = new boolean[tamanio_bloque];//bitsDe controlposee tmaño 32, tamanio bloque total
        int j = 0;

        //Agrego los bits de informacion al bloque
        for (int i = 3; i < tamanio_bloque; i++) {//los 2 1ros bits  son de conntrol
            if ((i & i - 1) != 0) {//Determinar si la posicion es de control, multiplo de 2
                caractersBloque[i - 1] = bitsInfo[j];//pasa  los bits de informacion al bloque 
                j++;
            }
        }
        //calcular los bit de control
        for (int i = 0; i < cant_bits_control; i++) {
            int posBitControl = (int) Math.pow(2, i);//Obtengo la posicion del bit de control
            caractersBloque[posBitControl - 1] = false;//Inicializo la posicion de control con falso para hacer el XOR
            for (int pos = 1; pos < caractersBloque.length; pos++) {
                if (posBitControl != pos && (posBitControl & pos) != 0) {//Si la posicion actual debe ser controlada por el bit de control en posControl
                    caractersBloque[posBitControl - 1] ^= caractersBloque[pos - 1];//Pos control = Pos control xor bit actual
                }
            }
        }
        for (int i = 0; i < tamanio_bloque - 1; i++) {
            caractersBloque[tamanio_bloque - 1] ^= caractersBloque[i];//Calculo el utimo bit de paridad

        }
        //Hacer un xor con todos los bits anteriores para el ultimo bit de paridad
        return caractersBloque;//Retorna el arreglo hamminificado
    }

    public static boolean[] getInfo(boolean[] arregloBits, boolean arreglar) {
        int cantRed = getBitsRedDeHamming(arregloBits);
        boolean[] arrInfo = new boolean[arregloBits.length - cantRed];
        boolean[] aux;
        aux = arregloBits.clone();
        int j = -1;
        for (int i = 3; i < aux.length; i++) {
            if ((i & i - 1) != 0) {//Esto devuelve si no es potencia de 2
                j++;
                arrInfo[j] = aux[i - 1];
            }
        }
        return arrInfo;
    }

    public static int getBitsRedDeHamming(boolean[] hamm) {
        int cantRedundante = 1;
        while (Math.pow(2, cantRedundante) <= hamm.length) {
            cantRedundante++;
        }
        return cantRedundante;
    }
    /**
     * Recibe por parametro un arreglo de booleanos Retorna un string de 0 y 1
     * equivalente
     *
     * @param ar
     * @return
     */

}
