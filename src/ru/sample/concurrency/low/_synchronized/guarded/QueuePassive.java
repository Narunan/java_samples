package ru.sample.concurrency.low._synchronized.guarded;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
class QueuePassive {

    private String data;

    synchronized String getData() throws InterruptedException {
        while (this.data == null) {
            wait();
        }
        String result = this.data;
        this.data = null;
        notifyAll(); //notify if one thread
        return result;
    }

    synchronized void setData(String data) throws InterruptedException {
        while (this.data != null) {
            wait();
        }
        this.data = data;
        notifyAll(); //notify if one thread
    }
}
