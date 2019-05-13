
import java.net.*;
        import java.io.*;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args)    {
        int port = 6666; // случайный порт (может быть любое число от 1025 до 65535)
        try {
            ServerSocket ss = new ServerSocket(port); // создаем сокет сервера и привязываем его к вышеуказанному порту
            System.out.println("Waiting for a client...");

            Socket socket = ss.accept(); // заставляем сервер ждать подключений и выводим сообщение когда кто-то связался с сервером
            System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");
            System.out.println();

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);
            ArrayList<String> computing=new ArrayList<>();
            int a=0;
            String line;
            while(true) {
                line = in.readUTF(); // ожидаем пока клиент пришлет строку текста.
                computing.add(line);

                System.out.println("The dumb client just sent me this line : " + line);
                System.out.println("I'm sending it back...");
                System.out.println();
                out.writeUTF(line); // отсылаем клиенту обратно ту самую строку текста.
                out.flush(); // заставляем поток закончить передачу данных.
                System.out.println("Waiting for the next line...");
                System.out.println();
                a++;
                if (a==3){
                    int b=0;
                    if (computing.get(1).charAt(0)=='+') {
                        b = Integer.parseInt(computing.get(0)) + Integer.parseInt(computing.get(2));
                    }
                    if (computing.get(1).charAt(0)=='-') {
                        b = Integer.parseInt(computing.get(0)) - Integer.parseInt(computing.get(2));
                    }
                    if (computing.get(1).charAt(0)=='/') {
                        b = Integer.parseInt(computing.get(0)) / Integer.parseInt(computing.get(2));
                    }
                    if (computing.get(1).charAt(0)=='*') {
                        b = Integer.parseInt(computing.get(0)) * Integer.parseInt(computing.get(2));
                    }
                    out.writeInt(b);
                    out.flush();
                    computing.clear();
                    a=0;
                }
            }

        } catch(Exception x) {
            System.out.println(x);
        }
    }
}