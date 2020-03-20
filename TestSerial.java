import java.io.*;

public class TestSerial implements Serializable {

  public void whenSerializingAndDeserializing_ThenObjectIsTheSame ()
  throws IOException, ClassNotFoundException {
    Board b1 = new Board();

    FileOutputStream fileOutputStream
      = new FileOutputStream("yourfile.txt");
    ObjectOutputStream objectOutputStream
      = new ObjectOutputStream(fileOutputStream);
    objectOutputStream.writeObject(b1);
    objectOutputStream.flush();
    objectOutputStream.close();

    FileInputStream fileInputStream
      = new FileInputStream("yourfile.txt");
    ObjectInputStream objectInputStream
      = new ObjectInputStream(fileInputStream);
    Board b2 = (Board) objectInputStream.readObject();
    objectInputStream.close();

    System.out.println(b1.equals(b2));
    System.out.println(b2);
    System.out.println(b1.getBlackKing().equals(b2.getBlackKing()));
    System.out.println(b1.getPieces().get(2).position.equals(b2.getPieces().get(2).position));
    System.out.println(b1.getPieces().get(2).position);
  }

  public static void main(String args[]) {
    TestSerial t = new TestSerial();
    try {
      t.whenSerializingAndDeserializing_ThenObjectIsTheSame();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

}
