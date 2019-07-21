package Controllers.Physicians;

import java.util.IdentityHashMap;

public interface Physician {
    IdentityHashMap<String, String> currentSession = new IdentityHashMap<>();

    void addPatientDetails();

    void viewPatientDetails();

    void viewPatientHistory();

    void viewPatientLabTests();

    void viewPatientAppointments();

    void Patientdiagnosis();

    void Patientprescription();
}
