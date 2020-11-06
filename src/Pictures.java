import java.awt.*;

public class Pictures extends Thread {

    private int x = 80, y = 150, j;

    Image boatL = Toolkit.getDefaultToolkit().getImage("./res/brp.gif");

    Image boatR = Toolkit.getDefaultToolkit().getImage("./res/blp.gif");

    Image mp = Toolkit.getDefaultToolkit().getImage("./res/mp.gif");

    Image cp = Toolkit.getDefaultToolkit().getImage("./res/cp.gif");

    private DrawCanvas drawcanvas;

    private boolean drawflag = false;

    private int mnumber;  //传教士数

    private int cnumber;  //野人数

    private int bnumber;  //船

    public int mnumber2;  //对岸传教士数

    public int cnumber2;  //对岸野人数

    public int bnumber2;  //对岸船

    private AStar astar = null;

    private int mbno;  //传教士数

    private int cbno;  //野人数

    private String strmbno;

    private String strcbno;

    private String info;

    public int sleepTime;

    public int i;

    private int mcnumber=0; //总人数

    private int number=0; //节点数

    private int Start=0;

    private int numR=0;

    public int num=0;  //当前节点下标

    private Display playL = new Display();

    private Display playR = new Display();

    public Pictures(DrawCanvas drawcanvas,int i) {
        this.drawcanvas = drawcanvas;
        this.i = i;
    }

    public void start(AStar astar) {
        this.astar = astar;
        mcnumber = this.astar.nofmc;
        Start = this.astar.Start;
        number = this.astar.num;
        num = -1;
        drawflag = true;
        i = -2;
        super.start();
    }

    public void halt() {
        drawflag = false;
    }

    public void next(AStar astar, int num, int mNumber2, int cNumber2, int bNumber2) {
        this.astar = astar;
        mcnumber = this.astar.nofmc;
        Start = this.astar.Start;
        number = this.astar.num;
        this.num = num;
        playR.missi = mNumber2;
        playR.canni = cNumber2;
        playR.boat = bNumber2;
        drawflag = true;
        super.start();
    }

    public void run() {
        while(drawflag) {
            try {
                sleep(sleepTime*15+10);
            } catch(InterruptedException ie) {
                System.err.println("线程中断！");
            }
            i = (i+1)%14;
            drawcanvas.repaint();
            if(i == 0 || i == 1) {
                num++;
                if(num >= number) {
                    drawflag = false;
                }
            }
        }
    }

