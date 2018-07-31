/*
* This file is part of WebLookAndFeel library.
*
* WebLookAndFeel library is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* WebLookAndFeel library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alee.examples.groups;

import com.alee.extended.background.NinePatchBackgroundPainter;
import com.alee.laf.StyleConstants;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.hotkey.HotkeyManager;
import com.alee.managers.hotkey.HotkeyRunnable;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;
import com.alee.managers.tooltip.WebCustomTooltipStyle;
import com.alee.utils.ImageUtils;
import com.alee.utils.LafUtils;
import com.alee.utils.SwingUtils;
import com.alee.utils.swing.Timer;
import info.clearthought.layout.TableLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

/**
 * User: mgarin Date: 14.02.12 Time: 16:01
 */

public class SlidingSearch
{
    public static ImageIcon searchIcon =
            new ImageIcon ( SlidingSearch.class.getResource ( "icons/search.png" ) );

    private boolean searchShown = false;

    private int contentSpacing = 4;
    private int shadeWidth = 5;
    private int topRound = 16;
    private int bottomRound = 6;
    private boolean connected = true;

    private WebPanel searchPanel;
    private SlideLayout slideLayout;

    private WebPanel searchPlate;
    private UpperLine leftLine;
    private UpperLine rightLine;

    private WebTextField searchField;

    public SlidingSearch ( final JLayeredPane layeredPane )
    {
        super ();


        final WebPanel topPanel = new WebPanel ();
        topPanel.setLayout ( new TableLayout (
                new double[][]{ { TableLayout.FILL, TableLayout.PREFERRED, TableLayout.FILL },
                        { TableLayout.PREFERRED } } ) );
        topPanel.setOpaque ( false );

        searchPanel = new WebPanel ()
        {
            public boolean contains ( int x, int y )
            {
                return topPanel.getBounds ().contains ( x, y ) && super.contains ( x, y );
            }
        };
        searchPanel.setOpaque ( false );
        slideLayout = new SlideLayout ( searchPanel );
        searchPanel.setLayout ( slideLayout );
        searchPanel.add ( topPanel );


        searchPlate = new WebPanel ()
        {

            private BufferedImage bi = ImageUtils
                    .getBufferedImage ( SlidingSearch.class.getResource ( "icons/searchBg.png" ) );
            private Paint bg = new TexturePaint ( bi,
                    new Rectangle ( 0, 0, bi.getWidth (), bi.getHeight () ) );

            {
                setBorder ( BorderFactory.createEmptyBorder ( contentSpacing - 1,
                        ( connected ? 0 : shadeWidth ) + topRound + contentSpacing + 1,
                        shadeWidth + contentSpacing,
                        ( connected ? 0 : shadeWidth ) + topRound + contentSpacing ) );
            }

            protected void paintComponent ( Graphics g )
            {
                super.paintComponent ( g );

                Graphics2D g2d = ( Graphics2D ) g;
                LafUtils.setupAntialias ( g2d );

                Shape border = createBorderShape ();

                LafUtils.drawShade ( g2d, border, WebCustomTooltipStyle.shadeColor, shadeWidth );

                g2d.setPaint ( bg );
                g2d.fill ( border );
                g2d.setPaint ( Color.WHITE );
                g2d.draw ( border );
            }

            private Shape createBorderShape ()
            {
                int leftSpace = connected ? 0 : shadeWidth;
                int rightSpace = connected ? 0 : shadeWidth;
                int shear = connected ? 0 : -1;
                GeneralPath gp = new GeneralPath ();
                gp.moveTo ( leftSpace, shear );
                gp.quadTo ( leftSpace + topRound, shear, leftSpace + topRound, topRound );
                gp.lineTo ( leftSpace + topRound, getHeight () - shadeWidth - bottomRound );
                gp.quadTo ( leftSpace + topRound, getHeight () - shadeWidth,
                        leftSpace + topRound + bottomRound, getHeight () - shadeWidth );
                gp.lineTo ( getWidth () - rightSpace - topRound - bottomRound,
                        getHeight () - shadeWidth );
                gp.quadTo ( getWidth () - rightSpace - topRound, getHeight () - shadeWidth,
                        getWidth () - rightSpace - topRound,
                        getHeight () - shadeWidth - bottomRound );
                gp.lineTo ( getWidth () - rightSpace - topRound, topRound );
                gp.quadTo ( getWidth () - rightSpace - topRound, shear, getWidth () - rightSpace,
                        shear );
                return gp;
            }
        };
        searchPlate.setOpaque ( false );
        searchPlate.setLayout ( new BorderLayout () );
        searchPlate.setVisible ( false );

        leftLine = new UpperLine ();
        leftLine.setVisible ( false );

        rightLine = new UpperLine ();
        rightLine.setVisible ( false );

        topPanel.add ( leftLine, "0,0" );
        topPanel.add ( searchPlate, "1,0" );
        topPanel.add ( rightLine, "2,0" );


        searchField = new WebTextField ( 15 );
        TooltipManager
                .setTooltip ( searchField, searchIcon, "Quick components search", TooltipWay.down );
        searchField.setBackgroundPainter ( new NinePatchBackgroundPainter (
                SlidingSearch.class.getResource ( "icons/searchField.png" ) ) );
        searchField.setForeground ( Color.WHITE );
        searchField.setCaretColor ( Color.LIGHT_GRAY );
        searchField.setHorizontalAlignment ( WebTextField.CENTER );
        searchPlate.add ( searchField );


        HotkeyManager.registerHotkey ( layeredPane, Hotkey.CTRL_F, new HotkeyRunnable ()
        {
            public void run ( KeyEvent e )
            {
                toggleSearch ();
            }
        } );
        HotkeyManager.registerHotkey ( layeredPane, Hotkey.ESCAPE, new HotkeyRunnable ()
        {
            public void run ( KeyEvent e )
            {
                if ( searchField.getText ().length () > 0 )
                {
                    searchField.setText ( "" );
                }
                else if ( searchField.isFocusOwner () )
                {
                    searchField.transferFocus ();
                }
            }
        } );
        searchField.addFocusListener ( new FocusAdapter ()
        {
            public void focusLost ( FocusEvent e )
            {
                hideSearch ();
            }
        } );

        layeredPane.add ( searchPanel, JLayeredPane.DRAG_LAYER );
        layeredPane.addComponentListener ( new ComponentAdapter ()
        {
            public void componentResized ( ComponentEvent e )
            {
                searchPanel.setBounds ( 0, 0, layeredPane.getWidth (), layeredPane.getHeight () );
            }
        } );
    }

