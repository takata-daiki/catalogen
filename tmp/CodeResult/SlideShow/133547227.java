
import FI.realitymodeler.*;
import FI.realitymodeler.common.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class SlideShow extends Applet implements ActionListener, ItemListener, Runnable
{
    Thread t;
    Label label;
    Button pause, stop;
    String authorization;
    boolean paused = false;
    Checkbox automatic, manual;
    W3Lock lock = new W3Lock();
    long delayValue, delay[] = new long[1];
    TextField placeField, delayField, username, password;
    BASE64Encoder b64e = new BASE64Encoder();

    public void init()
    {
        delay[0] = delayValue = 30000;
        String file = getDocumentBase().getFile();
        int i = file.indexOf('?');
        if (i != -1) authorization = file.substring(i + 1);
        Frame frame = new Frame(getClass().getName());
        GridBagLayout gbl = new GridBagLayout();
        frame.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        Label l = new Label("Place");
        gbc.anchor = gbc.WEST;
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        gbl.setConstraints(l, gbc);
        frame.add(l);
        placeField = new TextField(80);
        String place = getParameter("place");
        if (place == null) place = getDocumentBase().getRef();
        if (place != null) placeField.setText(getDocumentBase().getRef());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbl.setConstraints(placeField, gbc);
        frame.add(placeField);
        placeField.addActionListener(this);
        l = new Label("Delay");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbl.setConstraints(l, gbc);
        frame.add(l);
        delayField = new TextField(10);
        delayField.setText(String.valueOf(delay[0] / 1000));
        gbc.gridx = 1;
        gbl.setConstraints(delayField, gbc);
        frame.add(delayField);
        delayField.addActionListener(this);
        pause = new Button("Pause");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbl.setConstraints(pause, gbc);
        frame.add(pause);
        pause.addActionListener(this);
        stop = new Button("Stop");
        gbc.gridx = 1;
        gbl.setConstraints(stop, gbc);
        frame.add(stop);
        stop.addActionListener(this);
        CheckboxGroup cbg = new CheckboxGroup();
        automatic = new Checkbox("Automatic", cbg, true);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbl.setConstraints(automatic, gbc);
        frame.add(automatic);
        automatic.addItemListener(this);
        manual = new Checkbox("Manual", cbg, false);
        gbc.gridx = 1;
        gbl.setConstraints(manual, gbc);
        frame.add(manual);
        manual.addItemListener(this);
        l = new Label("Username");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbl.setConstraints(l, gbc);
        frame.add(l);
        username = new TextField(16);
        gbc.gridx = 1;
        gbl.setConstraints(username, gbc);
        frame.add(username);
        l = new Label("Password");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbl.setConstraints(l, gbc);
        frame.add(l);
        password = new TextField(16);
        password.setEchoChar('#');
        gbc.gridx = 1;
        gbl.setConstraints(password, gbc);
        frame.add(password);
        label = new Label("stopped");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbl.setConstraints(label, gbc);
        frame.add(label);
        frame.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent e)
                {
                    if (t == null || !t.isAlive()) return;
                    if (manual.getState()) lock.release();
                    else t.interrupt();
                }

            });
        frame.addWindowListener(new WindowAdapter() {

                public void windowClosing(WindowEvent event)
                {
                    event.getWindow().dispose();
                    destroy();
                }

            });
        frame.setSize(300, 200);
        frame.setVisible(true);
        t = new Thread(this);
        if (place != null && !place.equals("")) t.start();
    }

    URLConnection getDocument(URL url) throws IOException
    {
        URLConnection uc = url.openConnection();
        if (authorization != null) uc.setRequestProperty("Authorization", authorization);
        return uc;
    }

    public void run()
    {
        try {
            if (!username.getText().trim().equals("")) {
                String s = b64e.encodeStream((username.getText() + ":" + password.getText()).getBytes());
                authorization = "Basic " + s.substring(0, s.length() - 1);
            }
            String s = placeField.getText().trim();
            lock.release();
            label.setText("started");
            if (s.equals("") || s.equals("?")) {
                URL url = new URL(getCodeBase(), "/servlet/public/ClientRobot");
                for (;;) {
                    if (manual.getState()) lock.lock();
                    URLConnection uc = getDocument(url);
                    InputStream in = uc.getInputStream();
                    if ((s = uc.getHeaderField("location")) == null) break;
                    getAppletContext().showDocument(new URL(s), "document");
                    in.close();
                    if (automatic.getState()) {
                        Thread.sleep(delay[0]);
                        lock.lock();
                        lock.release();
                    }
                }
            }
            else {
                URL url;
                RegexpPool filter = null;
                if (!s.startsWith("?")) {
                    int i;
                    if ((i = s.indexOf('*')) != -1) {
                        url = new URL(s.substring(0, (i = s.lastIndexOf('/', i) + 1)));
                        try {
                            (filter = new RegexpPool()).add(s.substring(i), Boolean.TRUE);
                        }
                        catch (RegexException ex) {
                            filter = null;
                        }
                    }
                    else url = new URL(getCodeBase(), s);
                }
                else url = new URL(getCodeBase(), "/servlet/public/ClientRobot?query=" + URLEncoder.encode(s.substring(1)));
                URLConnection uc = getDocument(url);
                InputStream in = uc.getInputStream();
                Vector urls = new Vector();
                urls.addElement(url);
                W3URLConnection.parse(in, urls, null, null, url, null, null, null, null, this, filter,
                                      manual.getState() ? null : delay, lock, (s = uc.getContentType()) != null &&
                                      Support.getParameters(s, null).equalsIgnoreCase("text/html"), false);
                in.close();
            }
        } catch (Exception ex) {
            if (ex instanceof InterruptedException) return;
            showStatus(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        try {
            if (source == pause) {
                if (t == null || !t.isAlive()) return;
                if (manual.getState()) {
                    lock.release();
                    return;
                }
                if (paused) lock.release();
                else lock.lock();
                label.setText((paused = !paused) ? "paused" : "started");
            }
            else if (source == stop) {
                if (t == null || !t.isAlive()) return;
                t.interrupt();
                t = null;
                paused = false;
                label.setText("stopped");
                return;
            }
            else if (source == placeField) {
                if (t != null && t.isAlive()) t.interrupt();
                t = new Thread(this);
                t.start();
                paused = false;
                return;
            }
            else if (source == delayField) {
                delayValue = Integer.parseInt(delayField.getText()) * 1000;
                if (delay[0] != -1) delay[0] = delayValue;
                return;
            }
        }
        catch (InterruptedException ex) {}
    }

    public void itemStateChanged(ItemEvent e)
    {
        Object source = e.getSource();
        if (source == automatic) {
            delay[0] = delayValue;
            lock.release();
        }
        else if (source == manual) delay[0] = -1;
    }

}