    public void draw(Graphics g) {
        int mi,ci;
        if(Start == 1 && mcnumber != 0 && num >= 0) {
            // 左岸状态
            playL.missi = astar.dp[num].missi;
            playL.canni = astar.dp[num].canni;
            playL.boat = astar.dp[num].boat;
            mnumber = playL.missi;
            cnumber = playL.canni;
            bnumber = playL.boat;
            // 右岸下船
            if(i == 7) {
                playR.missi = mcnumber - mnumber;
                playR.canni = mcnumber - cnumber;
                playR.boat = 1;
            }
            // 右岸上船
            if(i == 8) {
                numR = num+1;
                playR.missi = mcnumber - astar.dp[numR].missi;
                playR.canni = mcnumber - astar.dp[numR].canni;
                playR.boat = 0;
            }
            mnumber2 = playR.missi;
            cnumber2 = playR.canni;
            bnumber2 = playR.boat;
            // 绘制左岸人物
            if(mnumber > 0) {
                for(int mn = 0; mn < mnumber; mn++) {
                    g.drawImage(mp,x+20,(int)(mn*160/mnumber)+20,35,46,drawcanvas);
                }
            }
            if(cnumber > 0) {
                for(int cn = 0; cn < cnumber; cn++) {
                    g.drawImage(cp,x-40,(int)(cn*160/cnumber)+100,35,46,drawcanvas);
                }
            }
            // 绘制右岸人物
            if(mnumber2 > 0) {
                for(int mn2 = 0; mn2 < mnumber2; mn2++) {
                    g.drawImage(mp,x+490,(int)(mn2*160/mnumber2)+130,35,46,drawcanvas);
                }
            }
            if(cnumber2 > 0) {
                for(int cn2 = 0; cn2 < cnumber2; cn2++) {
                    g.drawImage(cp,x+445,(int)(cn2*160/cnumber2)+230,35,46,drawcanvas);
                }
            }
            // 绘制船
            if(i == 0 || i == 1 || i == -1) {
                g.drawImage(boatL,x+65,y+5,200,130,drawcanvas);
                // 人上船
                if(i == 1) {
                    mbno = mcnumber - (mnumber + mnumber2);
                    cbno = mcnumber - (cnumber + cnumber2);
                    if(mbno > 0) {
                        for(mi = 0; mi < mbno; mi++) {
                            g.drawImage(mp,x+130-(int)(mi*80)/mbno,y+65,25,36,drawcanvas);
                        }
                    }
                    if(cbno > 0) {
                        for(ci = 0; ci < cbno; ci++) {
                            g.drawImage(cp,x+155+(int)(ci*80)/cbno,y+65,25,36,drawcanvas);
                        }
                    }
                }
            } else if(i > 1 && i < 7) { //由左向右开船
                j = i-1;
                g.drawImage(boatL,x+65+j*45,y+5+j*8,200,130,drawcanvas);
                mbno = mcnumber - (mnumber + mnumber2);
                cbno = mcnumber - (cnumber + cnumber2);
                if(mbno > 0) {
                    for(mi = 0; mi < mbno; mi++) {
                        g.drawImage(mp,x+130+j*45-(int)(mi*80)/mbno,y+65+j*8,25,36,drawcanvas);
                    }
                }
                if(cbno > 0) {
                    for(ci = 0; ci < cbno; ci++) {
                        g.drawImage(cp,x+155+j*45+(int)(ci*80)/cbno,y+65+j*8,25,36,drawcanvas);
                    }
                }
                strmbno = "" + mbno;
                strcbno = "" + cbno;
                info = "→→→运送" + strmbno + "个传教士，" + strcbno + "个野人→→→";
                g.setColor(Color.red);
                g.drawString(info,200,360);
            } else if(i == 7 || i == 8) {
                g.drawImage(boatR,x+215,y+40,200,130,drawcanvas);
                // 人上船
                if(i==8) {
                    mbno = mcnumber - (mnumber + mnumber2);
                    cbno = mcnumber - (cnumber + cnumber2);
                    if(mbno > 0) {
                        for(mi = 0; mi < mbno; mi++) {
                            g.drawImage(mp,x+325-(int)(mi*80)/mbno,y+100,25,36,drawcanvas);
                        }
                    }
                    if(cbno > 0) {
                        for(ci = 0; ci < cbno; ci++) {
                            g.drawImage(cp,x+340+(int)(ci*80)/cbno,y+100,25,36,drawcanvas);
                        }
                    }
                }
            } else if(i > 8 && i < 14) { //由右向左开船
                j=i-8;
                g.drawImage(boatR,x+215-j*45,y+40-j*8,200,130,drawcanvas);
                mbno = mcnumber - (mnumber + mnumber2);
                cbno = mcnumber - (cnumber + cnumber2);
                if(mbno > 0) {
                    for(mi = 0; mi < mbno; mi++) {
                        g.drawImage(mp,x+325-j*45-(int)(mi*80)/mbno,y+100-j*8,25,36,drawcanvas);
                    }
                }
                if(cbno > 0) {
                    for(ci = 0; ci < cbno; ci++) {
                        g.drawImage(cp,x+340-j*45+(int)(ci*80)/cbno,y+100-j*8,25,36,drawcanvas);
                    }
                }
                strmbno = "" + mbno;
                strcbno = "" + cbno;
                info = "←←←运送" + strmbno + "个传教士，" + strcbno + "个野人←←←";
                g.setColor(Color.red);
                g.drawString(info,200,360);
            }
            // 结束标记
            if(i == 7 && mnumber == 0 && cnumber == 0) {
                g.drawImage(boatR,x+215,y+40,200,130,drawcanvas);
                drawflag = false;
            }
        }
    }

}