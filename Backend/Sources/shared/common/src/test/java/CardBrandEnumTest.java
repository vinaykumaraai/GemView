/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.luretechnologies.common.enums.CardBrandEnum;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author carlos
 */
public class CardBrandEnumTest {

    public CardBrandEnumTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private void match(String label, CardBrandEnum brand, String pan) {
        CardBrandEnum match = CardBrandEnum.infer(pan);
        assertTrue(String.format("%s -> %s -> %s", label, pan, match.getName()), brand == match);
    }

    @Test
    public void Visa() {
        CardBrandEnum brand = CardBrandEnum.VISA;
        String label = brand.getName();

        match(label, brand, "4242424242424242");
    }

    @Test
    public void MasterCard() {
        CardBrandEnum brand = CardBrandEnum.MASTERCARD;
        String label = brand.getName();

        match(label, brand, "5555555555554444");
        match(label, brand, "2222222222222224");
    }

    @Test
    public void Amex() {
        CardBrandEnum brand = CardBrandEnum.AMEX;
        String label = brand.getName();

        match(label, brand, "378282246310005");
    }

    @Test
    public void Discover() {
        CardBrandEnum brand = CardBrandEnum.DISCOVER;
        String label = brand.getName();

        match(label, brand, "6011111111111117");
    }
}
