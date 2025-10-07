import java.util.*;

public class SistemaCitas {
    private final List<Doctor> doctores   = new ArrayList<>();
    private final List<Paciente> pacientes= new ArrayList<>();
    private final List<Cita> citas        = new ArrayList<>();
    // Admin simple (demo). Puedes migrarlo a CSV si lo piden.
    private final Map<String,String> admins = Map.of("admin","1234");

    public void cargarDatos() {
        doctores.clear(); pacientes.clear(); citas.clear();
        ArchivoCSV.asegurarArchivos(); // crea CSV vacíos si faltan
        doctores.addAll(ArchivoCSV.cargarDoctores());
        pacientes.addAll(ArchivoCSV.cargarPacientes());
        citas.addAll(ArchivoCSV.cargarCitas());
    }

    private boolean login(Scanner sc) {
        System.out.println("=== Login de administrador ===");
        while (true) {
            System.out.print("Usuario: ");
            String u = sc.nextLine().trim();
            System.out.print("Contraseña: ");
            String p = sc.nextLine().trim();
            if (admins.containsKey(u) && admins.get(u).equals(p)) return true;
            System.out.println("Credenciales inválidas. Intente de nuevo.");
        }
    }

    public void loopPrincipal() {
        Scanner sc = new Scanner(System.in);
        if (!login(sc)) return;
        int op;
        do {
            mostrarMenu();
            try { op = Integer.parseInt(sc.nextLine().trim()); }
            catch (Exception e) { op = -1; }
            switch (op) {
                case 1 -> altaDoctor(sc);
                case 2 -> altaPaciente(sc);
                case 3 -> crearCita(sc);
                case 4 -> listarDoctores();
                case 5 -> listarPacientes();
                case 6 -> listarCitas();
                case 7 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 7);
    }

    private void mostrarMenu() {
        System.out.println("\n=== Menú principal ===");
        System.out.println("1) Alta doctor");
        System.out.println("2) Alta paciente");
        System.out.println("3) Crear cita");
        System.out.println("4) Listar doctores");
        System.out.println("5) Listar pacientes");
        System.out.println("6) Listar citas");
        System.out.println("7) Salir");
        System.out.print("Seleccione opción: ");
    }

    private boolean existeDoctor(String id) {
        return doctores.stream().anyMatch(d -> d.getId().equals(id));
    }

    private boolean existePaciente(String id) {
        return pacientes.stream().anyMatch(p -> p.getId().equals(id));
    }

    private boolean disponible(String doctorId, String fechaHora) {
        return citas.stream().noneMatch(c ->
                c.getDoctorId().equals(doctorId) && c.getFechaHora().equals(fechaHora));
    }

    private void altaDoctor(Scanner sc) {
        System.out.println("\n-- Alta de doctor --");
        System.out.print("ID: "); String id = sc.nextLine().trim();
        System.out.print("Nombre: "); String nom = sc.nextLine().trim();
        System.out.print("Especialidad: "); String esp = sc.nextLine().trim();

        if (id.isEmpty() || nom.isEmpty() || esp.isEmpty()) { System.out.println("Datos incompletos."); return; }
        if (existeDoctor(id)) { System.out.println("ID duplicado."); return; }

        Doctor d = new Doctor(id, nom, esp);
        doctores.add(d);
        ArchivoCSV.guardarDoctores(doctores);
        System.out.println("Doctor registrado.");
    }

    private void altaPaciente(Scanner sc) {
        System.out.println("\n-- Alta de paciente --");
        System.out.print("ID: "); String id = sc.nextLine().trim();
        System.out.print("Nombre: "); String nom = sc.nextLine().trim();

        if (id.isEmpty() || nom.isEmpty()) { System.out.println("Datos incompletos."); return; }
        if (existePaciente(id)) { System.out.println("ID duplicado."); return; }

        Paciente p = new Paciente(id, nom);
        pacientes.add(p);
        ArchivoCSV.guardarPacientes(pacientes);
        System.out.println("Paciente registrado.");
    }

    private void crearCita(Scanner sc) {
        System.out.println("\n-- Crear cita --");
        System.out.print("ID de cita: "); String id = sc.nextLine().trim();
        System.out.print("Fecha y hora (YYYY-MM-DD HH:MM): "); String fh = sc.nextLine().trim();
        System.out.print("Motivo: "); String mot = sc.nextLine().trim();
        System.out.print("ID doctor: "); String docId = sc.nextLine().trim();
        System.out.print("ID paciente: "); String pacId = sc.nextLine().trim();

        if (id.isEmpty() || fh.isEmpty() || mot.isEmpty() || docId.isEmpty() || pacId.isEmpty()) {
            System.out.println("Datos incompletos."); return;
        }
        if (!existeDoctor(docId)) { System.out.println("El doctor no existe."); return; }
        if (!existePaciente(pacId)) { System.out.println("El paciente no existe."); return; }
        if (!disponible(docId, fh)) { System.out.println("Conflicto: el doctor ya tiene una cita en esa fecha/hora."); return; }

        Cita c = new Cita(id, fh, mot, docId, pacId);
        citas.add(c);
        ArchivoCSV.guardarCitas(citas);
        System.out.println("Cita creada.");
    }

    private void listarDoctores() {
        System.out.println("\n-- Doctores --");
        doctores.forEach(d -> System.out.println(d.getId() + " | " + d.getNombre() + " | " + d.getEspecialidad()));
    }
    private void listarPacientes() {
        System.out.println("\n-- Pacientes --");
        pacientes.forEach(p -> System.out.println(p.getId() + " | " + p.getNombre()));
    }
    private void listarCitas() {
        System.out.println("\n-- Citas --");
        citas.forEach(c -> System.out.println(
                c.getId() + " | " + c.getFechaHora() + " | " + c.getMotivo()
                        + " | Doctor:" + c.getDoctorId() + " | Paciente:" + c.getPacienteId()));
    }
}
//