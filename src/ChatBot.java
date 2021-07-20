import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.stream.IntStream;

public class ChatBot extends JFrame implements KeyListener {
    JPanel p = new JPanel();
    JTextArea dialog = new JTextArea(20, 50);
    JTextArea input = new JTextArea(1, 50);
    JScrollPane scroll = new JScrollPane(
            dialog,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
    );

    public static void main(String[] args) {
        new ChatBot();
    }

    public ChatBot() {
        super("Chat Bot");
        setSize(600, 400);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        dialog.setEditable(false);
        input.addKeyListener(this);

        p.add(scroll);
        p.add(input);
        p.setBackground(new Color(255, 200, 0));
        add(p);

        setVisible(true);
    }

    String[][] chatBot = {
            //standard greeting
            {"hi", "hello", "ola", "howdy"},
            {"hi", "hello", "hey"},
            //question greeting
            {"how are you", "how r you", "how r u", "how are u"},
            {"good", "doing well"},
            //yes
            {"yes"},
            {"no", "NO", "NO!!!!!!"},
            //no
            {"no", "nee", "not"},
            {"yee", "yes", "ai", "oui"},
            //default
            {"shut up", "you're bad", "noob", "stop talking",
                    "(michael is unavailable, due to LOL)"}
    };

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            input.setEditable(false);
            String quote = input.getText();
            input.setText("");
            addText("-->You:\t" + quote);

            quote = doTrimAndEliminateSomeSignsAtTheEnd(quote.trim());

            boolean weHaveAnswerInArray = false;
            for (int i = 0; i < chatBot.length - 1; i += 2) {  //even i - question, odd i - answer
                if (inArray(quote.toLowerCase(), chatBot[i])) {
                    int r = (int) Math.floor(Math.random() * chatBot[i + 1].length);
                    addText("\n-->Michael\t" + chatBot[i + 1][r]);
                    weHaveAnswerInArray = true;
                    break;
                }
            }
            if (!weHaveAnswerInArray) { //working with the last part of ChatBot array
                int r = (int) Math.floor(Math.random() * chatBot[chatBot.length - 1].length);
                addText("\n-->Michael\t" + chatBot[chatBot.length - 1][r]);
            }
//            byte response = 0;
//            /*
//            0: we're searching through chatBot[][] for matches
//            1: we didn't find anything in ChatBot[][]
//            2: we did find something in ChatBot[][]
//             */
//            // ---check for matches---
//            int j = 0;
//            while (response == 0) {
//
//                if (inArray(quote.toLowerCase(), chatBot[j * 2])) {
//                    response = 2;
//                    int r = (int) Math.floor(Math.random() * chatBot[(j * 2) + 1].length);
//                    addText("\n-->Michael\t" + chatBot[(j * 2) + 1][r]);
//                }
//
//                j++;
//
//                if (j * 2 == chatBot.length - 1 && response == 0) {
//                    response = 1;
//                }
//
//            }
//            // ---default---
//            if (response == 1) {
//                int r = (int) Math.floor(Math.random() * chatBot[chatBot.length - 1].length);
//                addText("\n-->Michael\t" + chatBot[chatBot.length - 1][r]);
//            }
            addText("\n");
        }
    }

    private String doTrimAndEliminateSomeSignsAtTheEnd(String quote) {
        while (
                quote.charAt(quote.length() - 1) == '!' ||
                        quote.charAt(quote.length() - 1) == '.' ||
                        quote.charAt(quote.length() - 1) == '?'
        ) {
            quote = quote.substring(0, quote.length() - 1);
        }
        return quote.trim();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            input.setEditable(true);
        }
    }

    private void addText(String str) {
        dialog.setText(dialog.getText() + str);
    }

    private boolean inArray(String in, String[] str) {
        return IntStream.range(0, str.length).anyMatch(i -> str[i].equals(in));
    }
}
