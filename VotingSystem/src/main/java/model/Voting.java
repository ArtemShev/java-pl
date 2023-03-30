package model;

import java.util.Calendar;
import java.util.List;

public class Voting {

    public String title;
    public List candidates;

    public Calendar calendar;

    public Voting(String name, Calendar calendar){
        this.title = name;
        this.calendar = calendar;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setCandidates(List candidates) {
        this.candidates = candidates;
    }

    public List getCandidates() {
        return candidates;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
