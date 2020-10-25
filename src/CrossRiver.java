import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.Locale;
import javax.swing.*;
import javax.swing.event.*;

public class CrossRiver extends JFrame implements ActionListener {

    private final JMenuItem exit = new JMenuItem("退出");

    private final JMenuItem start = new JMenuItem("开始");

    private final JMenuItem stop = new JMenuItem("暂停");

    private final JMenuItem continu = new JMenuItem("继续");

    private final JMenuItem helpMSG = new JMenuItem("帮助信息");

    private final JButton bstart = new JButton("开始");

    private final JButton bstop = new JButton("暂停");

    private final JButton bcontinu = new JButton("继续");

    private final JLabel setparam = new JLabel("参数设置");

    private final Label mcnumber = new Label("野人传教士各");

    private final Label cannumber = new Label("船上最多可载");

    private final Label cmren = new Label("人");

    private final Label canren = new Label("人");

    private final Label Nspace = new Label(" ");

    private final Label Sspace = new Label(" ");

    private final Label Bspace = new Label(" ");

    private final Label quick = new Label("快");

    private final Label slow = new Label("慢");

    private final JLabel changerate = new JLabel("速度调节");

    private final JSlider showrate = new JSlider();

    private final JLabel control = new JLabel("演示控制");

    private final JTextField tmcnumber = new JTextField(8);

    private final JTextField tcannumber = new JTextField(8);

    private final DrawCanvas drawing = new DrawCanvas();

