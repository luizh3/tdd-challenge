import org.example.exception.InvalidDiscountException;
import org.example.exception.InvalidProductException;
import org.example.model.ProductModel;
import org.example.service.PromotionService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Test cases for the {@link PromotionService}.
 */

public class TestPromotionService {

    @Test
    public void testWithoutDiscount() {

        PromotionService promotionService = new PromotionService();

        List<ProductModel> products = new ArrayList<>();
        products.add( new ProductModel("Tomato", 10.00f, 1 ) );

        final BigDecimal totalPrice = promotionService.calculateInvoice( products, 0.00f );

        assertEquals( new BigDecimal("10.00"), totalPrice );

    }

    @Test
    public void testNegativeProductQuantity() {

        PromotionService promotionService = new PromotionService();

        List<ProductModel> products = new ArrayList<>();
        products.add( new ProductModel("Tomato", 10.00f, -1 ) );

        assertThrows( InvalidProductException.class, () -> promotionService.calculateInvoice( products, 30.00f ));

    }

    @Test
    public void testNegativeProductPrice() {

        PromotionService promotionService = new PromotionService();

        List<ProductModel> products = new ArrayList<>();
        products.add( new ProductModel("Tomato", -10.00f, 1 ) );

        assertThrows( InvalidProductException.class, () -> promotionService.calculateInvoice( products, 30.00f ));

    }

    @Test
    public void testNegativeDiscount() {

        PromotionService promotionService = new PromotionService();

        List<ProductModel> products = new ArrayList<>();
        products.add( new ProductModel("Tomato", 10.00f, 1 ) );

        assertThrows( InvalidDiscountException.class, () -> promotionService.calculateInvoice( products, -30.00f ));

    }

    @Test
    public void testWithValidDiscount() {

        PromotionService promotionService = new PromotionService();

        List<ProductModel> products = new ArrayList<>();
        products.add( new ProductModel("Tomato", 10.00f, 1 ) );

        final BigDecimal totalPrice = promotionService.calculateInvoice( products, 25.00f );

        assertEquals(new BigDecimal("7.50" ), totalPrice );
    }

    @Test
    public void testWithoutProducts() {

        PromotionService promotionService = new PromotionService();

        List<ProductModel> products = new ArrayList<>();

        final BigDecimal totalPrice = promotionService.calculateInvoice( products, 25.00f );

        assertEquals( new BigDecimal( "0.00" ) , totalPrice );
    }

    @Test
    public void testOnlyAdditionalDiscount() {

        PromotionService promotionService = new PromotionService();

        List<ProductModel> products = new ArrayList<>();
        products.add( new ProductModel("Tomato", 1250.00f, 1 ) );

        final BigDecimal totalPrice = promotionService.calculateInvoice( products, 0.00f );

        assertEquals(new BigDecimal("1150.00" ), totalPrice );
    }

    @Test
    public void testAdditionalDiscountAndPercentage() {

        PromotionService promotionService = new PromotionService();

        List<ProductModel> products = new ArrayList<>();
        products.add( new ProductModel("Tomato", 1250.00f, 1 ) );

        final BigDecimal totalPrice = promotionService.calculateInvoice( products, 10.00f );

        assertEquals(new BigDecimal("1035.00" ), totalPrice );
    }

    @Test
    public void testeWithMultipleProducts() {

        PromotionService promotionService = new PromotionService();

        List<ProductModel> products = new ArrayList<>();
        products.add( new ProductModel("Tomato", 5.00f, 1 ) );
        products.add( new ProductModel("Orange", 10.00f, 2 ) );
        products.add( new ProductModel("Banana", 2.50f, 3 ) );

        final BigDecimal totalPrice = promotionService.calculateInvoice( products, 5.00f );

        assertEquals( new BigDecimal( "30.88" ), totalPrice );
    }

    @Test
    public void testeWithDecimalDiscount() {

        PromotionService promotionService = new PromotionService();

        List<ProductModel> products = new ArrayList<>();
        products.add( new ProductModel("Tomato", 5.00f, 1 ) );
        products.add( new ProductModel("Orange", 10.00f, 2 ) );
        products.add( new ProductModel("Banana", 2.50f, 3 ) );

        final BigDecimal totalPrice = promotionService.calculateInvoice( products, 5.27f );

        assertEquals( new BigDecimal("30.79") , totalPrice );
    }

}
