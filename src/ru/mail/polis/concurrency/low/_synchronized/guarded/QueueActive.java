package ru.mail.polis.concurrency.low._synchronized.guarded;

/**
 * Created by Nechaev Mikhail
 * Since 08/11/2018.
 */
class QueueActive {

    private String data;

    String getData() {
        while (true) {
            synchronized (this) {
                if (this.data != null) {
                    String result = this.data;
                    this.data = null;
                    return result;
                }
            }
        }
    }

    void setData(String data) {
        while (true) {
            synchronized (this) {
                if (this.data == null) {
                    this.data = data;
                    break;
                }
            }
        }
    }
}
