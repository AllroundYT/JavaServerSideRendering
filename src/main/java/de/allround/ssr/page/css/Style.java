package de.allround.ssr.page.css;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.awt.*;
import java.net.URL;

@Getter
@Setter
@Accessors(fluent = true)
public class Style {
    private final StringBuilder styleAttributes = new StringBuilder();
    private final StringBuilder selectors = new StringBuilder();

    private Color color;
    private Color backgroundColor;
    private CSSSize borderTop;
    private CSSSize borderBottom;
    private CSSSize borderLeft;
    private CSSSize borderRight;
    private CSSSize borderRadius;
    private CSSSize borderWidth;
    private CSSSize padding;
    private Font font;
    private CSSSize textAlign;
    private TextDecoration textDecoration;
    private Display display;
    private CSSSize margin;
    private CSSSize width;
    private CSSSize height;
    private URL backgroundImage;
    private BackgroundRepeat backgroundRepeat;
    private BackgroundPosition backgroundPosition;
    private BackgroundSize backgroundSize;
    private BoxShadow boxShadow;
    private BoxShadow textShadow;
    private double opacity = 1.0;
    private String transition;
    private String transform;
    private String cursor;
    private int zIndex;
    private Overflow overflow;
    private Visibility visibility;
    private CSSSize lineHeight;
    private FontStyle fontStyle;
    private FontWeight fontWeight;
    private TextTransform textTransform;
    private CSSSize textIndent;
    private WhiteSpace whiteSpace;
    private CSSSize wordSpacing;
    private CSSSize letterSpacing;
    private ListStyleType listStyleType;
    private ListStylePosition listStylePosition;
    private VerticalAlign verticalAlign;
    private TextOverflow textOverflow;
    private PageBreak pageBreakBefore;
    private PageBreak pageBreakAfter;
    private PageBreak pageBreakInside;
    private TableLayout tableLayout;
    private BorderCollapse borderCollapse;
    private CSSSize borderSpacing;
    private CaptionSide captionSide;

    // Weitere Attribute hinzufügen

