/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kap.invigulationtimetabling.domain;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
/**
 *
 * @author katiepambakian
 */
public class InvigulationTimer{
    public static void main(String[] args) {
            //get the current time
            Clock baseClock = Clock.systemDefaultZone();
            //get the time in x secconds
            Instant clock = Clock.offset(baseClock, Duration.ofSeconds(3)).instant();
            //keep looping before the time to halt has been reached
            while (!clock.isBefore(baseClock.instant())){
                baseClock = Clock.systemDefaultZone();
            }
            System.out.println("true");
    }
}
