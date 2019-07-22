package Controllers.Physicians;

import java.io.InputStream;
import java.util.IdentityHashMap;

public interface Physician {
    IdentityHashMap<String, String> currentSession = new IdentityHashMap<>();
    IdentityHashMap<String, InputStream> imageResult = new IdentityHashMap<String, java.io.InputStream>();
    IdentityHashMap<String, String> resultText = new IdentityHashMap<>();
    void addPatientDetails();

    void viewPatientDetails();

    void viewPatientHistory();

    void viewPatientLabTests();

    void viewPatientAppointments();

    void Patientdiagnosis();

    void Patientprescription();
}
