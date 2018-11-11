package ru.mail.polis.concurrency.low._synchronized;

import ru.mail.polis.logger.LoggerUtils;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
class FineGrainedSynchronization {

    private static final Logger LOGGER = LoggerUtils.getFormattedLogger(
            FineGrainedSynchronization.class.getSimpleName()
    );
    
    private final Object lockA = new Object();
    private final Object lockB = new Object();
    private int a = 0;
    private int b = 0;

    private void incrementA() {
        LOGGER.info(" A-1");
        synchronized (lockA) {
            LOGGER.info(" A-2");
            a++;
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                LOGGER.info(" A-I");
                return;
            }
            LOGGER.info(" A-3");
        }
        LOGGER.info(" A-4");
    }

    private void incrementB() {
        LOGGER.info(" B-1");
        synchronized (lockB) {
            LOGGER.info(" B-2");
            b--;
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                LOGGER.info(" B-I");
                return;
            }
            LOGGER.info(" B-3");
        }
        LOGGER.info(" B-4");
    }

    public static void main(String[] args) {
        FineGrainedSynchronization fineGrainedSynchronization = new FineGrainedSynchronization();
        Thread threadA = new Thread(fineGrainedSynchronization::incrementA);
        Thread threadB = new Thread(fineGrainedSynchronization::incrementB);
        threadA.start();
        threadB.start();
    }
}
