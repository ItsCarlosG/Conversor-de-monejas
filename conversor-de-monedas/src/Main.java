import Com.ConversorDeMonedas.ManejoApiExchange;
import Com.Modelo.DiccionarioDeMonedas;
import Com.Modelo.Moneda;
import Com.Modelo.MonedaRecord;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

import static java.text.MessageFormat.format;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String nombreMonedaBase, codigoMonedaBase;
        String nombreMonedaObjetivo, codigoMonedaObjetivo;
        BigDecimal valorMonedaBase, valorConversionMoneda;

        DiccionarioDeMonedas diccionario = new DiccionarioDeMonedas();
        HashMap<String, String> diccionarioMoneda = diccionario.getCurrencyMap();

        mostrarMenu(diccionarioMoneda);

        while(true){
            System.out.print("Convertir de: ");
            nombreMonedaBase = sc.nextLine();
            System.out.print("Convertir a: ");
            nombreMonedaObjetivo = sc.nextLine();

            boolean validacionMonedaBase, validacionMonedaObjetivo;
            validacionMonedaBase = verificarCodigoMoneda(nombreMonedaBase, diccionarioMoneda);
            validacionMonedaObjetivo = verificarCodigoMoneda(nombreMonedaObjetivo, diccionarioMoneda);

            if(validacionMonedaBase && validacionMonedaObjetivo){
                System.out.print("Ingrese el monto: ");
                valorMonedaBase = sc.nextBigDecimal();

                codigoMonedaBase = obtenerClavePorValor(diccionarioMoneda, nombreMonedaBase);
                codigoMonedaObjetivo = obtenerClavePorValor(diccionarioMoneda, nombreMonedaObjetivo);




                Gson gson = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .setPrettyPrinting()
                        .create();

                ManejoApiExchange manejoApi = new ManejoApiExchange();
                String jsonMoneda = manejoApi.obtenerMoneda(codigoMonedaBase, codigoMonedaObjetivo, valorMonedaBase);
                MonedaRecord monedaRecord = gson.fromJson(jsonMoneda, MonedaRecord.class);


                Moneda moneda = new Moneda(monedaRecord);
                valorConversionMoneda = new BigDecimal(moneda.getValorMoneda());
                System.out.println(format("El valor {0} {1} corresponde al valor final de => {2} {3} ",valorMonedaBase, codigoMonedaBase, valorConversionMoneda, codigoMonedaObjetivo ));
                sc.nextLine();
                System.out.println("Desea salir?(s/n)");
                String salida = sc.nextLine();
                if(salida.equalsIgnoreCase("s")){
                    break;
                }
            }else{
                System.out.println("Nombre de moneda incorrecto!");
            }
        }



    }


    public static String obtenerClavePorValor(HashMap<String, String> diccionario, String valor){
        for(HashMap.Entry<String, String> entry: diccionario.entrySet()){

            if(entry.getValue().equalsIgnoreCase(valor)){
                return entry.getKey();
            }
        }
        return null;
    }

    public static void mostrarMenu(HashMap<String, String> diccionarioMoneda){
        System.out.println("****CONVERSOR DE MONEDAS****");
        System.out.println("MONEDAS SOPORTADAS");

        for (String code : diccionarioMoneda.keySet()) {
            System.out.println(diccionarioMoneda.get(code));
        }
    }


    public static boolean verificarCodigoMoneda(String codigo, HashMap<String, String> monedas) {
        for (String valor : monedas.values()) {
            if (codigo.equalsIgnoreCase(valor)) {
                return true;
            }
        }
        return false;
    }



}