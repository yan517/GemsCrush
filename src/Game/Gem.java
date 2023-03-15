//Author: Lam Yan Yan Cindy, Tse Wai To

package Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gem implements Serializable {

    public static final int orgX = 240;
    public static final int orgY = 40;
    public static final int w = 65;
    public static final int h = 65;
    private double posX = 0.0;
    private double posY = 0.0;
    private boolean selected = false;
    private transient Image pic;
    private transient Image focus;
    private boolean match;
    private String fileName;
    private int posX_;
    private int posY_;

    Gem(String file, int x, int y) {
        this.focus = new ImageIcon(this.getClass().getResource("/assets/focus.png")).getImage();
        this.pic = new ImageIcon(this.getClass().getResource(file)).getImage();
        this.posX = x;
        this.posY = y;
        this.posX_ = x * 65 + 240;
        this.posY_ = y * 65 + 40;
        match = false;
        fileName = file;
    }

    public void respam(int set) {
        Random ran = new Random();
        int i = ran.nextInt(7);
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
        }
        pic = new ImageIcon(this.getClass().getResource(newGemName)).getImage();
    }

    public void respam1(int i, int set) {
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
    }

    public boolean isAt(Point point) {
        if (point != null) {
            return (point.x > (posX * w + orgX) && point.x <= ((posX + 1) * w + orgX) && point.y > (posY * h + orgY) && point.y <= ((posY + 1) * h + orgY));
        } else {
            return false;
        }
    }

    public int getPosX_() {
        if (posX_ == (posX * 65 + 40)) {
            return posX_;
        }
        if (posX_ < (posX * 65 + 240)) {
            posX_ = posX_ + 13;

        }
        if (posX_ > (posX * 65 + 240)) {
            posX_ = posX_ - 13;
        }
        return posX_;
    }

    public int getPosY_() {
        if (posY_ == (posY * 65 + 40)) {
            return posY_;
        }

        if (posY_ < (posY * 65 + 40)) {
            posY_ = posY_ + 13;

        }
        if (posY_ > (posY * 65 + 40)) {
            posY_ = posY_ - 13;
        }
        return posY_;
    }

    public void setPosX_(int posX_) {
        this.posX_ = posX_;
    }

    public void setPosY_(int posY_) {
        this.posY_ = posY_;
    }

    public Image getPic() {
        return pic;
    }

    public void setPic(Image image) {
        this.pic = image;
    }

    public void setfileName(int fulfil, int set) {
        if (set == 1) {
            if (fulfil == 0) {
                fileName = "/assets/01.png";
            } else if (fulfil == 1) {
                fileName = "/assets/02.png";
            } else if (fulfil == 2) {
                fileName = "/assets/03.png";
            } else if (fulfil == 3) {
                fileName = "/assets/04.png";
            } else if (fulfil == 4) {
                fileName = "/assets/05.png";
            } else if (fulfil == 5) {
                fileName = "/assets/06.png";
            } else if (fulfil == 6) {
                fileName = "/assets/07.png";
            } else if (fulfil == 10) {
                fileName = "/assets/10.png";
            }
        } else if (fulfil == 0) {
            fileName = "/assets/gemBlue.png";
        } else if (fulfil == 1) {
            fileName = "/assets/gemGreen.png";
        } else if (fulfil == 2) {
            fileName = "/assets/gemOrange.png";
        } else if (fulfil == 3) {
            fileName = "/assets/gemPurple.png";
        } else if (fulfil == 4) {
            fileName = "/assets/gemRed.png";
        } else if (fulfil == 5) {
            fileName = "/assets/gemWhite.png";
        } else if (fulfil == 6) {
            fileName = "/assets/gemYellow.png";
        } else if (fulfil == 10) {
            fileName = "/assets/10.png";
        }
    }

    public void setfileName(String fileName) {
        this.fileName = fileName;
    }

    public String getfileName() {
        return fileName;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
        posX_ = (int) posX * 65 + 240;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
        posY_ = (int) posY * 65 + 40;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isMatch() {
        return match;
    }

    public void match(boolean match) {
        this.match = !match;
        // return match;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void toggleFocus() {
        selected = !selected;
    }

    public void setP(String pic) {
        this.focus = new ImageIcon(this.getClass().getResource("/assets/focus.png")).getImage();
        this.pic = new ImageIcon(this.getClass().getResource(pic)).getImage();
        setPic(this.pic);
    }

}
