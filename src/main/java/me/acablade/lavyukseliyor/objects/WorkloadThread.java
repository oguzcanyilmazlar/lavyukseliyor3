package me.acablade.lavyukseliyor.objects;

import java.util.*;

public class WorkloadThread implements Runnable {

    private static final double MAX_MILLIS_PER_TICK = 2.5;
    private static final int MAX_NANOS_PER_TICK = (int) (MAX_MILLIS_PER_TICK* 1e6);

    private final Deque<Workload> workloadDeque = new ArrayDeque<>();

    public void add(Workload workload){
        workloadDeque.add(workload);
    }


    @Override
    public void run() {
        if(workloadDeque.isEmpty()) return;
        long stopTime = System.nanoTime() + MAX_NANOS_PER_TICK;

        Workload nextLoad;

        while (System.nanoTime() <= stopTime && (nextLoad = workloadDeque.poll()) != null){
            nextLoad.compute();
        }
    }
}