    public CrossRiver() {
        super("野人传教士过河问题");
        this.panel();
        this.helpMSG.addActionListener(this);
        this.exit.addActionListener(this);
        this.start.addActionListener(this);
        this.stop.addActionListener(this);
        this.continu.addActionListener(this);
        this.bstart.addActionListener(this);
        this.bstop.addActionListener(this);
        this.bcontinu.addActionListener(this);
        this.addWindowListener(new CrossRiver.WindowCloser());
        ChangeListener listener = new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                JSlider slider = (JSlider) event.getSource();
                if (slider == CrossRiver.this.showrate) {
                    CrossRiver.this.drawing.sleepTime = CrossRiver.this.showrate.getValue();
                }

            }
        };
        this.showrate.addChangeListener(listener);
        this.setSize(862, 583);
        this.show();
    }

    private class WindowCloser extends WindowAdapter {
        private WindowCloser() {}
        public void windowClosing(WindowEvent we) {
            System.exit(0);
        }
    }

    private void panel() {
        this.setLocation(90, 90);
        JMenu menu = new JMenu("操作");
        menu.add(this.start);
        menu.add(this.exit);
        JMenu menu2 = new JMenu("演示控制");
        menu2.add(this.stop);
        menu2.add(this.continu);
        JMenu menu3 = new JMenu("帮助");
        menu3.add(this.helpMSG);
        JMenuBar menubar = new JMenuBar();
        menubar.add(menu);
        menubar.add(menu2);
        menubar.add(menu3);
        this.setJMenuBar(menubar);
        JPanel cminput = new JPanel();
        cminput.setLayout(new FlowLayout());
        cminput.add(this.tmcnumber);
        cminput.add(this.cmren);
        JPanel caninput = new JPanel();
        caninput.setLayout(new FlowLayout());
        caninput.add(this.tcannumber);
        caninput.add(this.canren);
        JPanel head1 = new JPanel();
        head1.setLayout(new FlowLayout());
        head1.add(this.setparam);
        JPanel setpanel = new JPanel();
        setpanel.setLayout(new GridLayout(5, 1));
        setpanel.add(head1);
        setpanel.add(this.mcnumber);
        setpanel.add(cminput);
        setpanel.add(this.cannumber);
        setpanel.add(caninput);
        JPanel head2 = new JPanel();
        head2.setLayout(new FlowLayout());
        head2.add(this.changerate);
        JPanel center = new JPanel();
        center.setLayout(new BorderLayout());
        center.add("West", this.quick);
        center.add("North", this.showrate);
        center.add("East", this.slow);
        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(new FlowLayout());
        buttonpanel.add(this.bstart);
        JPanel ratepanel = new JPanel();
        ratepanel.setLayout(new GridLayout(5, 1));
        ratepanel.add(this.Nspace);
        ratepanel.add(head2);
        ratepanel.add(center);
        ratepanel.add(buttonpanel);
        ratepanel.add(this.Sspace);
        JPanel head3 = new JPanel();
        head3.setLayout(new FlowLayout());
        head3.add(this.control);
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.add(this.bstop);
        buttons.add(this.bcontinu);
        JPanel controlpanel = new JPanel();
        controlpanel.setLayout(new GridLayout(3, 1));
        controlpanel.add(head3);
        controlpanel.add(buttons);
        controlpanel.add(this.Bspace);
        JPanel left = new JPanel();
        left.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "操作"));
        left.setLayout(new BorderLayout());
        left.add("North", setpanel);
        left.add("Center", ratepanel);
        left.add("South", controlpanel);
        JPanel right = new JPanel();
        right.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "详细信息"));
        right.setLayout(new BorderLayout());
        right.add("Center", this.drawing);
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        container.add("West", left);
        container.add("Center", right);
        this.bstop.setEnabled(false);
        this.bcontinu.setEnabled(false);
        this.stop.setEnabled(false);
        this.continu.setEnabled(false);
        this.setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.bstart) {
            this.start();
        } else if (e.getSource() == this.bstop) {
            this.drawing.halt();
            this.bcontinu.setEnabled(true);
            this.bstop.setEnabled(false);
            this.continu.setEnabled(true);
            this.stop.setEnabled(false);
        } else if (e.getSource() == this.bcontinu) {
            this.drawing.next();
            this.bcontinu.setEnabled(false);
            this.bstop.setEnabled(true);
            this.continu.setEnabled(false);
            this.stop.setEnabled(true);
        } else if (e.getSource() == this.start) {
            this.start();
        } else if (e.getSource() == this.stop) {
            this.drawing.halt();
            this.bcontinu.setEnabled(true);
            this.bstop.setEnabled(false);
            this.continu.setEnabled(true);
            this.stop.setEnabled(false);
        } else if (e.getSource() == this.exit) {
            System.exit(0);
        } else if (e.getSource() == this.continu) {
            this.drawing.next();
            this.bcontinu.setEnabled(false);
            this.bstop.setEnabled(true);
            this.continu.setEnabled(false);
            this.stop.setEnabled(true);
        } else if (e.getSource() == this.helpMSG) {
            new HelpDialog(this, "程序帮助文档");
        }

    }

    private void start() {
        int kofmc = this.getValue(this.tcannumber.getText());
        int nofmc = this.getValue(this.tmcnumber.getText());
        if (kofmc >= 0 && nofmc >= 0) {
            AStar astar = new AStar(nofmc, kofmc);
            astar.start();
            if (astar.found == 1) {
                this.drawing.start(astar);
                this.bstop.setEnabled(true);
                this.stop.setEnabled(true);
                this.bcontinu.setEnabled(false);
                this.continu.setEnabled(false);
            } else {
                this.drawing.noSolution(astar.found);
            }

        } else {
            this.drawing.inputError();
            System.out.println("输入不合法！");
        }
    }

    private int getValue(String str) {
        int retVal = -1;
        if (!str.equals("")) {
            NumberFormat integerFormatter = null;
            integerFormatter = NumberFormat.getNumberInstance(Locale.getDefault());
            integerFormatter.setParseIntegerOnly(true);
            try {
                retVal = integerFormatter.parse(str).intValue();
            } catch (ParseException var5) {
                System.out.println("类型转换错误！");
            }

        }
        return retVal;
    }

}
