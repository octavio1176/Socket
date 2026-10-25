package ServidorChat;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorChat {

    private static final int PORTA = 12345;

    static void main(String[] args) {
        System.out.println("Servidor de Chat iniciado na porta " + PORTA + "...");

        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Novo cliente conectado: " + socket.getInetAddress());


                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            System.out.println("Erro crítico no servidor: " + e.getMessage());
        }
    }
}
