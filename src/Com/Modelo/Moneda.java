package Com.Modelo;

public class Moneda {
    String resultado;
    String monedaBase;
    String monedaObjetivo;
    String valorMoneda;
    String resultadoDeConversion;

    public Moneda(MonedaRecord monedaRecord){
        this.resultado = monedaRecord.result();
        this.monedaBase = monedaRecord.baseCode();
        this.monedaObjetivo = monedaRecord.targetCode();
        this.valorMoneda = monedaRecord.conversionRate();
        this.resultadoDeConversion = monedaRecord.conversionResult();
    }

    public String getValorMoneda() {
        return resultadoDeConversion;
    }
}
