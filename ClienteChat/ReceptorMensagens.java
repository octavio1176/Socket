package ClienteChat;
import java.io.BufferedReader;
import java.io.IOException;

public class ReceptorMensagens implements Runnable {
    private BufferedReader in;

    public ReceptorMensagens(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            String msgRecebida;
            while ((msgRecebida = in.readLine()) != null) {
                System.out.println(msgRecebida);
            }
        } catch (IOException e) {
            System.out.println("Conexão encerrada pelo servidor.");
        }
    }
}