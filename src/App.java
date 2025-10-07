import java.nio.file.*;

public class App {
    public static void main(String[] args) {
        try {
            // Asegurar carpeta db
            Path db = Paths.get("db");
            if (!Files.exists(db)) Files.createDirectory(db);

            SistemaCitas sistema = new SistemaCitas();
            sistema.cargarDatos();      // carga CSV si existen (si no, los crea)
            sistema.loopPrincipal();    // login + menú
        } catch (Exception e) {
            System.out.println("Error al iniciar: " + e.getMessage());
        }
    }
}
