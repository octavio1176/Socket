package ServidorChat;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorClientes {
    private static final List<PrintWriter> clientes = new ArrayList<>();

    public static synchronized void adicionar(PrintWriter cliente) {
        clientes.add(cliente);
    }

    public static synchronized void remover(PrintWriter cliente) {
        clientes.remove(cliente);
    }
    public static synchronized void broadcast(
            String mensagem, PrintWriter remetente) {

        for (PrintWriter cliente : clientes) {
            if (cliente != remetente) {
                cliente.println(mensagem);
            }
        }
    }
}
