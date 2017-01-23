package tools;

import java.io.*;

/**
 * According to the example of Dave Miller - JAVAWORLD
 * link: http://www.javaworld.com/article/2077578/learn-java/java-tip-76--an-alternative-to-the-deep-copy-technique.html
 */
public class ObjectCloner {

    private ObjectCloner() {
    }

    /**
     * @param object
     * @return a copy of any object which implements Serializable
     * @throws IOException
     */
    public static Object deepClone(Object object) throws IOException {
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;

        try {
            ByteArrayOutputStream arrayOutput = new ByteArrayOutputStream();

            outputStream = new ObjectOutputStream(arrayOutput);
            outputStream.writeObject(object);
            outputStream.flush();

            inputStream = new ObjectInputStream(new ByteArrayInputStream(arrayOutput.toByteArray()));

            return inputStream.readObject();

        } catch (Exception e) {
            System.out.println("Exception while object cloning: " + e);
        } finally {
            outputStream.close();
            inputStream.close();
        }
        return null;
    }
}
