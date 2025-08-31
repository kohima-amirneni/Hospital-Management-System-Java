import java.io.*;                       
import java.util.*;       

abstract class Person {
    protected String name;
    protected int age;
    protected String gender;

    public Person(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public abstract void displayInfo();
}

class Doctor extends Person {
    private String specialization;  
    public Doctor(String name, int age, String gender, String specialization) {
        super(name, age, gender);  
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void displayInfo() {
        System.out.println("Doctor Name: " + name);
        System.out.println("Age: " + age + ", Gender: " + gender);
        System.out.println("Specialization: " + specialization);
    }
}



class Patient extends Person {
    private String disease;  
    public Patient(String name, int age, String gender, String disease) {
        super(name, age, gender);
        this.disease = disease;
    }

    public String getDisease() {
        return disease;
    }

    public void displayInfo() {
        System.out.println("Patient Name: " + name);
        System.out.println("Age: " + age + ", Gender: " + gender);
        System.out.println("Disease: " + disease);
    }
}

public class HospitalManagementSystem {
    private static final String DOCTOR_FILE = "doctors.txt";
    private static final String PATIENT_FILE = "patients.txt";

    private static ArrayList<Doctor> doctors = new ArrayList<>();
    private static ArrayList<Patient> patients = new ArrayList<>();

    private static void addDoctor(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter specialization: ");
        String specialization = scanner.nextLine();

        Doctor doc = new Doctor(name, age, gender, specialization);
        doctors.add(doc);           
        writeDoctorToFile(doc);     
        System.out.println("Doctor added successfully.");
    }

    private static void addPatient(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter disease: ");
        String disease = scanner.nextLine();

        Patient pat = new Patient(name, age, gender, disease);
        patients.add(pat);          
        writePatientToFile(pat);    

        System.out.println("Patient added successfully.");
    }

    private static void viewDoctors() {
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
        } else {
            System.out.println("\n--- Doctors List ---");
            for (Doctor d : doctors) {
                d.displayInfo();    
                System.out.println("--------------------");
            }
        }
    }

    private static void viewPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
        } else {
            System.out.println("\n--- Patients List ---");
            for (Patient p : patients) {
                p.displayInfo();    
                System.out.println("---------------------");
            }
        }
    }

    private static void writeDoctorToFile(Doctor doctor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DOCTOR_FILE, true))) {
            writer.write(doctor.name + "|" + doctor.age + "|" + doctor.gender + "|" + doctor.getSpecialization());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing doctor to file.");
        }
    }

    private static void writePatientToFile(Patient patient) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATIENT_FILE, true))) {
            writer.write(patient.name + "|" + patient.age + "|" + patient.gender + "|" + patient.getDisease());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing patient to file.");
        }
    }

    private static void loadDoctorsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DOCTOR_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    doctors.add(new Doctor(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("No doctor data found yet.");
        }
    }

    private static void loadPatientsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PATIENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    patients.add(new Patient(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("No patient data found yet.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        loadDoctorsFromFile();
        loadPatientsFromFile();

        do {
            System.out.println("\n--- Hospital Management System ---");
            System.out.println("1. Add Doctor");
            System.out.println("2. Add Patient");
            System.out.println("3. View Doctors");
            System.out.println("4. View Patients");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1 -> addDoctor(scanner);
                case 2 -> addPatient(scanner);
                case 3 -> viewDoctors();
                case 4 -> viewPatients();
                case 5 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 5);

        scanner.close(); 
    }

}