    public String compile() {
        StringBuilder compiledStyle = new StringBuilder();

        if (selectors.length() == 0) {
            compiledStyle.append("* {");
        } else {
            compiledStyle.append(selectors).append(" {");
        }

        if (color != null) compiledStyle.append("\ncolor: ").append(toHexString(color)).append(";");
        if (backgroundColor != null)
            compiledStyle.append("\nbackground-color: ").append(toHexString(backgroundColor)).append(";");
        if (borderTop != null && borderTop.value() != 0)
            compiledStyle.append("\nborder-top: ").append(borderTop).append(";");
        if (borderBottom != null && borderBottom.value() != 0)
            compiledStyle.append("\nborder-bottom: ").append(borderBottom).append(";");
        if (borderLeft != null && borderLeft.value() != 0)
            compiledStyle.append("\nborder-left: ").append(borderLeft).append(";");
        if (borderRight != null && borderRight.value() != 0)
            compiledStyle.append("\nborder-right: ").append(borderRight).append(";");
        if (borderRadius != null && borderRadius.value() != 0)
            compiledStyle.append("\nborder-radius: ").append(borderRadius).append(";");
        if (borderWidth != null && borderWidth.value() != 0)
            compiledStyle.append("\nborder-width: ").append(borderWidth).append(";");
        if (padding != null && padding.value() != 0) compiledStyle.append("\npadding: ").append(padding).append(";");
        if (font != null)
            compiledStyle.append("\nfont: ").append(font.getFontName()).append(", ").append(font.getStyle()).append(", ").append(font.getSize()).append(";");
        if (textAlign != null) compiledStyle.append("\ntext-align: ").append(textAlign).append(";");
        if (textDecoration != null) compiledStyle.append("\ntext-decoration: ").append(textDecoration).append(";");
        if (display != null) compiledStyle.append("\ndisplay: ").append(display).append(";");
        if (margin != null && margin.value() != 0) compiledStyle.append("\nmargin: ").append(margin).append(";");
        if (width != null && width.value() != 0) compiledStyle.append("\nwidth: ").append(width).append(";");
        if (height != null && height.value() != 0) compiledStyle.append("\nheight: ").append(height).append(";");
        if (backgroundImage != null)
            compiledStyle.append("\nbackground-image: url(").append(backgroundImage).append(");");
        if (backgroundRepeat != null)
            compiledStyle.append("\nbackground-repeat: ").append(backgroundRepeat).append(";");
        if (backgroundPosition != null)
            compiledStyle.append("\nbackground-position: ").append(backgroundPosition).append(";");
        if (backgroundSize != null) compiledStyle.append("\nbackground-size: ").append(backgroundSize).append(";");
        if (boxShadow != null) compiledStyle.append("\nbox-shadow: ").append(boxShadow).append(";");
        if (textShadow != null) compiledStyle.append("\ntext-shadow: ").append(textShadow).append(";");
        if (opacity >= 0 && opacity <= 1) compiledStyle.append("\nopacity: ").append(opacity).append(";");
        if (transition != null) compiledStyle.append("\ntransition: ").append(transition).append(";");
        if (transform != null) compiledStyle.append("\ntransform: ").append(transform).append(";");
        if (cursor != null) compiledStyle.append("\ncursor: ").append(cursor).append(";");
        if (zIndex >= 0) compiledStyle.append("\nz-index: ").append(zIndex).append(";");
        if (overflow != null) compiledStyle.append("\noverflow: ").append(overflow).append(";");
        if (visibility != null) compiledStyle.append("\nvisibility: ").append(visibility).append(";");
        if (lineHeight != null) compiledStyle.append("\nline-height: ").append(lineHeight).append(";");
        if (fontStyle != null) compiledStyle.append("\nfont-style: ").append(fontStyle).append(";");
        if (fontWeight != null) compiledStyle.append("\nfont-weight: ").append(fontWeight).append(";");
        if (textTransform != null) compiledStyle.append("\ntext-transform: ").append(textTransform).append(";");
        if (textIndent != null && textIndent.value() != 0)
            compiledStyle.append("\ntext-indent: ").append(textIndent).append(";");
        if (whiteSpace != null) compiledStyle.append("\nwhite-space: ").append(whiteSpace).append(";");
        if (wordSpacing != null && wordSpacing.value() != 0)
            compiledStyle.append("\nword-spacing: ").append(wordSpacing).append(";");
        if (letterSpacing != null && letterSpacing.value() != 0)
            compiledStyle.append("\nletter-spacing: ").append(letterSpacing).append(";");
        if (listStyleType != null) compiledStyle.append("\nlist-style-type: ").append(listStyleType).append(";");
        if (listStylePosition != null)
            compiledStyle.append("\nlist-style-position: ").append(listStylePosition).append(";");
        if (verticalAlign != null) compiledStyle.append("\nvertical-align: ").append(verticalAlign).append(";");
        if (textOverflow != null) compiledStyle.append("\ntext-overflow: ").append(textOverflow).append(";");
        if (pageBreakBefore != null) compiledStyle.append("\npage-break-before: ").append(pageBreakBefore).append(";");
        if (pageBreakAfter != null) compiledStyle.append("\npage-break-after: ").append(pageBreakAfter).append(";");
        if (pageBreakInside != null) compiledStyle.append("\npage-break-inside: ").append(pageBreakInside).append(";");
        if (tableLayout != null) compiledStyle.append("\ntable-layout: ").append(tableLayout).append(";");
        if (borderCollapse != null) compiledStyle.append("\nborder-collapse: ").append(borderCollapse).append(";");
        if (borderSpacing != null && borderSpacing.value() != 0)
            compiledStyle.append("\nborder-spacing: ").append(borderSpacing).append(";");
        if (captionSide != null) compiledStyle.append("\ncaption-side: ").append(captionSide).append(";");

        compiledStyle.append("\n}");

        return compiledStyle.toString();
    }


    // Hilfsmethode, um die Farbwerte als hexadezimalen String zu formatieren
    private String toHexString(Color color) {
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }

    public enum SizeType {
        PIXEL("px"),
        PERCENT("%"),
        EM("em"),
        REM("rem"),
        POINT("pt"),
        VIEWPORT_WIDTH("vw"),
        VIEWPORT_HEIGHT("vh");

        private final String symbol;

