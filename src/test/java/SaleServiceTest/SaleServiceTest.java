/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SaleServiceTest;

import com.mycompany.salesmanagementsystem.Product;
import com.mycompany.salesmanagementsystem.SaleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class SaleServiceTest {

    private final SaleService service = new SaleService();

    // 1. TEST CALCULATE SUBTOTAL
    @ParameterizedTest
    @CsvSource({
        "100, 2, 200",
        "500, 3, 1500",
        "1000, 5, 5000",
        "99.99, 2, 199.98"
    })
    void testCalculateSubtotal(
            double price, int quantity, double expected) {

        Product product = new Product(
                "P01", "Laptop", price, quantity);

        assertEquals(
                expected,
                service.calculateSubtotal(product),
                0.001
        );
    }

    @Test
    void testCalculateSubtotalNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.calculateSubtotal(null)
        );
    }

    // 2. TEST CALCULATE DISCOUNT
    @ParameterizedTest
    @CsvSource({
        "0, 0",
        "999.99, 0",
        "1000, 50",
        "4999.99, 249.9995",
        "5000, 500",
        "9999.99, 999.999",
        "10000, 1500",
        "20000, 3000"
    })
    void testCalculateDiscount(
            double subtotal, double expected) {

        assertEquals(
                expected,
                service.calculateDiscount(subtotal),
                0.001
        );
    }

    @Test
    void testCalculateDiscountNegative() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.calculateDiscount(-1)
        );
    }

    // 3. TEST CALCULATE SHIPPING FEE
    @ParameterizedTest
    @CsvSource({
        "0, 50",
        "1000, 50",
        "1999.99, 50",
        "2000, 0",
        "5000, 0"
    })
    void testCalculateShippingFee(
            double subtotal, double expected) {

        assertEquals(
                expected,
                service.calculateShippingFee(subtotal),
                0.001
        );
    }

    @Test
    void testShippingFeeNegative() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.calculateShippingFee(-1)
        );
    }

    // 4. TEST CALCULATE TOTAL
    @ParameterizedTest
    @CsvSource({
        "100, 2, 250",
        "500, 2, 1000",
        "1000, 2, 1900",
        "1000, 5, 4500",
        "2000, 5, 8500"
    })
    void testCalculateTotal(
            double price, int quantity, double expected) {

        Product product = new Product(
                "P01", "Laptop", price, quantity);

        assertEquals(
                expected,
                service.calculateTotal(product),
                0.001
        );
    }

    @Test
    void testCalculateTotalNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.calculateTotal(null)
        );
    }

    // 5. TEST CLASSIFY CUSTOMER
    @ParameterizedTest
    @CsvSource({
        "0, REGULAR",
        "999.99, REGULAR",
        "1000, SILVER",
        "4999.99, SILVER",
        "5000, GOLD",
        "10000, GOLD",
        "10000.01, VIP",
        "20000, VIP"
    })
    void testClassifyCustomer(
            double total, String expected) {

        assertEquals(
                expected,
                service.classifyCustomer(total)
        );
    }

    // 6. TEST PRODUCT VALIDATION
    @Test
    void testProductNullId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product(null, "Laptop", 1000, 2)
        );
    }

    @Test
    void testProductEmptyName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product("P01", "", 1000, 2)
        );
    }

    @Test
    void testProductInvalidPrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product("P01", "Laptop", 0, 2)
        );
    }

    @Test
    void testProductInvalidQuantity() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product("P01", "Laptop", 1000, 0)
        );
    }
}
