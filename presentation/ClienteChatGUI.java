package presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class ClienteChatGUI extends JFrame {

    private JTextArea areaChat;
    private JTextField campoMensagem;
    private JButton botaoEnviar;

    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter saida;

    private DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public ClienteChatGUI() {
        super("Cliente de Chat (GUI)");

        setLayout(new BorderLayout(10, 10));

        areaChat = new JTextArea();
        areaChat.setEditable(false);
        areaChat.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaChat.setBackground(new Color(245, 245, 245));

        JScrollPane scrollPane = new JScrollPane(areaChat);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new BorderLayout(5, 5));

        campoMensagem = new JTextField();
        campoMensagem.setFont(new Font("SansSerif", Font.PLAIN, 14));

        botaoEnviar = new JButton("Enviar");
        botaoEnviar.setFont(new Font("SansSerif", Font.BOLD, 14));

        painelInferior.add(campoMensagem, BorderLayout.CENTER);
        painelInferior.add(botaoEnviar, BorderLayout.EAST);

        painelInferior.setBorder(
                BorderFactory.createEmptyBorder(5, 10, 10, 10));

        add(painelInferior, BorderLayout.SOUTH);

        ActionListener acaoEnviar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensagem();
            }
        };

        botaoEnviar.addActionListener(acaoEnviar);
        campoMensagem.addActionListener(acaoEnviar);

        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        Thread threadConexao = new Thread(new Runnable() {
            @Override
            public void run() {
                conectarAoServidor();
            }
        });

        threadConexao.start();
    }

    private void conectarAoServidor() {

        String ip = "127.0.0.1";
        int porta = 5000;

        try {
            adicionarMensagem(
                    "System: Conectando ao servidor ("
                            + ip + ":" + porta + ")...");

            socket = new Socket(ip, porta);

            adicionarMensagem("System: Conectado ao Servidor!");

            entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            saida = new PrintWriter(
                    socket.getOutputStream(), true);

            String mensagem;

            while ((mensagem = entrada.readLine()) != null) {
                adicionarMensagem("[Octavio]: " + mensagem);
            }

        } catch (IOException e) {
            adicionarMensagem(
                    "System: Erro de conexão com o servidor.");
        }
    }

    private void enviarMensagem() {

        String texto = campoMensagem.getText().trim();

        if (!texto.isEmpty() && saida != null) {

            String hora = LocalTime.now().format(formatter);

            saida.println(texto);

            adicionarMensagem(
                    "[Cliente (" + hora + ")]: " + texto);

            campoMensagem.setText("");
        }
    }

    private void adicionarMensagem(String msg) {

        areaChat.append(msg + "\n");

        areaChat.setCaretPosition(
                areaChat.getDocument().getLength());
    }

    public static void main(String[] args) {
        new ClienteChatGUI();
    }
}
