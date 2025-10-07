public class Cita {
    private final String id;
    private final String fechaHora;
    private final String motivo;
    private final String doctorId;
    private final String pacienteId;

    public Cita(String id, String fechaHora, String motivo, String doctorId, String pacienteId) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.doctorId = doctorId;
        this.pacienteId = pacienteId;
    }

    public String getId() { return id; }
    public String getFechaHora() { return fechaHora; }
    public String getMotivo() { return motivo; }
    public String getDoctorId() { return doctorId; }
    public String getPacienteId() { return pacienteId; }
}
