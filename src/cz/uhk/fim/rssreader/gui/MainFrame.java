package cz.uhk.fim.rssreader.gui;

import cz.uhk.fim.rssreader.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainFrame extends JFrame {

    private JTextField txtInputField;
    private JLabel lblError;
    private int color;
    private boolean isLoadError = false;
    private boolean isSaveError = false;
    private boolean isSaveSuccess = false;

    public MainFrame(){
        init();
    }

    public void init(){
        setTitle("RSSreader");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initContentUI();
    }

    private void initContentUI(){
        //TODO - listenery na Load a Save buttony - DONE
        //TODO - napsat metodu validateInput() - DONE - showErrorMessage()
        //TODO - přidat JLabel lblError - pod nebo nad txtInputField - ČERVENÁ BARVA, např. showErrorMessage() - DONE
        JPanel controlPanel = new JPanel(new BorderLayout());

        JButton btnLoad = new JButton("Load");
        JButton btnSave = new JButton("Save");
        txtInputField = new JTextField();
        lblError = new JLabel("", SwingConstants.CENTER);

        controlPanel.add(btnLoad, BorderLayout.WEST);
        controlPanel.add(btnSave, BorderLayout.EAST);
        controlPanel.add(txtInputField, BorderLayout.CENTER);
        controlPanel.add(lblError, BorderLayout.SOUTH);
        lblError.setVisible(false);

        add(controlPanel, BorderLayout.NORTH);

        JTextArea txtContent = new JTextArea();
        add(new JScrollPane(txtContent), BorderLayout.CENTER);

        btnLoad.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("loading...");
                if(!txtInputField.getText().equals("rss")){
                    txtContent.setText("");
                }
                try {
                    lblError.setVisible(false);
                    txtContent.setText(FileUtils.loadStringFromFile("RSSfeeder/" + txtInputField.getText()));
                    System.out.println("LOADED");
                } catch (IOException ex) {
                    isLoadError = true;
                    showErrorMessage();
                }
            }
        });

        btnSave.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("saving...");
                try{
                    FileUtils.saveStringToPath("RSSfeeder/test.txt", txtContent.getText().getBytes());
                    if(txtContent.getText().trim().isEmpty()){
                        isSaveError = true;
                        showErrorMessage();
                        isSaveError = false;
                    }else{
                        isSaveSuccess = true;
                        succesOnSave();
                    }
                }catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
        });
    }
    public void showErrorMessage(){
        if(isLoadError) {
            lblError.setBackground(Color.RED);
            lblError.setOpaque(true);
            lblError.setText("LOADING ERROR!");
            lblError.setVisible(true);
        }
        if(isSaveError) {
            lblError.setBackground(Color.RED);
            lblError.setOpaque(true);
            lblError.setText("SAVING ERROR!");
            lblError.setVisible(true);
        }
    }

    public void succesOnSave(){
        if(isSaveSuccess){
            lblError.setBackground(Color.GREEN);
            lblError.setOpaque(true);
            lblError.setText("SAVE SUCCESFUL!");
            lblError.setVisible(true);
        }
    }
}

