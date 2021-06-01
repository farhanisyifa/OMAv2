package com.example.oma_v13;

public class Agenda {
    private String agendaName;
    private String agendaFrom;
    private String agendaDetail;
    private String agendaDate;

    public Agenda(){}

    public Agenda (String agendaName, String agendaFrom, String agendaDetail, String agendaDate){
        this.agendaName = agendaName;
        this.agendaFrom = agendaFrom;
        this.agendaDetail = agendaDetail;
        this.agendaDate = agendaDate;
    }

    public String getAgendaName() {
        return agendaName;
    }

    public String getAgendaFrom() {
        return agendaFrom;
    }

    public String getAgendaDetail() {
        return agendaDetail;
    }

    public String getAgendaDate() {
        return agendaDate;
    }
}
