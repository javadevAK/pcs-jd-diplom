import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        String host = "localhost";
        int port = 8989;

        try (Socket clientSocket = new Socket(host, port);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            Scanner scanner = new Scanner(System.in);


            System.out.println("Введите слово для поиска: ");
            String searchWord = scanner.nextLine();
            out.println(searchWord);
            String resp = in.readLine();
            System.out.println(resp);


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
