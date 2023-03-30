package model;
public class Candidate {
    public static String name;
    public static String surname;

    public static String patromonic;

    public static String job;

    public int voices = 0;

    public Candidate(String name){
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setVoices(int voices){
        this.voices = voices;
    }

    public int getVoices() {
        return voices;
    }

    public static void setSurname(String surname) {
        Candidate.surname = surname;
    }

    public static String getSurname() {
        return surname;
    }

    public static void setJob(String job) {
        Candidate.job = job;
    }

    public static String getJob() {
        return job;
    }

    public static void setPatromonic(String patromonic) {
        Candidate.patromonic = patromonic;
    }

    public static String getPatromonic() {
        return patromonic;
    }
}
