package be.pxl.ja.oefening_9_1;

public class TalkerRunnable implements Runnable {
    public static void main(String[] args) {
        for (int i = 1; i <= 4; i++) {
            new Thread(new Talker(i)).start();
        }
    }

    private int id;

    public TalkerRunnable(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(this.id);

            try {
                Thread.sleep(500);
            } catch(InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}
