package enumerados;

public enum Licencia {
    GPL("GPL"),
    LGPL("LGPL"),
    MIT("MIT"),
    APACHE("Apache 2.0"),
    BSD("BSD"),
    MPL("Mozilla Public License"),
    COMERCIAL("Comercial"),
    PROPIETARIA("Propietaria");

    private String valor;

    Licencia(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor;
    }
}