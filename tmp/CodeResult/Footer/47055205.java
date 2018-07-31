/*
 * The MIT License
 *
 * Copyright (c) 2013, DirectMyFile
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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