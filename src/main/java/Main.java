import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        //System.out.println(engine.search("бизнес"));


        int port = 8989;

        ServerSocket serverSocket = new ServerSocket(port);
        //try (ServerSocket serverSocket = new ServerSocket(port);
        //ждём подключения, когда происходит соединение
        try (Socket clientSocket = serverSocket.accept();
             //    Socket clientSocket = serverSocket.accept();
             // отправлять
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             // получать сообщения
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            System.out.printf("New connection accepted. Port %d%n", clientSocket.getPort());

            while (true) {

                // ждем клиента
                final String wordSearch = in.readLine();
                //String wordSearch = "бизнес";
                // отвечаем клиенту
                List<PageEntry> list = engine.search(wordSearch);
                serverSocket.close();
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Type listType = new TypeToken<List<PageEntry>>() {
                }.getType();

                out.println(gson.toJson(list, listType));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}