package Com.ConversorDeMonedas;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ManejoApiExchange {

    public String obtenerMoneda(String monedaBase, String monedaObjetivo, BigDecimal valorMoneda){
        String direccion = "https://v6.exchangerate-api.com/v6/5732af32bce82b5173b421ee/pair/"+monedaBase+"/"+monedaObjetivo+"/"+valorMoneda;
        try{
            HttpClient cliente = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();

            HttpResponse<String> response = cliente
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();


        }catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }





}
