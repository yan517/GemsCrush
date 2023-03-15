//Author: Lam Yan Yan Cindy, Tse Wai To

package Game;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author yan
 */
public class previewImage {

    Image pic;
    Random ran = new Random();

    public int preview(int set) {

        int save1 = ran.nextInt(7);
        String newGemName = "";
        if (set == 1) {
            if (save1 == 0) {
                newGemName = "/assets/01.png";
            } else if (save1 == 1) {
                newGemName = "/assets/02.png";
            } else if (save1 == 2) {
                newGemName = "/assets/03.png";
            } else if (save1 == 3) {
                newGemName = "/assets/04.png";
            } else if (save1 == 4) {
                newGemName = "/assets/05.png";
            } else if (save1 == 5) {
                newGemName = "/assets/06.png";
            } else if (save1 == 6) {
                newGemName = "/assets/07.png";
            }
        } else if (save1 == 0) {
            newGemName = "/assets/gemBlue.png";
        } else if (save1 == 1) {
            newGemName = "/assets/gemGreen.png";
        } else if (save1 == 2) {
            newGemName = "/assets/gemOrange.png";
        } else if (save1 == 3) {
            newGemName = "/assets/gemPurple.png";
        } else if (save1 == 4) {
            newGemName = "/assets/gemRed.png";
        } else if (save1 == 5) {
            newGemName = "/assets/gemWhite.png";
        } else if (save1 == 6) {
            newGemName = "/assets/gemYellow.png";
        }
        return save1;
    }

    public Image image(int i, int set) {

        String newGemName = "";
        if (set == 1) {
            if (i == 0) {
                newGemName = "/assets/01.png";
            } else if (i == 1) {
                newGemName = "/assets/02.png";
            } else if (i == 2) {
                newGemName = "/assets/03.png";
            } else if (i == 3) {
                newGemName = "/assets/04.png";
            } else if (i == 4) {
                newGemName = "/assets/05.png";
            } else if (i == 5) {
                newGemName = "/assets/06.png";
            } else if (i == 6) {
                newGemName = "/assets/07.png";
            } else if (i == 10) {
                newGemName = "/assets/10.png";
            }
        } else if (i == 0) {
            newGemName = "/assets/gemBlue.png";
        } else if (i == 1) {
            newGemName = "/assets/gemGreen.png";
        } else if (i == 2) {
            newGemName = "/assets/gemOrange.png";
        } else if (i == 3) {
            newGemName = "/assets/gemPurple.png";
        } else if (i == 4) {
            newGemName = "/assets/gemRed.png";
        } else if (i == 5) {
            newGemName = "/assets/gemWhite.png";
        } else if (i == 6) {
            newGemName = "/assets/gemYellow.png";
        } else if (i == 10) {
            newGemName = "/assets/10.png";
        }
        pic = new ImageIcon(this.getClass().getResource(newGemName)).getImage();
        return pic;
    }
}
