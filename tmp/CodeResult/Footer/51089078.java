package kaendfinger.kenbot.gui;

import kaendfinger.kenbot.api.utils.BotUtils;

import javax.swing.*;
import java.awt.*;

public class Footer extends JPanel {

    private static DefaultComboBoxModel<String> channels = new DefaultComboBoxModel<>();
    private static JTextField chatBox = new JTextField("");

    public Footer() {
        add(channels(), BorderLayout.WEST);
        add(chat(), BorderLayout.EAST);
    }

    private JPanel chat() { // TODO: Make the size dynamic to the channel box size
        JPanel chatPanel = new JPanel();
        chatBox.setColumns(43);
        chatPanel.add(chatBox);
        return chatPanel;
    }

    private JPanel channels() {
        JPanel channelPanel = new JPanel();
        JComboBox<String> box = new JComboBox<>();
        box.setModel(channels);
        channelPanel.add(box);
        return channelPanel;
    }

    public static void updateChatBox() {
        if (!BotUtils.getBot().isConnected()) {
            channels.removeAllElements();
            return;
        }
        for (String channel : BotUtils.getBot().getChannelsNames()) {
            if (channels.getIndexOf(channel) == -1) {
                channels.addElement(channel);
            }
        }
        for (int i = 0; i < channels.getSize(); i++) {
            String chan = channels.getElementAt(i);
            if (!BotUtils.getBot().getChannelsNames().contains(chan)) {
                channels.removeElement(chan);
            }
        }
    }

}