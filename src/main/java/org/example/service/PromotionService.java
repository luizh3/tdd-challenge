package org.example.service;

import org.example.exception.InvalidDiscountException;
import org.example.exception.InvalidProductException;
import org.example.helper.DecimalHelper;
import org.example.model.ProductModel;

import java.math.BigDecimal;
import java.util.List;

public class PromotionService {

    public static final float NR_GREATER_VALUE_FOR_ADDITIONAL_DISCOUNT = 1000.00f;
    public static final float NR_ADDITIONAL_DISCOUNT_VALUE = 100.00f;

    public BigDecimal calculateInvoice( List<ProductModel> products, float discount ) throws InvalidDiscountException, InvalidProductException {

        if( products.isEmpty() ) {
            return new BigDecimal( "0.00" );
        }

        if( discount < 0.00f ) {
            throw new InvalidDiscountException( "Value of discount is invalid" );
        }

        float totalValue = products.stream()
            .map( item -> {

                if ( item.getQuantity() < 0 ) {
                    throw new InvalidProductException("Invalid quantity");
                }

                if ( item.getPrice() < 0.00f ) {
                    throw new InvalidProductException("Invalid price");
                }

                return item.getPrice() * item.getQuantity();

            })
            .reduce(0.00f, Float::sum);

        if( totalValue > NR_GREATER_VALUE_FOR_ADDITIONAL_DISCOUNT ) {
            totalValue -= NR_ADDITIONAL_DISCOUNT_VALUE;
        }

        if( discount > 0.00f ) {
            totalValue -= (  totalValue * discount ) / 100;
        }

        return DecimalHelper.round( totalValue, 2 );

    }

}
