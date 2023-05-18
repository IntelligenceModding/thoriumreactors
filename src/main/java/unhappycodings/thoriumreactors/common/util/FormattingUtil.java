package unhappycodings.thoriumreactors.common.util;

import net.minecraft.network.chat.Style;

import java.text.DecimalFormat;

public class FormattingUtil {

    public static String formatNum(float num) {
        DecimalFormat formatter = new DecimalFormat("0.00");
        if (num >= 1000000000) return formatter.format(num / 1000).replaceAll(",", ".") + " GFE";
        if (num >= 1000000) return formatter.format(num / 1000).replaceAll(",", ".") + " MFE";
        if (num >= 1000) return formatter.format(num / 1000).replaceAll(",", ".") + " kFE";
        return (int) num + " FE";
    }

    public static String formatPercentNum(float num, float max) {
        DecimalFormat formatter = new DecimalFormat("0.00");
        String formatted = formatter.format(num / max * 100).replaceAll(",", ".") + " %";
        return formatted.equals("NaN %") ? "0 %" : formatted;
    }

    public static String formatPercentNum(float num, float max, boolean showDecimals) {
        DecimalFormat formatter = new DecimalFormat(showDecimals ? "0.00" : "0");
        String formatted = formatter.format(num / max * 100).replaceAll(",", ".") + " %";
        return formatted.equals("NaN %") ? "0 %" : formatted.replaceAll("-", "");
    }

    public static String formatEnergy(float num) {
        DecimalFormat formatter = new DecimalFormat("0.00");
        if (num >= 1000000000) return formatter.format(num / 1000).replaceAll(",", ".") + " GFE";
        if (num >= 1000000) return formatter.format(num / 1000).replaceAll(",", ".") + " MFE";
        if (num >= 1000) return formatter.format(num / 1000).replaceAll(",", ".") + " kFE";
        return (int) num + " FE";
    }

    public static Style hex(int hex) {
        return Style.EMPTY.withColor(hex);
    }

}
