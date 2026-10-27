package ServidorChat;
import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            GerenciadorClientes.adicionar(out);

            String mensagem;
            while ((mensagem = in.readLine()) != null) {
                System.out.println("Mensagem recebida: " + mensagem);
                GerenciadorClientes.broadcast(mensagem);
            }
        } catch (IOException e) {
            System.out.println("Cliente desconectado.");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            GerenciadorClientes.remover(out);
        }
    }
}