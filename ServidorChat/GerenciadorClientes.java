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

    public static synchronized void broadcast(String mensagem) {
        for (PrintWriter cliente : clientes) {
            cliente.println(mensagem);
        }
    }
}
