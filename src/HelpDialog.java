import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 显示帮助信息的对话框
 */
public class HelpDialog extends JDialog implements ActionListener {

    // 确认按钮
    private JButton okay;

    // 取消按钮
    private JButton cancel;

    public HelpDialog(Frame parent,String title) {
        super(parent,title,true); //不可缺少true
        addText();
        okay.addActionListener(this);
        cancel.addActionListener(this);
        pack();
        show();
    }

    // 显示面板
    public void addText() {
        setLocation(300, 210);
        okay = new JButton("确认");
        cancel = new JButton("取消");
        Panel button = new Panel();
        button.setLayout(new FlowLayout());
        button.add(okay);
        button.add(cancel);
        setLayout(new GridLayout(5,1));
        add(new Label("传教士野人过河问题是一个经典的人工智能问题，问题描述如下："));
        add(new Label("有N个传教士和N个野人过河，只有一条能装下K个人（包括野人）的船，K<N，"));
        add(new Label("在河的任何一方或者船上，如果有野人和传教士在一起，必须要求传教士的人数多于或等于野人的人数。"));
        add(new Label("本程序使用A*搜索算法实现求解传教士野人过河问题，对任意N、K有解时给出其解决方案。"));
        add(button);
    }

    // 监听事件
    public void actionPerformed(ActionEvent ae) {
        //隐藏对话框
        dispose();
    }

};