        SizeType(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    public enum TextDecoration {
        NONE,
        UNDERLINE,
        OVERLINE,
        LINE_THROUGH,
        DOTTED,
        DASHED,
        DOUBLE,
        WAVE,
        INHERIT,
        INITIAL
    }

    public enum Display {
        NONE,
        BLOCK,
        INLINE,
        INLINE_BLOCK,
        FLEX,
        GRID,
        TABLE,
        TABLE_ROW,
        TABLE_CELL,
        LIST_ITEM
    }

    public enum BackgroundRepeat {
        REPEAT,
        REPEAT_X,
        REPEAT_Y,
        NO_REPEAT,
        SPACE,
        ROUND,
        INHERIT,
        INITIAL,
        ROUND_SPACE,
        REPEAT_SPACE
    }

    public enum BackgroundPosition {
        LEFT_TOP,
        LEFT_CENTER,
        LEFT_BOTTOM,
        CENTER_TOP,
        CENTER_CENTER,
        CENTER_BOTTOM,
        RIGHT_TOP,
        RIGHT_CENTER,
        RIGHT_BOTTOM,
        TOP,
        BOTTOM
    }

    public enum BackgroundSize {
        AUTO,
        COVER,
        CONTAIN,
        LENGTH,
        PERCENTAGE,
        INHERIT,
        INITIAL,
        UNSET,
        LENGTH_PERCENTAGE,
        AUTO_COVER
    }

    public enum BoxShadow {
        NONE("none"),
        INSET("inset"),
        OUTSET("outset"),
        INITIAL("initial"),
        INHERIT("inherit"),
        SHADOW1("1px 1px 1px #000"),
        SHADOW2("2px 2px 2px #000"),
        SHADOW3("3px 3px 3px #000"),
        SHADOW4("4px 4px 4px #000"),
        SHADOW5("5px 5px 5px #000"),
        SHADOW6("6px 6px 6px #000"),
        SHADOW7("7px 7px 7px #000"),
        SHADOW8("8px 8px 8px #000"),
        SHADOW9("9px 9px 9px #000"),
        SHADOW10("10px 10px 10px #000");

        private final String value;

        BoxShadow(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    public enum Overflow {
        VISIBLE,
        HIDDEN,
        SCROLL,
        AUTO,
        CLIP,
        ELLIPSIS,
        PAGED,
        MIX,
        PAN_X,
        PAN_Y
    }

    public enum Visibility {
        VISIBLE,
        HIDDEN,
        COLLAPSE,
        INHERIT,
        INITIAL,
        UNSET,
        LOCAL
    }

    public enum FontStyle {
        NORMAL,
        ITALIC,
        OBLIQUE,
        INHERIT,
        INITIAL,
        UNSET,
        OBLIQUE_ITALIC,
        OBLIQUE_NORMAL,
        ITALIC_NORMAL,
        ITALIC_OBLIQUE,
        NORMAL_OBLIQUE,
        NORMAL_ITALIC,
        OBLIQUE_BOLD,
        ITALIC_BOLD,
        NORMAL_BOLD
    }

    public enum FontWeight {
        NORMAL,
        BOLD,
        BOLDER,
        LIGHTER,
        INITIAL,
        INHERIT,
        UNSET,
        BOLD_100,
        BOLD_200,
        BOLD_300,
        BOLD_400,
        BOLD_500,
        BOLD_600,
        BOLD_700,
        BOLD_800,
        BOLD_900
    }

    public enum TextTransform {
        NONE,
        CAPITALIZE,
        UPPERCASE,
        LOWERCASE,
        FULL_WIDTH,
        FULL_SIZE_KANA,
        INHERIT,
        INITIAL,
        UNSET,
        SMALL_CAPS,
        VERTICAL_LEFT,
        VERTICAL_RIGHT,
        VERTICAL_270,
        VERTICAL_180,
        VERTICAL_90,
        VERTICAL_0,
        VERTICAL_CENTER,
        VERTICAL_JUSTIFY,
        VERTICAL_MIXED,
        VERTICAL_INTER_WORD,
        VERTICAL_DISTRIBUTED,
        UPPER_EAST_ASIAN,
        UNICASE,
        TITLING_CAPS,
        TITLING_SMALL_CAPS,
        TITLING_VERT_CAPS,
        TITLING_VERT_SMALL_CAPS,
        TITLING_VERT_MIDDLE_CAPS,
        TITLING_VERT_MIDDLE_SMALL_CAPS,
        TITLING_VERT_UPRIGHT,
        TITLING_VERT_UPRIGHT_SMALL_CAPS,
        TITLING_VERT_LOWER_UPRIGHT,
        TITLING_VERT_LOWER_UPRIGHT_SMALL_CAPS,
        TITLING_VERT_UPPER_UPRIGHT,
        TITLING_VERT_UPPER_UPRIGHT_SMALL_CAPS,
        TITLING_VERT_ROTATED,
        TITLING_VERT_ROTATED_SMALL_CAPS,
        TITLING_VERT_LOWER_ROTATED,
        TITLING_VERT_LOWER_ROTATED_SMALL_CAPS,
        TITLING_VERT_UPPER_ROTATED,
        TITLING_VERT_UPPER_ROTATED_SMALL_CAPS,
        TITLING_ALL_SMALL_CAPS,
        TITLING_ALL_SMALL_CAPS_CAPS,
        TITLING_VERT_ALL_SMALL_CAPS,
        TITLING_VERT_ALL_SMALL_CAPS_CAPS
    }

    public enum WhiteSpace {
        NORMAL,
        NOWRAP,
        PRE,
        PRE_WRAP,
        PRE_LINE,
        BREAK_SPACES,
        INITIAL,
        INHERIT,
        UNSET,
        NORMAL_BREAK,
        NORMAL_NO_WRAP,
        NORMAL_PRE_WRAP,
        NORMAL_PRE_LINE,
        BREAK_ALL,
        BREAK_NORMAL,
        BREAK_STRICT
    }

    public enum ListStyleType {
        NONE("none"),
        DISC("disc"),
        CIRCLE("circle"),
        SQUARE("square"),
        DECIMAL("decimal"),
        LOWER_ROMAN("lower-roman"),
        UPPER_ROMAN("upper-roman"),
        LOWER_ALPHA("lower-alpha"),
        UPPER_ALPHA("upper-alpha"),
        ARMENIAN("armenian"),
        CJK_IDEOGRAPHIC("cjk-ideographic"),
        GEORGIAN("georgian"),
        HEBREW("hebrew"),
        HIRAGANA("hiragana"),
        KATAKANA("katakana"),
        LOWER_ALPHA_GREKISH("lower-greek"),
        LOWER_LATIN("lower-latin"),
        UPPER_LATIN("upper-latin"),
        CJK_HEAVENLY_STEM("cjk-heavenly-stem"),
        CJK_EARTHLY_BRANCH("cjk-earthly-branch"),
        ETHIOPIC_NUMERIC("ethiopic-numeric"),
        ETHIOPIC_HALEHAME("ethiopic-halehame"),
        ETHIOPIC_HALEHAME_AA_AMET("ethiopic-halehame-aa-amet");

        private final String value;

        ListStyleType(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    public enum ListStylePosition {
        INSIDE,
        OUTSIDE,
        INITIAL,
        INHERIT,
        UNSET,
        REVERSE_INSIDE,
        REVERSE_OUTSIDE,
        REVERSE_INITIAL,
        REVERSE_INHERIT
    }

    public enum VerticalAlign {
        BASELINE,
        SUB,
        SUPER,
        TOP,
        TEXT_TOP,
        MIDDLE,
        BOTTOM,
        TEXT_BOTTOM,
        INITIAL,
        INHERIT,
        UNSET,
        LENGTH,
        PERCENTAGE,
        SUB_SUPER,
        TEXT_TOP_SUB_SUPER,
        MIDDLE_SUB_SUPER,
        BASELINE_SUB_SUPER,
        BOTTOM_SUB_SUPER,
        TEXT_BOTTOM_SUB_SUPER,
        TOP_SUB_SUPER,
        TEXT_TOP_BOTTOM,
        MIDDLE_BOTTOM,
        BASELINE_BOTTOM,
        TEXT_BOTTOM_BOTTOM,
        TOP_BOTTOM,
        SUB_TEXT_TOP,
        SUPER_TEXT_TOP,
        SUB_MIDDLE,
        SUPER_MIDDLE,
        SUB_BASELINE,
        SUPER_BASELINE,
        SUB_BOTTOM,
        SUPER_BOTTOM,
        SUB_TEXT_BOTTOM,
        SUPER_TEXT_BOTTOM,
        SUB_TOP,
        SUPER_TOP
    }

    public enum TextOverflow {
        CLIP,
        ELLIPSIS,
        INITIAL,
        INHERIT,
        UNSET,
        STRING,
        ELLIPSIS_CLIP,
        ELLIPSIS_ELLIPSIS,
        ELLIPSIS_ELLIPSIS_CLIP,
        CLIP_ELLIPSIS,
        CLIP_ELLIPSIS_CLIP
    }

    public enum PageBreak {
        AUTO,
        ALWAYS,
        AVOID,
        LEFT,
        RIGHT,
        INITIAL,
        INHERIT,
        UNSET,
        RECTO,
        VERSO,
        RECTO_ALWAYS,
        VERSO_ALWAYS,
        RECTO_AVOID,
        VERSO_AVOID,
        RECTO_LEFT,
        VERSO_LEFT,
        RECTO_RIGHT,
        VERSO_RIGHT
    }

    public enum TableLayout {
        AUTO,
        FIXED,
        INHERIT,
        INITIAL,
        UNSET,
        FIXED_COLUMN_GROUP,
        FIXED_AUTO,
        FIXED_FIXED,
        FIXED_AUTO_COLUMN_GROUP,
        FIXED_COLUMN_GROUP_AUTO
    }

    public enum BorderCollapse {
        SEPARATE,
        COLLAPSE,
        INHERIT,
        INITIAL,
        UNSET,
        COLLAPSE_SEPARATE,
        SEPARATE_COLLAPSE,
        SEPARATE_SEPARATE,
        COLLAPSE_COLLAPSE
    }

    public enum CaptionSide {
        TOP,
        BOTTOM,
        INITIAL,
        INHERIT,
        UNSET
    }

    @Getter
    @Setter
    @Accessors(fluent = true)
    public static class CSSSize {
        private double value;
        private SizeType unit;

        public CSSSize(double value, SizeType unit) {
            this.value = value;
            this.unit = unit;
        }

        @Override
        public String toString() {
            return value + unit.getSymbol();
        }
    }
}
