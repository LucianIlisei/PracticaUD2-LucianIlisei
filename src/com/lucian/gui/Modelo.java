package com.lucian.gui;

import com.lucian.base.entidades.*;
import com.lucian.conexion.Conexion;
import com.lucian.utilidades.Utilidades;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Modelo {

    private Conexion conexion;
    public Modelo(Conexion conexion) {
        this.conexion = conexion;
    }

    void insertarPaciente(Paciente paciente) {
        String sentenciaSql = "INSERT INTO pacientes (nombre, primer_apellido, segundo_apellido, fecha_nacimiento, sexo, telefono, email, fumador, id_hospital) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.getConexion().prepareStatement(sentenciaSql);

            sentencia.setString(1, paciente.getNombre());
            sentencia.setString(2, paciente.getPrimerApellido());
            sentencia.setString(3, paciente.getSegundoApellido());
            sentencia.setDate(4, Date.valueOf(paciente.getFechaNacimiento()));
            sentencia.setString(5, paciente.getSexo());
            sentencia.setString(6, paciente.getTelefono());
            sentencia.setString(7, paciente.getEmail());
            sentencia.setBoolean(8, paciente.isFumador());
            sentencia.setInt(9, paciente.getIdHospital());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void modificarPaciente(Paciente pacienteModificado) {
        String sentenciaSql = "UPDATE pacientes SET nombre =?, primer_apellido =?, segundo_apellido =?, " +
                "fecha_nacimiento =?, sexo =?, telefono =?, email =?, fumador =?, id_hospital =? WHERE id_paciente =?";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.getConexion().prepareStatement(sentenciaSql);
            sentencia.setString(1, pacienteModificado.getNombre());
            sentencia.setString(2, pacienteModificado.getPrimerApellido());
            sentencia.setString(3, pacienteModificado.getSegundoApellido());
            sentencia.setDate(4, Date.valueOf(pacienteModificado.getFechaNacimiento()));
            sentencia.setString(5, pacienteModificado.getSexo());
            sentencia.setString(6, pacienteModificado.getTelefono());
            sentencia.setString(7, pacienteModificado.getEmail());
            sentencia.setBoolean(8, pacienteModificado.isFumador());
            sentencia.setInt(9, pacienteModificado.getIdHospital());
            sentencia.setInt(10, pacienteModificado.getIdPaciente());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void eliminarPaciente(int idPaciente) {
        String sentenciaSql = "DELETE FROM pacientes WHERE id_paciente =?";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.getConexion().prepareStatement(sentenciaSql);
            sentencia.setInt(1, idPaciente);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    void insertarDoctor(Doctor doctor) {
        String sentenciaSql = "INSERT INTO doctores " +
                "(nombre, primer_apellido, segundo_apellido, telefono, email, especialidad, fecha_contratacion, id_hospital) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.getConexion().prepareStatement(sentenciaSql);

            sentencia.setString(1, doctor.getNombre());
            sentencia.setString(2, doctor.getPrimerApellido());
            sentencia.setString(3, doctor.getSegundoApellido());
            sentencia.setString(4, doctor.getTelefono());
            sentencia.setString(5, doctor.getEmail());
            sentencia.setString(6, doctor.getEspecialidad());
            sentencia.setDate(7, Date.valueOf(doctor.getFechaContratacion()));
            sentencia.setInt(8, doctor.getIdHospital());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void modificarDoctor(Doctor doctorModificado) {
        String sentenciaSql = "UPDATE doctores SET nombre =?, primer_apellido =?, segundo_apellido =?, " +
                "telefono =?, email =?, especialidad =?, fecha_contratacion =?, id_hospital =? " +
                "WHERE id_doctor =?";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.getConexion().prepareStatement(sentenciaSql);

            sentencia.setString(1, doctorModificado.getNombre());
            sentencia.setString(2, doctorModificado.getPrimerApellido());
            sentencia.setString(3, doctorModificado.getSegundoApellido());
            sentencia.setString(4, doctorModificado.getTelefono());
            sentencia.setString(5, doctorModificado.getEmail());
            sentencia.setString(6, doctorModificado.getEspecialidad());
            sentencia.setDate(7, Date.valueOf(doctorModificado.getFechaContratacion()));
            sentencia.setInt(8, doctorModificado.getIdHospital());
            sentencia.setInt(9, doctorModificado.getIdDoctor());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void eliminarDoctor(int idDoctor) {
        String sentenciaSql = "DELETE FROM doctores WHERE id_doctor = ?";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.getConexion().prepareStatement(sentenciaSql);
            sentencia.setInt(1, idDoctor);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void insertarHospital(Hospital hospital) {
        String sentenciaSql = "INSERT INTO hospitales (nombre, provincia, telefono, capacidad, tipo) " +
                "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.getConexion().prepareStatement(sentenciaSql);

            sentencia.setString(1, hospital.getNombre());
            sentencia.setString(2, hospital.getProvincia());
            sentencia.setString(3, hospital.getTelefono());
            sentencia.setInt(4, hospital.getCapacidad());
            sentencia.setString(5, hospital.getTipo());

            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void modificarHospital(Hospital hospitalModificado) {
        String sentenciaSql = "UPDATE hospitales SET nombre =?, provincia =?, telefono =?, " +
                "capacidad =?, tipo =? WHERE id_hospital =?";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.getConexion().prepareStatement(sentenciaSql);

            sentencia.setString(1, hospitalModificado.getNombre());
            sentencia.setString(2, hospitalModificado.getProvincia());
            sentencia.setString(3, hospitalModificado.getTelefono());
            sentencia.setInt(4, hospitalModificado.getCapacidad());
            sentencia.setString(5, hospitalModificado.getTipo());
            sentencia.setInt(6, hospitalModificado.getIdHospital());

            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void eliminarHospital(int idHospital) {
        String sentenciaSql = "DELETE FROM hospitales WHERE id_hospital = ?";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.getConexion().prepareStatement(sentenciaSql);
            sentencia.setInt(1, idHospital);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void insertarCita(Cita cita) {
        String sentenciaSql = "INSERT INTO citas (id_paciente, id_doctor, fecha_hora, motivo, diagnostico, id_medicamento) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.getConexion().prepareStatement(sentenciaSql);

            sentencia.setInt(1, cita.getIdPaciente());
            sentencia.setInt(2, cita.getIdDoctor());
            sentencia.setTimestamp(3, Timestamp.valueOf(cita.getFechaHora()));
            sentencia.setString(4, cita.getMotivo());
            sentencia.setString(5, cita.getDiagnostico());
            sentencia.setInt(6, cita.getIdMedicamento());

            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void modificarCita(Cita citaModificada) {
        String sentenciaSql = "UPDATE citas SET id_paciente =?, id_doctor =?, fecha_hora =?, " +
                "motivo =?, diagnostico =?, id_medicamento =? WHERE id_cita =?";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.getConexion().prepareStatement(sentenciaSql);

            sentencia.setInt(1, citaModificada.getIdPaciente());
            sentencia.setInt(2, citaModificada.getIdDoctor());
            sentencia.setTimestamp(3, Timestamp.valueOf(citaModificada.getFechaHora()));
            sentencia.setString(4, citaModificada.getMotivo());
            sentencia.setString(5, citaModificada.getDiagnostico());
            sentencia.setInt(6, citaModificada.getIdMedicamento());
            sentencia.setInt(7, citaModificada.getIdCita());

            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void eliminarCita(int idCita) {
        String sentenciaSql = "DELETE FROM citas WHERE id_cita = ?";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.getConexion().prepareStatement(sentenciaSql);
            sentencia.setInt(1, idCita);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void insertarMedicamento(Medicamento medicamento) {
        String sentenciaSql = "INSERT INTO medicamentos (nombre, descripcion, tipo, dosis, efectos_secundarios) " +
                "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.getConexion().prepareStatement(sentenciaSql);

            sentencia.setString(1, medicamento.getNombre());
            sentencia.setString(2, medicamento.getDescripcion());
            sentencia.setString(3, medicamento.getTipo());
            sentencia.setString(4, medicamento.getDosis());
            sentencia.setString(5, medicamento.getEfectosSecundarios());

            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void modificarMedicamento(Medicamento medicamentoModificado) {
        String sentenciaSql = "UPDATE medicamentos SET nombre =?, descripcion =?, tipo =?, " +
                "dosis =?, efectos_secundarios =? WHERE id_medicamento =?";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.getConexion().prepareStatement(sentenciaSql);

            sentencia.setString(1, medicamentoModificado.getNombre());
            sentencia.setString(2, medicamentoModificado.getDescripcion());
            sentencia.setString(3, medicamentoModificado.getTipo());
            sentencia.setString(4, medicamentoModificado.getDosis());
            sentencia.setString(5, medicamentoModificado.getEfectosSecundarios());
            sentencia.setInt(6, medicamentoModificado.getIdMedicamento());

            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void eliminarMedicamento(int idMedicamento) {
        String sentenciaSql = "DELETE FROM medicamentos WHERE id_medicamento = ?";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.getConexion().prepareStatement(sentenciaSql);
            sentencia.setInt(1, idMedicamento);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
