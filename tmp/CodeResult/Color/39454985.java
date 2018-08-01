/**
 * Copyright (C) 2012 J.W.Marsden <jmarsden@plural.cc>
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package cc.plural.utils;

public class Color {

    final int R;
    final int G;
    final int B;
    final int A;

    public Color(int r, int g, int b) {
        R = r;
        G = g;
        B = b;
        A = 1;
    }

    public Color(int r, int g, int b, int a) {
        R = r;
        G = g;
        B = b;
        A = a;
    }

    public Color(float r, float g, float b) {
        R = (int) (r * 255);
        G = (int) (g * 255);
        B = (int) (b * 255);
        A = 1;
    }

    public Color(float r, float g, float b, float a) {
        R = (int) (r * 255);
        G = (int) (g * 255);
        B = (int) (b * 255);
        A = (int) (a * 255);
    }

    /*
     public Color(int rgb) {
     super(rgb);
     }

     public Color(int rgba, boolean hasalpha) {
     super(rgba, hasalpha);
     }
     */
    public int getRed() {
        return R;
    }

    public int getGreen() {
        return G;
    }

    public int getBlue() {
        return B;
    }

    public int getAlpha() {
        return A;
    }
    public static final Color ALICE_BLUE = new Color(240, 248, 255);
    public static final Color ANTIQUE_WHITE = new Color(250, 235, 215);
    public static final Color ANTIQUE_WHITE_1 = new Color(255, 239, 219);
    public static final Color ANTIQUE_WHITE_2 = new Color(238, 223, 204);
    public static final Color ANTIQUE_WHITE_3 = new Color(205, 192, 176);
    public static final Color ANTIQUE_WHITE_4 = new Color(139, 131, 120);
    public static final Color AQUA_MARINE = new Color(127, 255, 212);
    public static final Color AQUAMARINE_1 = new Color(127, 255, 212);
    public static final Color AQUAMARINE_2 = new Color(118, 238, 198);
    public static final Color AQUAMARINE_3 = new Color(102, 205, 170);
    public static final Color AQUAMARINE_4 = new Color(69, 139, 116);
    public static final Color AZURE = new Color(240, 255, 255);
    public static final Color AZURE_1 = new Color(240, 255, 255);
    public static final Color AZURE_2 = new Color(224, 238, 238);
    public static final Color AZURE_3 = new Color(193, 205, 205);
    public static final Color AZURE_4 = new Color(131, 139, 139);
    public static final Color BANANA = new Color(227, 207, 87);
    public static final Color BEIGE = new Color(245, 245, 220);
    public static final Color BISQUE = new Color(255, 228, 196);
    public static final Color BISQUE_1 = new Color(255, 228, 196);
    public static final Color BISQUE_2 = new Color(238, 213, 183);
    public static final Color BISQUE_3 = new Color(205, 183, 158);
    public static final Color BISQUE_4 = new Color(139, 125, 107);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color BLANCHE_DALMOND = new Color(255, 235, 205);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color BLUE_1 = new Color(0, 0, 242);
    public static final Color BLUE_2 = new Color(0, 0, 238);
    public static final Color BLUE_VIOLET = new Color(138, 43, 226);
    public static final Color BRICK = new Color(156, 102, 31);
    public static final Color BROWN = new Color(165, 42, 42);
    public static final Color BROWN_1 = new Color(255, 64, 64);
    public static final Color BROWN_2 = new Color(238, 59, 59);
    public static final Color BROWN_3 = new Color(205, 51, 51);
    public static final Color BROWN_4 = new Color(139, 35, 35);
    public static final Color BURLYWOOD = new Color(222, 184, 135);
    public static final Color BURLYWOOD_1 = new Color(255, 211, 155);
    public static final Color BURLYWOOD_2 = new Color(238, 197, 145);
    public static final Color BURLYWOOD_3 = new Color(205, 170, 125);
    public static final Color BURLYWOOD_4 = new Color(139, 115, 85);
    public static final Color BURNT_SIENNA = new Color(138, 54, 15);
    public static final Color BURNT_UMBER = new Color(138, 51, 36);
    public static final Color CADET_BLUE = new Color(95, 158, 160);
    public static final Color CADET_BLUE_1 = new Color(152, 245, 255);
    public static final Color CADET_BLUE_2 = new Color(142, 229, 238);
    public static final Color CADET_BLUE_3 = new Color(122, 197, 205);
    public static final Color CADET_BLUE_4 = new Color(83, 134, 139);
    public static final Color CADMIUM_ORANGE = new Color(255, 97, 3);
    public static final Color CADMIUM_YELLOW = new Color(255, 153, 18);
    public static final Color CARROT = new Color(237, 145, 33);
    public static final Color CHOCOLATE = new Color(210, 105, 30);
    public static final Color CHOCOLATE_1 = new Color(255, 127, 36);
    public static final Color CHOCOLATE_2 = new Color(238, 118, 33);
    public static final Color CHOCOLATE_3 = new Color(205, 102, 29);
    public static final Color CHOCOLATE_4 = new Color(139, 69, 19);
    public static final Color COBALT = new Color(61, 89, 171);
    public static final Color COBALTGREEN = new Color(61, 145, 64);
    public static final Color COLDGREY = new Color(128, 138, 135);
    public static final Color CORAL = new Color(255, 127, 80);
    public static final Color CORAL_1 = new Color(255, 114, 86);
    public static final Color CORAL_2 = new Color(238, 106, 80);
    public static final Color CORAL_3 = new Color(205, 91, 69);
    public static final Color CORAL_4 = new Color(139, 62, 47);
    public static final Color CORNFLOWER_BLUE = new Color(100, 149, 237);
    public static final Color CORNSILK = new Color(255, 248, 220);
    public static final Color CORNSILK_1 = new Color(255, 248, 220);
    public static final Color CORNSILK_2 = new Color(238, 232, 205);
    public static final Color CORNSILK_3 = new Color(205, 200, 177);
    public static final Color CORNSILK_4 = new Color(139, 136, 120);
    public static final Color CRIMSON = new Color(220, 20, 60);
    public static final Color AQUA = new Color(0, 255, 255);
    public static final Color CYAN = new Color(0, 255, 255);
    public static final Color CYAN_2 = new Color(0, 238, 238);
    public static final Color CYAN_3 = new Color(0, 205, 205);
    public static final Color DARK_CYAN = new Color(0, 139, 139);
    public static final Color CYAN_4 = new Color(0, 139, 139);
    public static final Color DARK_BLUE = new Color(0, 0, 139);
    public static final Color DARK_GRAY = new Color(169, 169, 169);
    public static final Color DARK_MAGENTA = new Color(139, 0, 139);
    public static final Color DARK_ORCHID = new Color(153, 50, 204);
    public static final Color DARK_ORCHID_1 = new Color(191, 62, 255);
    public static final Color DARK_ORCHID_2 = new Color(178, 58, 238);
    public static final Color DARK_ORCHID_3 = new Color(154, 50, 205);
    public static final Color DARK_ORCHID_4 = new Color(104, 34, 139);
    public static final Color DARK_RED = new Color(139, 0, 0);
    public static final Color DARK_SALMON = new Color(233, 150, 122);
    public static final Color DARK_SLATE_BLUE = new Color(72, 61, 139);
    public static final Color DARK_TURQUOISE = new Color(0, 206, 209);
    public static final Color DARK_VIOLET = new Color(148, 0, 211);
    public static final Color DARK_GOLDEN_ROD = new Color(184, 134, 11);
    public static final Color DARK_GOLDEN_ROD_1 = new Color(255, 185, 15);
    public static final Color DARK_GOLDEN_ROD_2 = new Color(238, 173, 14);
    public static final Color DARK_GOLDEN_ROD_3 = new Color(205, 149, 12);
    public static final Color DARK_GOLDEN_ROD_4 = new Color(139, 101, 8);
    public static final Color DARK_GREEN = new Color(0, 100, 0);
    public static final Color DARK_KHAKI = new Color(189, 183, 107);
    public static final Color DARK_OLIVE_GREEN = new Color(85, 107, 47);
    public static final Color DARK_OLIVE_GREEN_1 = new Color(202, 255, 112);
    public static final Color DARK_OLIVE_GREEN_2 = new Color(188, 238, 104);
    public static final Color DARK_OLIVE_GREEN_3 = new Color(162, 205, 90);
    public static final Color DARK_OLIVE_GREEN_4 = new Color(110, 139, 61);
    public static final Color DARK_ORANGE = new Color(255, 140, 0);
    public static final Color DARK_ORANGE_1 = new Color(255, 127, 0);
    public static final Color DARK_ORANGE_2 = new Color(238, 118, 0);
    public static final Color DARK_ORANGE_3 = new Color(205, 102, 0);
    public static final Color DARK_ORANGE_4 = new Color(139, 69, 0);
    public static final Color DARK_SEAGREEN = new Color(143, 188, 143);
    public static final Color DARK_SEAGREEN_1 = new Color(193, 255, 193);
    public static final Color DARK_SEAGREEN_2 = new Color(180, 238, 180);
    public static final Color DARK_SEAGREEN_3 = new Color(155, 205, 155);
    public static final Color DARK_SEAGREEN_4 = new Color(105, 139, 105);
    public static final Color DARK_SLATE_GRAY = new Color(47, 79, 79);
    public static final Color DARK_SLATE_GRAY_1 = new Color(151, 255, 255);
    public static final Color DARK_SLATE_GRAY_2 = new Color(141, 238, 238);
    public static final Color DARK_SLATE_GRAY_3 = new Color(121, 205, 205);
    public static final Color DARK_SLATE_GRAY_4 = new Color(82, 139, 139);
    public static final Color DEEP_PINK = new Color(255, 20, 147);
    public static final Color DEEP_PINK_2 = new Color(238, 18, 137);
    public static final Color DEEP_PINK_3 = new Color(205, 16, 118);
    public static final Color DEEP_PINK_4 = new Color(139, 10, 80);
    public static final Color DEEP_SKYBLUE = new Color(0, 191, 255);
    public static final Color DEEP_SKYBLUE_1 = new Color(0, 191, 255);
    public static final Color DEEP_SKYBLUE_2 = new Color(0, 178, 238);
    public static final Color DEEP_SKYBLUE_3 = new Color(0, 154, 205);
    public static final Color DEEP_SKYBLUE_4 = new Color(0, 104, 139);
    public static final Color DIM_GRAY = new Color(105, 105, 105);
    public static final Color DODGER_BLUE = new Color(30, 144, 255);
    public static final Color DODGER_BLUE_1 = new Color(30, 144, 255);
    public static final Color DODGER_BLUE_2 = new Color(28, 134, 238);
    public static final Color DODGER_BLUE_3 = new Color(24, 116, 205);
    public static final Color DODGER_BLUE_4 = new Color(16, 78, 139);
    public static final Color EGG_SHELL = new Color(252, 230, 201);
    public static final Color EMERALD_GREEN = new Color(0, 201, 87);
    public static final Color FIRE_BRICK = new Color(178, 34, 34);
    public static final Color FIRE_BRICK_1 = new Color(255, 48, 48);
    public static final Color FIRE_BRICK_2 = new Color(238, 44, 44);
    public static final Color FIRE_BRICK_3 = new Color(205, 38, 38);
    public static final Color FIRE_BRICK_4 = new Color(139, 26, 26);
    public static final Color FLESH = new Color(255, 125, 64);
    public static final Color FLORAL_WHITE = new Color(255, 250, 240);
    public static final Color FOREST_GREEN = new Color(34, 139, 34);
    public static final Color FUCHSIA = new Color(0, 0, 0);
    public static final Color GAINSBORO = new Color(220, 220, 220);
    public static final Color GHOST_WHITE = new Color(248, 248, 255);
    public static final Color GOLD = new Color(255, 215, 0);
    public static final Color GOLD_1 = new Color(255, 215, 0);
    public static final Color GOLD_2 = new Color(238, 201, 0);
    public static final Color GOLD_3 = new Color(205, 173, 0);
    public static final Color GOLD_4 = new Color(139, 117, 0);
    public static final Color GOLDEN_ROD = new Color(218, 165, 32);
    public static final Color GOLDEN_ROD_1 = new Color(255, 193, 37);
    public static final Color GOLDEN_ROD_2 = new Color(238, 180, 34);
    public static final Color GOLDEN_ROD_3 = new Color(205, 155, 29);
    public static final Color GOLDEN_ROD_4 = new Color(139, 105, 20);
    public static final Color GRAY = new Color(128, 128, 128);
    public static final Color GRAY_1 = new Color(3, 3, 3);
    public static final Color GRAY_10 = new Color(26, 26, 26);
    public static final Color GRAY_11 = new Color(28, 28, 28);
    public static final Color GRAY_12 = new Color(31, 31, 31);
    public static final Color GRAY_13 = new Color(33, 33, 33);
    public static final Color GRAY_14 = new Color(36, 36, 36);
    public static final Color GRAY_15 = new Color(38, 38, 38);
    public static final Color GRAY_16 = new Color(41, 41, 41);
    public static final Color GRAY_17 = new Color(43, 43, 43);
    public static final Color GRAY_18 = new Color(46, 46, 46);
    public static final Color GRAY_19 = new Color(48, 48, 48);
    public static final Color GRAY_2 = new Color(5, 5, 5);
    public static final Color GRAY_20 = new Color(51, 51, 51);
    public static final Color GRAY_21 = new Color(54, 54, 54);
    public static final Color GRAY_22 = new Color(56, 56, 56);
    public static final Color GRAY_23 = new Color(59, 59, 59);
    public static final Color GRAY_24 = new Color(61, 61, 61);
    public static final Color GRAY_25 = new Color(64, 64, 64);
    public static final Color GRAY_26 = new Color(66, 66, 66);
    public static final Color GRAY_27 = new Color(69, 69, 69);
    public static final Color GRAY_28 = new Color(71, 71, 71);
    public static final Color GRAY_29 = new Color(74, 74, 74);
    public static final Color GRAY_3 = new Color(8, 8, 8);
    public static final Color GRAY_30 = new Color(77, 77, 77);
    public static final Color GRAY_31 = new Color(79, 79, 79);
    public static final Color GRAY_32 = new Color(82, 82, 82);
    public static final Color GRAY_33 = new Color(84, 84, 84);
    public static final Color GRAY_34 = new Color(87, 87, 87);
    public static final Color GRAY_35 = new Color(89, 89, 89);
    public static final Color GRAY_36 = new Color(92, 92, 92);
    public static final Color GRAY_37 = new Color(94, 94, 94);
    public static final Color GRAY_38 = new Color(97, 97, 97);
    public static final Color GRAY_39 = new Color(99, 99, 99);
    public static final Color LIGHT_CYAN = new Color(10, 10, 10);
    public static final Color GRAY_40 = new Color(102, 102, 102);
    public static final Color GRAY_41 = new Color(105, 105, 105);
    public static final Color GRAY_42 = new Color(107, 107, 107);
    public static final Color GRAY_43 = new Color(110, 110, 110);
    public static final Color GRAY_44 = new Color(112, 112, 112);
    public static final Color GRAY_45 = new Color(115, 115, 115);
    public static final Color GRAY_46 = new Color(117, 117, 117);
    public static final Color GRAY_47 = new Color(120, 120, 120);
    public static final Color GRAY_48 = new Color(122, 122, 122);
    public static final Color GRAY_49 = new Color(125, 125, 125);
    public static final Color GRAY_5 = new Color(13, 13, 13);
    public static final Color GRAY_50 = new Color(127, 127, 127);
    public static final Color GRAY_51 = new Color(130, 130, 130);
    public static final Color GRAY_52 = new Color(133, 133, 133);
    public static final Color GRAY_53 = new Color(135, 135, 135);
    public static final Color GRAY_54 = new Color(138, 138, 138);
    public static final Color GRAY_55 = new Color(140, 140, 140);
    public static final Color GRAY_56 = new Color(143, 143, 143);
    public static final Color GRAY_57 = new Color(145, 145, 145);
    public static final Color GRAY_58 = new Color(148, 148, 148);
    public static final Color GRAY_59 = new Color(150, 150, 150);
    public static final Color GRAY_6 = new Color(15, 15, 15);
    public static final Color GRAY_60 = new Color(153, 153, 153);
    public static final Color GRAY_61 = new Color(156, 156, 156);
    public static final Color GRAY_62 = new Color(158, 158, 158);
    public static final Color GRAY_63 = new Color(161, 161, 161);
    public static final Color GRAY_64 = new Color(163, 163, 163);
    public static final Color GRAY_65 = new Color(166, 166, 166);
    public static final Color GRAY_66 = new Color(168, 168, 168);
    public static final Color GRAY_67 = new Color(171, 171, 171);
    public static final Color GRAY_68 = new Color(173, 173, 173);
    public static final Color GRAY_69 = new Color(176, 176, 176);
    public static final Color GRAY_7 = new Color(18, 18, 18);
    public static final Color GRAY_70 = new Color(179, 179, 179);
    public static final Color GRAY_71 = new Color(181, 181, 181);
    public static final Color GRAY_72 = new Color(184, 184, 184);
    public static final Color GRAY_73 = new Color(186, 186, 186);
    public static final Color GRAY_74 = new Color(189, 189, 189);
    public static final Color GRAY_75 = new Color(191, 191, 191);
    public static final Color GRAY_76 = new Color(194, 194, 194);
    public static final Color GRAY_77 = new Color(196, 196, 196);
    public static final Color GRAY_78 = new Color(199, 199, 199);
    public static final Color GRAY_79 = new Color(201, 201, 201);
    public static final Color GRAY_8 = new Color(20, 20, 20);
    public static final Color GRAY_80 = new Color(204, 204, 204);
    public static final Color GRAY_81 = new Color(207, 207, 207);
    public static final Color GRAY_82 = new Color(209, 209, 209);
    public static final Color GRAY_83 = new Color(212, 212, 212);
    public static final Color GRAY_84 = new Color(214, 214, 214);
    public static final Color GRAY_85 = new Color(217, 217, 217);
    public static final Color GRAY_86 = new Color(219, 219, 219);
    public static final Color GRAY_87 = new Color(222, 222, 222);
    public static final Color GRAY_88 = new Color(224, 224, 224);
    public static final Color GRAY_89 = new Color(227, 227, 227);
    public static final Color GRAY_9 = new Color(23, 23, 23);
    public static final Color GRAY_90 = new Color(229, 229, 229);
    public static final Color GRAY_91 = new Color(232, 232, 232);
    public static final Color GRAY_92 = new Color(235, 235, 235);
    public static final Color GRAY_93 = new Color(237, 237, 237);
    public static final Color GRAY_94 = new Color(240, 240, 240);
    public static final Color GRAY_95 = new Color(242, 242, 242);
    public static final Color GRAY_96 = new Color(245, 245, 245);
    public static final Color GRAY_97 = new Color(247, 247, 247);
    public static final Color GRAY_98 = new Color(250, 250, 250);
    public static final Color GRAY_99 = new Color(252, 252, 252);
    public static final Color LIME = new Color(0, 255, 0);
    public static final Color GREEN_1 = new Color(0, 255, 0);
    public static final Color GREEN_2 = new Color(0, 238, 0);
    public static final Color GREEN_3 = new Color(0, 205, 0);
    public static final Color GREEN_4 = new Color(0, 139, 0);
    public static final Color GREEN = new Color(0, 128, 0);
    public static final Color GREEN_YELLOW = new Color(173, 255, 47);
    public static final Color HONEYDEW = new Color(240, 255, 240);
    public static final Color HONEYDEW_1 = new Color(240, 255, 240);
    public static final Color HONEYDEW_2 = new Color(224, 238, 224);
    public static final Color HONEYDEW_3 = new Color(193, 205, 193);
    public static final Color HONEYDEW_4 = new Color(131, 139, 131);
    public static final Color HOT_PINK = new Color(255, 105, 180);
    public static final Color HOT_PINK_1 = new Color(255, 110, 180);
    public static final Color HOT_PINK_2 = new Color(238, 106, 167);
    public static final Color HOT_PINK_3 = new Color(205, 96, 144);
    public static final Color HOT_PINK_4 = new Color(139, 58, 98);
    public static final Color INDIAN_RED = new Color(176, 23, 31);
    public static final Color INDIAN_RED_1 = new Color(255, 106, 106);
    public static final Color INDIAN_RED_2 = new Color(238, 99, 99);
    public static final Color INDIAN_RED_3 = new Color(205, 85, 85);
    public static final Color INDIAN_RED_4 = new Color(139, 58, 58);
    public static final Color INDIGO = new Color(75, 0, 130);
    public static final Color IVORY = new Color(255, 255, 240);
    public static final Color IVORY_1 = new Color(255, 255, 240);
    public static final Color IVORY_2 = new Color(238, 238, 224);
    public static final Color IVORY_3 = new Color(205, 205, 193);
    public static final Color IVORY_4 = new Color(139, 139, 131);
    public static final Color IVORY_BLACK = new Color(41, 36, 33);
    public static final Color KHAKI = new Color(240, 230, 140);
    public static final Color KHAKI_1 = new Color(255, 246, 143);
    public static final Color KHAKI_2 = new Color(238, 230, 133);
    public static final Color KHAKI_3 = new Color(205, 198, 115);
    public static final Color KHAKI_4 = new Color(139, 134, 78);
    public static final Color LAVENDER = new Color(230, 230, 250);
    public static final Color LAVENDER_BLUSH_1 = new Color(255, 240, 245);
    public static final Color LAVENDER_BLUSH_2 = new Color(238, 224, 229);
    public static final Color LAVENDER_BLUSH_3 = new Color(205, 193, 197);
    public static final Color LAVENDER_BLUSH_4 = new Color(139, 131, 134);
    public static final Color LAWNGREEN = new Color(124, 252, 0);
    public static final Color LEMON_CHIFFON = new Color(255, 250, 205);
    public static final Color LEMON_CHIFFON_1 = new Color(255, 250, 205);
    public static final Color LEMON_CHIFFON_2 = new Color(238, 233, 191);
    public static final Color LEMON_CHIFFON_3 = new Color(205, 201, 165);
    public static final Color LEMON_CHIFFON_4 = new Color(139, 137, 112);
    public static final Color LIGHT_BLUE = new Color(173, 216, 230);
    public static final Color LIGHT_BLUE_1 = new Color(191, 239, 255);
    public static final Color LIGHT_BLUE_2 = new Color(178, 223, 238);
    public static final Color LIGHT_BLUE_3 = new Color(154, 192, 205);
    public static final Color LIGHT_BLUEE_4 = new Color(104, 131, 139);
    public static final Color LIGHT_CORAL = new Color(240, 128, 128);
    public static final Color LIGHT_PINK = new Color(255, 182, 193);
    public static final Color LIGHT_PINK_1 = new Color(255, 174, 185);
    public static final Color LIGHT_PINK_2 = new Color(238, 162, 173);
    public static final Color LIGHT_PINK_3 = new Color(205, 140, 149);
    public static final Color LIGHT_PINK_4 = new Color(139, 95, 101);
    public static final Color LIGHT_SKY_BLUE = new Color(135, 206, 250);
    public static final Color LIGHT_SKY_BLUE_1 = new Color(176, 226, 255);
    public static final Color LIGHT_SKY_BLUE_2 = new Color(164, 211, 238);
    public static final Color LIGHT_SKY_BLUE_3 = new Color(141, 182, 205);
    public static final Color LIGHT_SKY_BLUE_4 = new Color(96, 123, 139);
    public static final Color LIGHT_SLATE_BLUE = new Color(132, 112, 255);
    public static final Color LIGHT_SLATE_GRAY = new Color(119, 136, 153);
    public static final Color LIGHT_STEEL_BLUE = new Color(176, 196, 222);
    public static final Color LIGHT_STEEL_BLUE_1 = new Color(202, 225, 255);
    public static final Color LIGHT_STEEL_BLUE_2 = new Color(188, 210, 238);
    public static final Color LIGHT_STEEL_BLUE_3 = new Color(162, 181, 205);
    public static final Color LIGHT_STEEL_BLUE_4 = new Color(110, 123, 139);
    public static final Color LIGHT_CYAN_1 = new Color(224, 255, 255);
    public static final Color LIGHT_CYAN_2 = new Color(209, 238, 238);
    public static final Color LIGHT_CYAN_3 = new Color(180, 205, 205);
    public static final Color LIGHT_CYAN_4 = new Color(122, 139, 139);
    public static final Color LIGHT_GOLDEN_ROD = new Color(255, 236, 139);
    public static final Color LIGHT_GOLDEN_ROD_1 = new Color(255, 236, 139);
    public static final Color LIGHT_GOLDEN_ROD_2 = new Color(238, 220, 130);
    public static final Color LIGHT_GOLDEN_ROD_3 = new Color(205, 190, 112);
    public static final Color LIGHT_GOLDEN_ROD_4 = new Color(139, 129, 76);
    public static final Color LIGHT_GOLDEN_ROD_YELLOW = new Color(250, 250, 210);
    public static final Color LIGHT_GREY = new Color(211, 211, 211);
    public static final Color LIGHT_SALMON = new Color(255, 160, 122);
    public static final Color LIGHT_SALMON_1 = new Color(255, 160, 122);
    public static final Color LIGHT_SALMON_2 = new Color(238, 149, 114);
    public static final Color LIGHT_SALMON_3 = new Color(205, 129, 98);
    public static final Color LIGHT_SALMON_4 = new Color(139, 87, 66);
    public static final Color LIGHT_SEAGREEN = new Color(32, 178, 170);
    public static final Color LIGHT_YELLOW = new Color(255, 255, 224);
    public static final Color LIGHT_YELLOW_1 = new Color(255, 255, 224);
    public static final Color LIGHT_YELLOW_2 = new Color(238, 238, 209);
    public static final Color LIGHT_YELLOW_3 = new Color(205, 205, 180);
    public static final Color LIGHT_YELLOW_4 = new Color(139, 139, 122);
    public static final Color LIME_GREEN = new Color(50, 205, 50);
    public static final Color LINEN = new Color(250, 240, 230);
    public static final Color MAGENTA = new Color(255, 0, 255);
    public static final Color MAGENTA_2 = new Color(238, 0, 238);
    public static final Color MAGENTA_3 = new Color(205, 0, 205);
    public static final Color MANGANESE_BLUE = new Color(3, 168, 158);
    public static final Color MAROON = new Color(128, 0, 0);
    public static final Color MAROON_1 = new Color(255, 52, 179);
    public static final Color MAROON_2 = new Color(238, 48, 167);
    public static final Color MAROON_3 = new Color(205, 41, 144);
    public static final Color MAROON_4 = new Color(139, 28, 98);
    public static final Color MEDIUM_BLUE = new Color(0, 0, 205);
    public static final Color MEDIUM_ORCHID = new Color(186, 85, 211);
    public static final Color MEDIUM_ORCHID_1 = new Color(224, 102, 255);
    public static final Color MEDIUM_ORCHID_2 = new Color(209, 95, 238);
    public static final Color MEDIUM_ORCHID_3 = new Color(180, 82, 205);
    public static final Color MEDIUM_ORCHID_4 = new Color(122, 55, 139);
    public static final Color MEDIUM_PURPLE = new Color(147, 112, 219);
    public static final Color MEDIUM_PURPLE_1 = new Color(171, 130, 255);
    public static final Color MEDIUM_PURPLE_2 = new Color(159, 121, 238);
    public static final Color MEDIUM_PURPLE_3 = new Color(137, 104, 205);
    public static final Color MEDIUM_PURPLE_4 = new Color(93, 71, 139);
    public static final Color MEDIUM_SLATE_BLUE = new Color(123, 104, 238);
    public static final Color MEDIUM_SEAGREEN = new Color(60, 179, 113);
    public static final Color MEDIUM_SPRING_GREEN = new Color(0, 250, 154);
    public static final Color MEDIUM_TURQUOISE = new Color(72, 209, 204);
    public static final Color MELON = new Color(227, 168, 105);
    public static final Color MIDNIGHT_BLUE = new Color(25, 25, 112);
    public static final Color MINT = new Color(189, 252, 201);
    public static final Color MINTCREAM = new Color(245, 255, 250);
    public static final Color MISTY_ROSE = new Color(255, 228, 225);
    public static final Color MISTY_ROSE_1 = new Color(255, 228, 225);
    public static final Color MISTY_ROSE_2 = new Color(238, 213, 210);
    public static final Color MISTY_ROSE_3 = new Color(205, 183, 181);
    public static final Color MISTY_ROSE_4 = new Color(139, 125, 123);
    public static final Color MOCCASIN = new Color(255, 228, 181);
    public static final Color NAVAJO_WHITE = new Color(255, 222, 173);
    public static final Color NAVAJO_WHITE_1 = new Color(255, 222, 173);
    public static final Color NAVAJO_WHITE_2 = new Color(238, 207, 161);
    public static final Color NAVAJO_WHITE_3 = new Color(205, 179, 139);
    public static final Color NAVAJO_WHITE_4 = new Color(139, 121, 94);
    public static final Color NAVY = new Color(0, 0, 128);
    public static final Color OLD_LACE = new Color(253, 245, 230);
    public static final Color OLIVE = new Color(128, 128, 0);
    public static final Color OLIVE_DRAB = new Color(107, 142, 35);
    public static final Color OLIVE_DRAB_1 = new Color(192, 255, 62);
    public static final Color OLIVE_DRAB_2 = new Color(179, 238, 58);
    public static final Color YELLOWGREEN = new Color(154, 205, 50);
    public static final Color OLIVE_DRAB_3 = new Color(154, 205, 50);
    public static final Color OLIVE_DRAB_4 = new Color(105, 139, 34);
    public static final Color ORANGE = new Color(255, 128, 0);
    public static final Color ORANGE_1 = new Color(255, 165, 0);
    public static final Color ORANGE_2 = new Color(238, 154, 0);
    public static final Color ORANGE_3 = new Color(205, 133, 0);
    public static final Color ORANGE_4 = new Color(139, 90, 0);
    public static final Color ORANGE_RED = new Color(255, 69, 0);
    public static final Color ORANGE_RED_1 = new Color(255, 69, 0);
    public static final Color ORANGE_RED_2 = new Color(238, 64, 0);
    public static final Color ORANGE_RED_3 = new Color(205, 55, 0);
    public static final Color ORANGE_RED_4 = new Color(139, 37, 0);
    public static final Color ORCHID = new Color(218, 112, 214);
    public static final Color ORCHID_1 = new Color(255, 131, 250);
    public static final Color ORCHID_2 = new Color(238, 122, 233);
    public static final Color ORCHID_3 = new Color(205, 105, 201);
    public static final Color ORCHID_4 = new Color(139, 71, 137);
    public static final Color PALE_VIOLET_RED = new Color(219, 112, 147);
    public static final Color PALE_VIOLET_RED_1 = new Color(255, 130, 171);
    public static final Color PALE_VIOLET_RED_2 = new Color(238, 121, 159);
    public static final Color PALE_VIOLET_RED_3 = new Color(205, 104, 137);
    public static final Color PALE_VIOLET_RED_4 = new Color(139, 71, 93);
    public static final Color PALE_GOLDEN_ROD = new Color(238, 232, 170);
    public static final Color PALE_GREEN = new Color(152, 251, 152);
    public static final Color PALE_GREEN_1 = new Color(154, 255, 154);
    public static final Color PALE_GREEN_2 = new Color(144, 238, 144);
    public static final Color LIGHT_GREEN = new Color(144, 238, 144);
    public static final Color PALE_GREEN_3 = new Color(124, 205, 124);
    public static final Color PALE_GREEN_4 = new Color(84, 139, 84);
    public static final Color PALE_TURQUOISE_1 = new Color(187, 255, 255);
    public static final Color PALE_TURQUOISE = new Color(187, 255, 255);
    public static final Color PALE_TURQUOISE_2 = new Color(174, 238, 238);
    public static final Color PALE_TURQUOISE_3 = new Color(150, 205, 205);
    public static final Color PALE_TURQUOISE_4 = new Color(102, 139, 139);
    public static final Color PAPAYA_WHIP = new Color(255, 239, 213);
    public static final Color PEACH_PUFF = new Color(255, 218, 185);
    public static final Color PEACH_PUFF_1 = new Color(255, 218, 185);
    public static final Color PEACH_PUFF_2 = new Color(238, 203, 173);
    public static final Color PEACH_PUFF_3 = new Color(205, 175, 149);
    public static final Color PEACH_PUFF_4 = new Color(139, 119, 101);
    public static final Color PEA_COCK = new Color(51, 161, 201);
    public static final Color PINK = new Color(255, 192, 203);
    public static final Color PINK_1 = new Color(255, 181, 197);
    public static final Color PINK_2 = new Color(238, 169, 184);
    public static final Color PINK_3 = new Color(205, 145, 158);
    public static final Color PINK_4 = new Color(139, 99, 108);
    public static final Color PLUM = new Color(221, 160, 221);
    public static final Color PLUM_1 = new Color(255, 187, 255);
    public static final Color PLUM_2 = new Color(238, 174, 238);
    public static final Color PLUM_3 = new Color(205, 150, 205);
    public static final Color PLUM_4 = new Color(139, 102, 139);
    public static final Color POWDER_BLUE = new Color(176, 224, 230);
    public static final Color PURPLE = new Color(128, 0, 128);
    public static final Color PURPLE_1 = new Color(155, 48, 255);
    public static final Color PURPLE_2 = new Color(145, 44, 238);
    public static final Color PURPLE_3 = new Color(125, 38, 205);
    public static final Color PURPLE_4 = new Color(85, 26, 139);
    public static final Color RASPBERRY = new Color(135, 38, 87);
    public static final Color RAW_SIENNA = new Color(199, 97, 20);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color RED_1 = new Color(255, 0, 0);
    public static final Color RED_2 = new Color(238, 0, 0);
    public static final Color RED_3 = new Color(205, 0, 0);
    public static final Color ROSY_BROWN = new Color(188, 143, 143);
    public static final Color ROSY_BROWN_1 = new Color(255, 193, 193);
    public static final Color ROSY_BROWN_2 = new Color(238, 180, 180);
    public static final Color ROSY_BROWN_3 = new Color(205, 155, 155);
    public static final Color ROSY_BROWN_4 = new Color(139, 105, 105);
    public static final Color ROYAL_BLUE = new Color(65, 105, 225);
    public static final Color ROYAL_BLUE_1 = new Color(72, 118, 255);
    public static final Color ROYAL_BLUE_2 = new Color(67, 110, 238);
    public static final Color ROYAL_BLUE_3 = new Color(58, 95, 205);
    public static final Color ROYAL_BLUE_4 = new Color(39, 64, 139);
    public static final Color SADDLE_BROWN = new Color(139, 69, 19);
    public static final Color SALMON = new Color(250, 128, 114);
    public static final Color SALMON_1 = new Color(255, 140, 105);
    public static final Color SALMON_2 = new Color(238, 130, 98);
    public static final Color SALMON_3 = new Color(205, 112, 84);
    public static final Color SALMON_4 = new Color(139, 76, 57);
    public static final Color SANDY_BROWN = new Color(244, 164, 96);
    public static final Color SAP_GREEN = new Color(48, 128, 20);
    public static final Color SEAGREEN = new Color(46, 139, 87);
    public static final Color SEAGREEN_1 = new Color(84, 255, 159);
    public static final Color SEAGREEN_2 = new Color(78, 238, 148);
    public static final Color SEAGREEN_3 = new Color(67, 205, 128);
    public static final Color SEAGREEN_4 = new Color(46, 139, 87);
    public static final Color SEASHELL = new Color(255, 245, 238);
    public static final Color SEASHELL_1 = new Color(255, 245, 238);
    public static final Color SEASHELL_2 = new Color(238, 229, 222);
    public static final Color SEASHELL_3 = new Color(205, 197, 191);
    public static final Color SEASHELL_4 = new Color(139, 134, 130);
    public static final Color SEPIA = new Color(94, 38, 18);
    public static final Color SIENNA = new Color(160, 82, 45);
    public static final Color SIENNA_1 = new Color(255, 130, 71);
    public static final Color SIENNA_2 = new Color(238, 121, 66);
    public static final Color SIENNA_3 = new Color(205, 104, 57);
    public static final Color SIENNA_4 = new Color(139, 71, 38);
    public static final Color SILVER = new Color(192, 192, 192);
    public static final Color SKY_BLUE = new Color(135, 206, 235);
    public static final Color SKY_BLUE_1 = new Color(135, 206, 255);
    public static final Color SKY_BLUE_2 = new Color(126, 192, 238);
    public static final Color SKY_BLUE_3 = new Color(108, 166, 205);
    public static final Color SKY_BLUE_4 = new Color(74, 112, 139);
    public static final Color SLATE_BLUE = new Color(106, 90, 205);
    public static final Color SLATE_BLUE_1 = new Color(131, 111, 255);
    public static final Color SLATE_BLUE_2 = new Color(122, 103, 238);
    public static final Color SLATE_BLUE_3 = new Color(105, 89, 205);
    public static final Color SLATE_BLUE_4 = new Color(71, 60, 139);
    public static final Color SLATE_GRAY = new Color(112, 128, 144);
    public static final Color SLATE_GRAY_1 = new Color(198, 226, 255);
    public static final Color SLATE_GRAY_2 = new Color(185, 211, 238);
    public static final Color SLATE_GRAY_3 = new Color(159, 182, 205);
    public static final Color SLATE_GRAY_4 = new Color(108, 123, 139);
    public static final Color SNOW = new Color(255, 250, 250);
    public static final Color SNOW_1 = new Color(255, 250, 250);
    public static final Color SNOW_2 = new Color(238, 233, 233);
    public static final Color SNOW_3 = new Color(205, 201, 201);
    public static final Color SNOW_4 = new Color(139, 137, 137);
    public static final Color SPRING_GREEN = new Color(0, 255, 127);
    public static final Color SPRING_GREEN_1 = new Color(0, 238, 118);
    public static final Color SPRING_GREEN_2 = new Color(0, 205, 102);
    public static final Color SPRING_GREEN_3 = new Color(0, 139, 69);
    public static final Color STEEL_BLUE = new Color(70, 130, 180);
    public static final Color STEEL_BLUE_1 = new Color(99, 184, 255);
    public static final Color STEEL_BLUE_2 = new Color(92, 172, 238);
    public static final Color STEEL_BLUE_3 = new Color(79, 148, 205);
    public static final Color STEEL_BLUE_4 = new Color(54, 100, 139);
    public static final Color TAN = new Color(210, 180, 140);
    public static final Color TAN_1 = new Color(255, 165, 79);
    public static final Color TAN_2 = new Color(238, 154, 73);
    public static final Color TAN_3 = new Color(205, 133, 63);
    public static final Color TAN_4 = new Color(139, 90, 43);
    public static final Color TEAL = new Color(0, 128, 128);
    public static final Color THISTLE = new Color(216, 191, 216);
    public static final Color THISTLE_1 = new Color(255, 225, 255);
    public static final Color THISTLE_2 = new Color(238, 210, 238);
    public static final Color THISTLE_3 = new Color(205, 181, 205);
    public static final Color THISTLE_4 = new Color(139, 123, 139);
    public static final Color TOMATO = new Color(255, 99, 71);
    public static final Color TOMATO_1 = new Color(255, 99, 71);
    public static final Color TOMATO_2 = new Color(238, 92, 66);
    public static final Color TOMATO_3 = new Color(205, 79, 57);
    public static final Color TOMATO_4 = new Color(139, 54, 38);
    public static final Color TURQUOISE = new Color(64, 224, 208);
    public static final Color TURQUOISE_1 = new Color(0, 245, 255);
    public static final Color TURQUOISE_2 = new Color(0, 229, 238);
    public static final Color TURQUOISE_3 = new Color(0, 197, 205);
    public static final Color TURQUOISE_4 = new Color(0, 134, 139);
    public static final Color TURQUOISE_BLUE = new Color(0, 199, 140);
    public static final Color VIOLET = new Color(238, 130, 238);
    public static final Color VIOLET_RED = new Color(208, 32, 144);
    public static final Color VIOLET_RED_MEDIUM = new Color(199, 21, 133);
    public static final Color VIOLETRED_1 = new Color(255, 62, 150);
    public static final Color VIOLETRED_2 = new Color(238, 58, 140);
    public static final Color VIOLETRED_3 = new Color(205, 50, 120);
    public static final Color VIOLETRED_4 = new Color(139, 34, 82);
    public static final Color WARM_GREY = new Color(128, 128, 105);
    public static final Color WHEAT = new Color(245, 222, 179);
    public static final Color WHEAT_1 = new Color(255, 231, 186);
    public static final Color WHEAT_2 = new Color(238, 216, 174);
    public static final Color WHEAT_3 = new Color(205, 186, 150);
    public static final Color WHEAT_4 = new Color(139, 126, 102);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color WHITE_SMOKE = new Color(245, 245, 245);
    public static final Color YELLOW = new Color(255, 255, 0);
    public static final Color YELLOW_1 = new Color(255, 255, 0);
    public static final Color YELLOW_2 = new Color(238, 238, 0);
    public static final Color YELLOW_3 = new Color(205, 205, 0);
    public static final Color YELLOW_4 = new Color(139, 139, 0);
}