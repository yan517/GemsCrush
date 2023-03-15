//Author: Lam Yan Yan Cindy, Tse Wai To

package Game;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yan
 */
public class SaveLoad {

    private int time, score, s1, s2, set;
    Gem[] difGem = new Gem[64];

    public static void Save(String user, Gem[] difGem, int time, int score, int s1, int s2, int set) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(user));
            output.writeInt(time);
            output.writeInt(score);
            for (int x = 0; x < 64; x++) {
                output.writeObject(difGem[x]);
                output.writeUTF(difGem[x].getfileName());
            }
            output.writeInt(s1);
            output.writeInt(s2);
            output.writeInt(set);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean Load(String user) {
        boolean success = true;
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(user + ".txt"));
            this.time = input.readInt();
            this.score = input.readInt();
            for (int x = 0; x < 64; x++) {
                difGem[x] = (Gem) input.readObject();
                difGem[x].setfileName(input.readUTF());
                difGem[x].setP(difGem[x].getfileName());
            }
            this.s1 = input.readInt();
            this.s2 = input.readInt();
            this.set = input.readInt();
        } catch (IOException ex) {
            success = false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SaveLoad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public int getTime() {
        return time;
    }

    public int getScore() {
        return score;
    }

    public int getS1() {
        return s1;
    }

    public int getS2() {
        return s2;
    }

    public Gem[] getDifGem() {
        return difGem;
    }

    public int getSet() {
        return set;
    }
}
