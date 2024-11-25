package org.example.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DecimalHelper {

    public static BigDecimal round( float valor, int precision ) {
        return new BigDecimal( valor ).setScale( precision, RoundingMode.HALF_EVEN);
    }

}
