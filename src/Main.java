/**
 * Created by Julien on 21/04/2016.
 */
public class Main {

        public static void main(String[] args) throws Exception {

            // j'invoque un lecteur adapté, et il ne me reste plus qu'a le play
            MusicReader reader = MusicReaderFactory.create("TestAAC.aac");
            reader.play();

        }
}
