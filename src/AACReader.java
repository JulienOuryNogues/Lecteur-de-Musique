import net.sourceforge.jaad.aac.Decoder;
import net.sourceforge.jaad.aac.SampleBuffer;
import net.sourceforge.jaad.adts.ADTSDemultiplexer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.io.FileInputStream;

public class AACReader extends MusicReader{
    public AACReader(String filename) {
        super(filename);
    }
    public void play() {
        try {
            decodeAAC(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void decodeAAC(String in) throws Exception {
        SourceDataLine line = null;

        try {
            // le lecteur, le decodeur et le buffer
            ADTSDemultiplexer adts = new ADTSDemultiplexer(new FileInputStream(in));
            Decoder dec = new Decoder(adts.getDecoderSpecificInfo());
            SampleBuffer buf = new SampleBuffer();

            // c'est pas propre mais quand il essaie de lire plus loin que le fichier, cela plante
            while(true) {
                // on lit
                byte[] b = adts.readNextFrame();
                dec.decodeFrame(b, buf);
                if(line == null) {
                    // initialisation de la line
                    AudioFormat aufmt = new AudioFormat((float)buf.getSampleRate(), buf.getBitsPerSample(), buf.getChannels(), true, true);
                    line = AudioSystem.getSourceDataLine(aufmt);
                    line.open();
                    line.start();
                }
                // et on joue
                b = buf.getData();
                line.write(b, 0, b.length);
            }
        } finally {
            if(line != null) {
                //fermeture propre
                line.stop();
                line.close();
            }
        }
    }
}