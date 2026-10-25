package ClienteChat;
import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClienteChat {
    private static final String ENDERECO_SERVIDOR = "localhost";
    private static final int PORTA = 12345;

    static void main(String[] args) {
        try {

            Socket socket = new Socket(ENDERECO_SERVIDOR, PORTA);
            System.out.println("Conectado ao servidor com sucesso!");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);


            Thread threadRecebe = new Thread(new ReceptorMensagens(in));
            threadRecebe.start();


            System.out.println("Escreve a tua mensagem (pressiona Enter para enviar ou escreve 'sair'):");
            while (true) {
                String texto = scanner.nextLine();
                if (texto.equalsIgnoreCase("sair")) {
                    break;
                }

                String horaFormatada = LocalTime.now().withNano(0).toString();
                String mensagemCompleta = "[" + horaFormatada + "] Utilizador: " + texto;
                out.println(mensagemCompleta);
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("Erro ao conectar ao servidor. Verifica se o servidor está a correr.");
        }
    }
}