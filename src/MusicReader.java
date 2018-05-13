/**
 * Created by Julien on 21/04/2016.
 */
public  abstract class  MusicReader {

    // c'est ma super classe dont va hériter tous mes lecteurs pour chaque format spécifique.
    protected String filename;


    public MusicReader(String filename){
        this.filename=filename;
    }

    // une méthode play qui sera overwrite par les lecteurs
    public abstract void play();

}
