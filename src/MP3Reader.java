import javazoom.jl.player.advanced.*;



public class MP3Reader  extends MusicReader
{

    private AdvancedPlayer player;

    public MP3Reader(String filename)
    {
        super(filename);
    }

    public void play()
    {
        try
        {
            String urlAsString =
                    "file:///"
                            + new java.io.File(".").getCanonicalPath()+ "/"
                            + filename;

            // je crée mon lecteur Audio en suivant les superbes instructions de la Doc

            this.player = new AdvancedPlayer(
                            new java.net.URL(urlAsString).openStream(),
                            javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());


            this.player.play();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}