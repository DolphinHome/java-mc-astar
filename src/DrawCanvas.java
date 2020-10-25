import javax.swing.*;
import java.awt.*;
import java.text.*;
import java.awt.font.*;

public class DrawCanvas extends JPanel {

    private int x = 0, y = 0,i,num,mnumber2,cnumber2,bnumber2;

    private Image River = Toolkit.getDefaultToolkit().getImage("res/River.jpg");

    private AStar astar = null;

    private Pictures drawPic = new Pictures(this,-1);

    public int sleepTime = 50;

    private int start = 0;

    public DrawCanvas() {
        show();
    }

    public void paint(Graphics g) {
        g.drawImage(River,x,y,630,495,this);
        if(start == 1) {
            drawPic.sleepTime = sleepTime;
            drawPic.draw(g);
        } else if(start == -1) {
            Font serifFont = new Font("楷体", Font.PLAIN, 60);
            AttributedString as = new AttributedString("没有找到解决方案！");
            as.addAttribute(TextAttribute.FONT, serifFont);
            as.addAttribute(TextAttribute.FOREGROUND, Color.red);
            g.drawString(as.getIterator(), 70,210);
        } else if(start == -2) {
            Font serifFont = new Font("楷体", Font.PLAIN, 50);
            AttributedString as = new AttributedString("请输入数字！");
            as.addAttribute(TextAttribute.FONT, serifFont);
            as.addAttribute(TextAttribute.FOREGROUND, Color.red);
            g.drawString(as.getIterator(), 50,210);
        } else if(start == -3) {
            Font serifFont = new Font("楷体", Font.PLAIN, 50);
            AttributedString as = new AttributedString("数据太大难以计算！");
            as.addAttribute(TextAttribute.FONT, serifFont);
            as.addAttribute(TextAttribute.FOREGROUND, Color.red);
            g.drawString(as.getIterator(), 30,210);
        }
    }

    public void start(AStar astar) {
        this.astar = astar;
        drawPic.halt();
        drawPic = new Pictures(this,-1);
        drawPic.start(this.astar);
        start = 1;
    }

    public void halt() {
        drawPic.halt();
        i = drawPic.i;
        num = drawPic.num;
        mnumber2 = drawPic.mnumber2;
        cnumber2 = drawPic.cnumber2;
        bnumber2 = drawPic.bnumber2;
    }

    public void next() {
        drawPic.halt();
        drawPic = new Pictures(this,i);
        drawPic.next(astar,num,mnumber2,cnumber2,bnumber2);
    }

    public void noSolution(int found) {
        drawPic.halt();
        start = found == 0 ? -1 : -3;
        repaint();
    }

    public void inputError() {
        drawPic.halt();
        start = -2;
        repaint();
    }

}