/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assets;

/**
 *
 * @author yan
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MyFrame extends JFrame {

    Container c;
    // 儲存預設關閉參數字串                                               
    String closeOperationName[] = {
        "WindowConstants.DO_NOTHING_ON_CLOSE",
        "WindowConstants.HIDE_ON_CLOSE",
        "WindowConstants.DISPOSE_ON_CLOSE"};
    // MyFrame 的建構子

    public MyFrame() {
        super("MyFrame.java: JFrame測試");
        c = getContentPane();                                    // 取得container
        setSize(800, 600);		                 // 設定 frame的size
        show();
    }
    // 畫出目前 MyFrame 的外觀

    public void paint(Graphics g) {
        g.setFont(new Font("Serif", Font.BOLD, 14));	// 設定字型
        // 取得目前物件的資訊與 Container 的資料
        g.drawString("JFrame: " + paramString(), 10, 50);
        g.drawString("Container: " + c.toString(), 10, 80);
        g.drawString("Close Operation: "
                + closeOperationName[getDefaultCloseOperation()], 10, 110);
        
        Font fnt0 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString("Gems Crush", 50, 100);
    }
}
