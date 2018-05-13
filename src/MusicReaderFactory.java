import java.io.IOException;

/**
 * Created by Julien on 21/04/2016.
 */
public class MusicReaderFactory  {

    // ma factory va créer le lecteur spécifique en matchant sur les extensions

    public static MusicReader create(String filename) throws IOException {
        String ext = getExt(filename);
        if (ext.equalsIgnoreCase("MP3")){
            return new MP3Reader(filename);
        }
        if (ext.equalsIgnoreCase("AAC")){
            return new AACReader(filename);
        }
        if (ext.equalsIgnoreCase("MP4")){
            return new MP4Reader(filename);
        }
        // le WAV ne marche pas si on a la bibliothèque, mais je le laisse quand meme
        if (ext.equalsIgnoreCase("WAV")){
            return new WAVReader(filename);
        }else {
            // par défaut, on essaie le MP3
            return new MP3Reader(filename);
        }
    }

    // recupere l'extension du fichier (il faut qu'il y ait une extension )!
    public static String getExt(String name){
        String[] parsing = name.split("\\.");
        return parsing[(parsing.length)-1];

    }
}
