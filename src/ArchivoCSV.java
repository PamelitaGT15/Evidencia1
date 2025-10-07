import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ArchivoCSV {
    private static final Path DB_DIR = Paths.get("db");
    private static final Path DOCTORES = DB_DIR.resolve("doctores.csv");
    private static final Path PACIENTES = DB_DIR.resolve("pacientes.csv");
    private static final Path CITAS = DB_DIR.resolve("citas.csv");

    public static void asegurarArchivos() {
        try {
            if (!Files.exists(DB_DIR)) Files.createDirectories(DB_DIR);
            if (!Files.exists(DOCTORES)) Files.createFile(DOCTORES);
            if (!Files.exists(PACIENTES)) Files.createFile(PACIENTES);
            if (!Files.exists(CITAS)) Files.createFile(CITAS);
        } catch (IOException e) { System.out.println("No se pudo preparar db: " + e.getMessage()); }
    }

    // --- Guardar ---
    public static void guardarDoctores(List<Doctor> lista) {
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(DOCTORES))) {
            for (Doctor d : lista) pw.println(d.getId()+","+d.getNombre()+","+d.getEspecialidad());
        } catch (IOException e) { System.out.println("Error guardando doctores: " + e.getMessage()); }
    }

    public static void guardarPacientes(List<Paciente> lista) {
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(PACIENTES))) {
            for (Paciente p : lista) pw.println(p.getId()+","+p.getNombre());
        } catch (IOException e) { System.out.println("Error guardando pacientes: " + e.getMessage()); }
    }

    public static void guardarCitas(List<Cita> lista) {
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(CITAS))) {
            for (Cita c : lista)
                pw.println(c.getId()+","+c.getFechaHora()+","+c.getMotivo()+","+c.getDoctorId()+","+c.getPacienteId());
        } catch (IOException e) { System.out.println("Error guardando citas: " + e.getMessage()); }
    }

    // --- Cargar ---
    public static List<Doctor> cargarDoctores() {
        List<Doctor> out = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(DOCTORES)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] t = line.split(",", -1);
                if (t.length >= 3) out.add(new Doctor(t[0], t[1], t[2]));
            }
        } catch (IOException e) { System.out.println("Error cargando doctores: " + e.getMessage()); }
        return out;
    }

    public static List<Paciente> cargarPacientes() {
        List<Paciente> out = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(PACIENTES)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] t = line.split(",", -1);
                if (t.length >= 2) out.add(new Paciente(t[0], t[1]));
            }
        } catch (IOException e) { System.out.println("Error cargando pacientes: " + e.getMessage()); }
        return out;
    }

    public static List<Cita> cargarCitas() {
        List<Cita> out = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(CITAS)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] t = line.split(",", -1);
                if (t.length >= 5) out.add(new Cita(t[0], t[1], t[2], t[3], t[4]));
            }
        } catch (IOException e) { System.out.println("Error cargando citas: " + e.getMessage()); }
        return out;
    }
}
