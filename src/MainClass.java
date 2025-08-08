import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class MainClass {

  public static void main(String[] args) throws Exception {

    gerarArquivo();

    Path path = Paths.get("benchmark.txt");

    //Java IO - Classico
    long t1Ini, t1Fim;
    t1Ini = System.currentTimeMillis();
    BufferedReader br = new BufferedReader(new FileReader(path.toFile()));
    while (br.readLine() != null);
    br.close();
    t1Fim = System.currentTimeMillis();
    System.out.println("JAVA IO - Demorou " + (t1Fim-t1Ini) + "ms");


    //Java NIO - usando FileChannel
    long t2Ini, t2Fim;
    t2Ini = System.currentTimeMillis();
    FileChannel channel = FileChannel.open(path, StandardOpenOption.READ);
    ByteBuffer buffer = ByteBuffer.allocate(16384);
    while (channel.read(buffer) != -1){
      buffer.flip();
      buffer.clear();
    };
    channel.close();
    t2Fim = System.currentTimeMillis();
    System.out.println("JAVA NIO Channel - Demorou " + (t2Fim-t2Ini) + "ms");

    //Java NIO2 - ReadAllLines
    long t3Ini, t3Fim;
    t3Ini = System.currentTimeMillis();
    List<String> linhas = Files.readAllLines(path);
    t3Fim = System.currentTimeMillis();
    System.out.println("JAVA NIO2 ReadAllLines - Demorou " + (t3Fim-t3Ini) + "ms");

  }

  public static void gerarArquivo(){
    String fileName = "benchmark.txt";
    String linhaBasica = "Este eh uma linha de exemplo para benchmark de leitura de arquivo\n";
    long tamanho = 200 * 1024 * 1024;

    try {
      long tamanhoAtual = 0;
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
      while(tamanhoAtual < tamanho){
        writer.write(linhaBasica);
        tamanhoAtual += linhaBasica.getBytes().length;
      }
    } catch(Exception ex){
      ex.printStackTrace();
    }

  }

}
