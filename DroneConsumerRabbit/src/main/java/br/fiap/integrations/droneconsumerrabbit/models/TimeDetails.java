package br.fiap.integrations.droneconsumerrabbit.models;

import lombok.Data;

@Data
public class TimeDetails {

    private int totalFireCount;
    private int remainingFireCount;
    private boolean runForever;
    private long repeatIntervalMs;
    private long initialOffsetMs;
    private String callbackData;

}