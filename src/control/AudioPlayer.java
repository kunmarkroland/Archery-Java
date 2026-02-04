package control;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class AudioPlayer {
    public void playMusic() {
        String musicPathString = "sound/heavyMetalZene.wav";
        try{
            File musicPath = new File(musicPathString);
            if(musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);

                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                float volume = -20;
                volumeControl.setValue(volume);

                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else{
                System.out.println("sound/heavyMetalZene.wav nem letezik");
            }
        }
        catch (Exception e){
            System.out.println("Hiba a zene lejatszasanal");
        }
    }

    public void playHitSound() {
        String musicPathString = "sound/hit_vagott.wav";
        try{
            File musicPath = new File(musicPathString);
            if(musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
            else{
                System.out.println("sound/hit_vagott.wav nem letezik");
            }
        }
        catch (Exception e){
            System.out.println("Hiba a hit hang lejatszasanal");
        }
    }

    public void playShotSound() {
        String musicPathString = "sound/shot_vagott.wav";
        try{
            File musicPath = new File(musicPathString);
            if(musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
            else{
                System.out.println("sound/shot_vagott.wav nem letezik");
            }
        }
        catch (Exception e){
            System.out.println("Hiba a shot hang lejatszasanal");
        }
    }
}
