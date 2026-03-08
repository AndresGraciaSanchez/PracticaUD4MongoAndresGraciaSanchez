package enumerados;

public enum Categoria {
    IDE("IDE / Entorno de Desarrollo"),
    EDITOR_CODIGO("Editor de Código"),
    CONTROL_VERSIONES("Control de Versiones"),
    FRAMEWORK("Framework"),
    LIBRERIA("Librería"),
    BASE_DATOS("Base de Datos"),
    DEVOPS("DevOps / CI-CD"),
    CONTENEDORES("Contenedores y Virtualización"),
    TESTING("Testing / Pruebas"),
    SEGURIDAD("Seguridad"),
    DOCUMENTACION("Documentación"),
    HERRAMIENTAS_BUILD("Herramientas de Build"),
    GESTION_DEPENDENCIAS("Gestión de Dependencias"),
    CLOUD("Cloud / Infraestructura");

    private String valor;

    Categoria(String valor) {
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