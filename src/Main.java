import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {
    public JFrame mainFrame;
    public JPanel panel;
    public JPanel bigPanel;
    public JTextArea enterStartPage;
    public JTextArea enterEndPage;
    public JLabel urlText;
    public JLabel termText;
    public JTextArea results;
    public JButton searchButton;
    public JScrollPane scrollBar;

    public String currentLink;
    public String linkList;
    public String searchTerm;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        prepareGUI();
        searchLink();
    }

    public void searchLink(){
        linkList = "Results:\n";
        try {
            URL url = new URL(enterStartPage.getText());
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while ( (line = reader.readLine()) != null ) {
                if(line.contains("href=\"http")) {
                    currentLink = (line.substring((line.indexOf("href=\"http") + 6), (line.indexOf("\"", line.indexOf("href=\"http") + 6))));
                    if(currentLink.contains(searchTerm)) {
                        if(!linkList.contains(currentLink)){
                            linkList = linkList.concat(currentLink + "\n");
                        }
                    }
                }
            }
            reader.close();

            if(linkList.equals("Results:\n")){
                results.setText("no results found");
            } else {
                results.setText(linkList);
                System.out.println(linkList);
            }
        } catch(Exception ex) {
            results.setText("please enter a valid link");
        }
    }

    public void prepareGUI(){
        mainFrame = new JFrame("Html Project");
        mainFrame.setLayout(new GridLayout(2,1));

        bigPanel = new JPanel();
        bigPanel.setLayout(new BorderLayout());
        panel = new JPanel();
        panel.setLayout(new GridLayout(2,2));

        urlText = new JLabel("URL:", JLabel.CENTER);
        termText = new JLabel("Term:", JLabel.CENTER);

        enterStartPage = new JTextArea();
        enterStartPage.setBounds(50,5,700,650);
        enterEndPage = new JTextArea();
        enterEndPage.setBounds(50,5,700,650);

        searchButton = new JButton("Search");
        searchButton.setActionCommand("Search");
        searchButton.addActionListener(new ButtonClickListener());

        results = new JTextArea();
        results.setLineWrap(true);
        scrollBar = new JScrollPane(results);
        scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(urlText);
        panel.add(enterStartPage);
        panel.add(termText);
        panel.add(enterEndPage);
        bigPanel.add(panel, BorderLayout.CENTER);
        bigPanel.add(searchButton, BorderLayout.SOUTH);
        mainFrame.add(bigPanel);
        mainFrame.add(scrollBar);
        mainFrame.setSize(800,700);
        mainFrame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("Search")) {
                System.out.println(enterStartPage.getText());
                searchTerm = enterEndPage.getText();
                if(searchTerm.equals("")) {
                    results.setText("please enter a search term");
                } else {
                    searchLink();
                }
            }
        }
    }
}
