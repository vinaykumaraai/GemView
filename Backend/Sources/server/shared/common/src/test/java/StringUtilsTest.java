/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.luretechnologies.server.common.utils.MaskItem;
import static com.luretechnologies.server.common.utils.StringUtils.maskXmlFields;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class StringUtilsTest {

    String xml1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<requestMessage xmlns=\"urn:schemas-visa-com:transaction-data-1.115\">\n"
            + "    <merchantID>v6p264p849552</merchantID>\n"
            + "    <merchantReferenceCode>170530120730</merchantReferenceCode>\n"
            + "    <purchaseTotals>\n"
            + "        <currency>usd</currency>\n"
            + "        <grandTotalAmount>9000.00</grandTotalAmount>\n"
            + "    </purchaseTotals>\n"
            + "    <pos>\n"
            + "        <entryMode>swiped</entryMode>\n"
            + "        <cardPresent>Y</cardPresent>\n"
            + "        <terminalCapability>2</terminalCapability>\n"
            + "        <trackData>;4111111111111111=181010114991888?</trackData>\n"
            + "        <terminalID>87654321</terminalID>\n"
            + "    </pos>\n"
            + "    <card>\n"
            + "        <cardType>001</cardType>\n"
            + "    </card>\n"
            + "    <ccCreditService run=\"true\">\n"
            + "        <commerceIndicator>retail</commerceIndicator>\n"
            + "    </ccCreditService>\n"
            + "    <thirdPartyCertificationNumber>849552183998</thirdPartyCertificationNumber>\n"
            + "    <merchantTransactionIdentifier>170530120730</merchantTransactionIdentifier>\n"
            + "</requestMessage>";

    public StringUtilsTest() {
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

    @Test
    public void maskXmlFieldsTest() {

        List<MaskItem> fields = new ArrayList<>();
        fields.add(new MaskItem("postalCode", 0, 0));
        fields.add(new MaskItem("cardPan", 6, 4));
        fields.add(new MaskItem("trackData", 5, 1));
        String result = maskXmlFields(xml1, fields);
        System.out.println(result);
    }
}
