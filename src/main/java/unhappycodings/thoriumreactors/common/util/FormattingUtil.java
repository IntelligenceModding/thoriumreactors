package unhappycodings.thoriumreactors.common.util;

import net.minecraft.network.chat.Style;

import java.text.DecimalFormat;

public class FormattingUtil {

    public static float getTurbineGenerationModifier(float rpm) {
        if (rpm < 100) return 0.1f;
        if (rpm < 200) return 0.6f;
        if (rpm < 300) return 1.3f;
        if (rpm < 400) return 2.1f;
        if (rpm < 500) return 3.3f;
        if (rpm < 600) return 3.5f;
        if (rpm < 700) return 4.3f;
        if (rpm < 800) return 5.1f;
        if (rpm < 900) return 6.2f;
        if (rpm < 1000) return 7f;
        if (rpm < 1100) return 9f;
        if (rpm < 1200) return 10f;
        if (rpm < 1300) return 11f;
        if (rpm < 1400) return 14f;
        if (rpm < 1500) return 17f;
        if (rpm < 1600) return 21f;
        if (rpm < 1700) return 29f;
        if (rpm < 1800) return 38f;
        if (rpm < 1900) return 47;
        if (rpm < 1950) return 55;
        if (rpm < 2000) return 58;
        if (rpm < 2050) return 55;
        if (rpm < 2100) return 47f;
        if (rpm < 2200) return 39f;
        if (rpm < 2300) return 34f;
        if (rpm < 2400) return 31f;
        return 28;
    }

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
        if (num >= 1000000000) return formatter.format(num / 1000000000).replaceAll(",", ".") + " GFE";
        if (num >= 1000000) return formatter.format(num / 1000000).replaceAll(",", ".") + " MFE";
        if (num >= 1000) return formatter.format(num / 1000).replaceAll(",", ".") + " kFE";
        return Math.round(num * 100f) / 100f + " FE";
    }

    public static String formatEnergyComma(float num) {
        DecimalFormat formatter = new DecimalFormat("0.00");
        if (num >= 1000000000) return formatter.format(num / 1000000000) + " GFE";
        if (num >= 1000000) return formatter.format(num / 1000000) + " MFE";
        if (num >= 1000) return formatter.format(num / 1000) + " kFE";
        return Math.round(num * 100f) / 100f + " FE";
    }

    public static Style hex(int hex) {
        return Style.EMPTY.withColor(hex);
    }

}