    public WebTextField getSearchField ()
    {
        return searchField;
    }

    public void toggleSearch ()
    {
        if ( searchShown )
        {
            hideSearch ();
        }
        else
        {
            showSearch ();
        }
    }

    public void showSearch ()
    {
        if ( !isSearchEnabled () )
        {
            return;
        }

        leftLine.setVisible ( true );
        searchPlate.setVisible ( true );
        rightLine.setVisible ( true );

        slideLayout.slideIn ();

        searchField.requestFocusInWindow ();

        searchShown = true;
    }

    protected boolean isSearchEnabled ()
    {
        return true;
    }

    public void hideSearch ()
    {
        searchField.setText ( "" );

        slideLayout.slideOut ();

        //        leftLine.setVisible ( false );
        //        searchPlate.setVisible ( false );
        //        rightLine.setVisible ( false );

        searchShown = false;
    }

    private class UpperLine extends JComponent
    {
        public UpperLine ()
        {
            super ();
        }

        protected void paintComponent ( Graphics g )
        {
            Graphics2D g2d = ( Graphics2D ) g;
            LafUtils.setupAntialias ( g2d );

            Shape border = new Line2D.Double ( 0, 0, getWidth (), 0 );

            LafUtils.drawShade ( g2d, border, WebCustomTooltipStyle.shadeColor, shadeWidth );

            g2d.setPaint ( Color.WHITE );
            g2d.draw ( border );
        }
    }

    private class SlideLayout implements LayoutManager
    {
        private int slideY = 0;
        private Timer animator = null;
        private int height = 0;

        private JComponent container;

        public SlideLayout ( JComponent container )
        {
            super ();
            this.container = container;
        }

        public void slideIn ()
        {
            if ( animator != null && animator.isRunning () )
            {
                animator.stop ();
            }

            animator = new Timer ( 1000 / StyleConstants.averageAnimationFps, new ActionListener ()
            {
                public void actionPerformed ( ActionEvent e )
                {
                    if ( slideY < height )
                    {
                        slideY += 3;
                        container.revalidate ();
                    }
                    else
                    {
                        animator.stop ();
                    }
                }
            } );
            animator.start ();
        }

        public void slideOut ()
        {
            if ( animator != null && animator.isRunning () )
            {
                animator.stop ();
            }

            animator = new Timer ( 1000 / StyleConstants.animationFps, new ActionListener ()
            {
                public void actionPerformed ( ActionEvent e )
                {
                    if ( slideY > 0 )
                    {
                        slideY -= 5;
                        container.revalidate ();
                    }
                    else
                    {
                        animator.stop ();
                    }
                }
            } );
            animator.start ();
        }

        public void addLayoutComponent ( String name, Component comp )
        {
            //
        }

        public void removeLayoutComponent ( Component comp )
        {
            //
        }

        public Dimension preferredLayoutSize ( Container parent )
        {
            Dimension ps = new Dimension ( 0, 0 );
            for ( Component c : parent.getComponents () )
            {
                ps = SwingUtils.max ( ps, c.getPreferredSize () );
            }
            ps.height = slideY < ps.height ? slideY : ps.height;
            return ps;
        }

        public Dimension minimumLayoutSize ( Container parent )
        {
            return preferredLayoutSize ( parent );
        }

        public void layoutContainer ( Container parent )
        {
            for ( Component c : parent.getComponents () )
            {
                Dimension ps = c.getPreferredSize ();
                c.setBounds ( 0, slideY < ps.height ? slideY - ps.height : 0, parent.getWidth (),
                        ps.height );
                height = Math.max ( height, ps.height );
            }
        }
    }
}