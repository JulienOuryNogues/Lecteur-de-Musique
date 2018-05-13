import net.sourceforge.jaad.aac.AACException;
import net.sourceforge.jaad.aac.Decoder;
import net.sourceforge.jaad.aac.SampleBuffer;
import net.sourceforge.jaad.mp4.MP4Container;
import net.sourceforge.jaad.mp4.api.AudioTrack;
import net.sourceforge.jaad.mp4.api.Frame;
import net.sourceforge.jaad.mp4.api.Movie;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.io.RandomAccessFile;
import java.util.List;

/**
 * Created by Julien on 21/04/2016.
 */
public class MP4Reader extends MusicReader {

    public MP4Reader(String filename){
        super(filename);
    }


    public void play(){
        try {
            decodeMP4(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // EN suivant la biblio et des exemples sur le net, on arrive à peu près a ca

    private void decodeMP4(String in) throws Exception {
        SourceDataLine line = null;

        try {
            MP4Container cont = new MP4Container(new RandomAccessFile(in, "r"));
            Movie movie = cont.getMovie();
            // je récupère le AAC
            List tracks = movie.getTracks(AudioTrack.AudioCodec.AAC);
            if(tracks.isEmpty()) {
                throw new Exception("movie does not contain any AAC track");
            }
            // la musique
            AudioTrack track = (AudioTrack)tracks.get(0);
            AudioFormat aufmt = new AudioFormat((float)track.getSampleRate(), track.getSampleSize(), track.getChannelCount(), true, true);
            // j'ouvre la "ligne"
            line = AudioSystem.getSourceDataLine(aufmt);
            line.open();
            line.start();
            // je mes en place le decodeur et le buffer qui vont lire frame à frame
            Decoder dec = new Decoder(track.getDecoderSpecificInfo());
            SampleBuffer buf = new SampleBuffer();

            // pour toute les frames
            while(track.hasMoreFrames()) {
                Frame frame = track.readNextFrame();

                try {
                    // on décode et on joue
                    dec.decodeFrame(frame.getData(), buf);
                    byte[] b = buf.getData();
                    line.write(b, 0, b.length);
                } catch (AACException var15) {
                    var15.printStackTrace();
                }
            }
        } finally {
            if(line != null) {
                // fermeture propre
                line.stop();
                line.close();
            }

        }

    }
}
