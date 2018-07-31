/*
 * ============================================================
 * [ SYSTEM ]		: Web::Java ?? ( HOBOKEN )
 * [ PROJECT ]		: HOBOKEN Project
 * 
 * $Id: ColorContents.java 1098 2009-07-29 10:15:28Z mezawa_takuji $
 * ============================================================
 */

package prj.hoboken.patrasche.service.poi.config;

import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.poi.hssf.util.HSSFColor;

/**
 * ?????????????????????????????????
 * 
 * 
 * NOTE:<br>
 * <blockquote>?????POI?????????</blockquote>
 * 
 * <p>$Revision: 1098 $<br>$Date: 2009-07-29 19:15:28 +0900 (?, 29 7 2009) $</p>
 *
 * @since  J2SDK 1.4 : Servlet2.3/JSP1.2 : Apache Struts 1.2 : SpringFramework 1.2
 * @since  POI 3.0.1 (final 20070705)
 * @since  Patrasche 3.0
 * 
 * @author
 *     Mezawa Takuji  ( HOBOKEN Project )<br>
 *     <!-- *???*  ( CompanyName )<br> -->
 */
public final class ColorContents {
    
    /* ????? */
    private static final SortedMap COLOR;
    
    /* ????????? */
    static {
        COLOR = new TreeMap();
        COLOR.put(new Short(HSSFColor.SKY_BLUE.index), new HSSFColor.SKY_BLUE());
        COLOR.put(new Short(HSSFColor.WHITE.index), new HSSFColor.WHITE());
        COLOR.put(new Short(HSSFColor.BLACK.index), new HSSFColor.BLACK());
        COLOR.put(new Short(HSSFColor.BLUE_GREY.index), new HSSFColor.BLUE_GREY());
        COLOR.put(new Short(HSSFColor.BRIGHT_GREEN.index), new HSSFColor.BRIGHT_GREEN());
        COLOR.put(new Short(HSSFColor.ROSE.index), new HSSFColor.ROSE());
        COLOR.put(new Short(HSSFColor.YELLOW.index), new HSSFColor.YELLOW());
        COLOR.put(new Short(HSSFColor.AQUA.index), new HSSFColor.AQUA());
        COLOR.put(new Short(HSSFColor.BLUE.index), new HSSFColor.BLUE());
        COLOR.put(new Short(HSSFColor.BROWN.index), new HSSFColor.BROWN());
        COLOR.put(new Short(HSSFColor.CORAL.index), new HSSFColor.CORAL());
        COLOR.put(new Short(HSSFColor.CORNFLOWER_BLUE.index), new HSSFColor.CORNFLOWER_BLUE());
        COLOR.put(new Short(HSSFColor.DARK_BLUE.index), new HSSFColor.DARK_BLUE());
        COLOR.put(new Short(HSSFColor.DARK_GREEN.index), new HSSFColor.DARK_GREEN());
        COLOR.put(new Short(HSSFColor.DARK_RED.index), new HSSFColor.DARK_RED());
        COLOR.put(new Short(HSSFColor.DARK_TEAL.index), new HSSFColor.DARK_TEAL());
        COLOR.put(new Short(HSSFColor.DARK_YELLOW.index), new HSSFColor.DARK_YELLOW());
        COLOR.put(new Short(HSSFColor.GOLD.index), new HSSFColor.GOLD());
        COLOR.put(new Short(HSSFColor.GREEN.index), new HSSFColor.GREEN());
        COLOR.put(new Short(HSSFColor.GREY_25_PERCENT.index), new HSSFColor.GREY_25_PERCENT());
        COLOR.put(new Short(HSSFColor.GREY_40_PERCENT.index), new HSSFColor.GREY_40_PERCENT());
        COLOR.put(new Short(HSSFColor.GREY_50_PERCENT.index), new HSSFColor.GREY_50_PERCENT());
        COLOR.put(new Short(HSSFColor.GREY_80_PERCENT.index), new HSSFColor.GREY_80_PERCENT());
        COLOR.put(new Short(HSSFColor.INDIGO.index), new HSSFColor.INDIGO());
        COLOR.put(new Short(HSSFColor.LAVENDER.index), new HSSFColor.LAVENDER());
        COLOR.put(new Short(HSSFColor.LEMON_CHIFFON.index), new HSSFColor.LEMON_CHIFFON());
        COLOR.put(new Short(HSSFColor.LIGHT_BLUE.index), new HSSFColor.LIGHT_BLUE());
        COLOR.put(new Short(HSSFColor.LIGHT_CORNFLOWER_BLUE.index), new HSSFColor.LIGHT_CORNFLOWER_BLUE());
        COLOR.put(new Short(HSSFColor.LIGHT_GREEN.index), new HSSFColor.LIGHT_GREEN());
        COLOR.put(new Short(HSSFColor.LIGHT_ORANGE.index), new HSSFColor.LIGHT_ORANGE());
        COLOR.put(new Short(HSSFColor.LIGHT_TURQUOISE.index), new HSSFColor.LIGHT_TURQUOISE());
        COLOR.put(new Short(HSSFColor.LIGHT_YELLOW.index), new HSSFColor.LIGHT_YELLOW());
        COLOR.put(new Short(HSSFColor.LIME.index), new HSSFColor.LIME());
        COLOR.put(new Short(HSSFColor.MAROON.index), new HSSFColor.MAROON());
        COLOR.put(new Short(HSSFColor.OLIVE_GREEN.index), new HSSFColor.OLIVE_GREEN());
        COLOR.put(new Short(HSSFColor.ORANGE.index), new HSSFColor.ORANGE());
        COLOR.put(new Short(HSSFColor.ORCHID.index), new HSSFColor.ORCHID());
        COLOR.put(new Short(HSSFColor.PALE_BLUE.index), new HSSFColor.PALE_BLUE());
        COLOR.put(new Short(HSSFColor.PINK.index), new HSSFColor.PINK());
        COLOR.put(new Short(HSSFColor.PLUM.index), new HSSFColor.PLUM());
        COLOR.put(new Short(HSSFColor.RED.index), new HSSFColor.RED());
        COLOR.put(new Short(HSSFColor.ROYAL_BLUE.index), new HSSFColor.ROYAL_BLUE());
        COLOR.put(new Short(HSSFColor.SEA_GREEN.index), new HSSFColor.SEA_GREEN());
        COLOR.put(new Short(HSSFColor.TAN.index), new HSSFColor.TAN());
        COLOR.put(new Short(HSSFColor.TEAL.index), new HSSFColor.TEAL());
        COLOR.put(new Short(HSSFColor.TURQUOISE.index), new HSSFColor.TURQUOISE());
        COLOR.put(new Short(HSSFColor.VIOLET.index), new HSSFColor.VIOLET());
    };
    
    /**
     * ??? <code>ColorContents</code> ??????????????
     */
    private ColorContents() {
        super();
    }
    
    /**
     * ??????????????????
     * 
     * @return ????????????
     */
    public static ColorContents getInstance() {
        return new ColorContents();
    }
    
    public SortedMap getColorContents() {
        return COLOR;
    }
    
    /**
     * ???????????????????????????
     * 
     * @param index ???????????????????
     * @return ???
     */
    public HSSFColor getColorFromIndex(short index) {
        HSSFColor color = null;
        
        Short colorIndex = new Short(index);
        if (COLOR.containsKey(colorIndex)) {
            color = (HSSFColor)COLOR.get(colorIndex);
        }
        
        return color;
    }
}


/* Copyright (C) 2005, HOBOKEN Project, All Rights Reserved. */