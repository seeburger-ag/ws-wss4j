/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.swssf.test;

import org.swssf.impl.transformer.canonicalizer.Canonicalizer11_OmitCommentsTransformer;
import org.swssf.impl.transformer.canonicalizer.Canonicalizer11_WithCommentsTransformer;
import org.swssf.impl.transformer.canonicalizer.CanonicalizerBase;
import org.swssf.test.utils.XMLEventNSAllocator;
import org.testng.annotations.Test;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.testng.Assert.*;

/**
 * @author $Author: giger $
 * @version $Revision: 272 $ $Date: 2010-12-23 14:30:56 +0100 (Thu, 23 Dec 2010) $
 */
public class Canonicalizer11Test {

    private XMLInputFactory xmlInputFactory;

    public Canonicalizer11Test() throws Exception {
        this.xmlInputFactory = XMLInputFactory.newFactory();
        this.xmlInputFactory.setEventAllocator(new XMLEventNSAllocator());
        XMLResolver xmlResolver = new XMLResolver() {
            public Object resolveEntity(String publicID, String systemID, String baseURI, String namespace) throws XMLStreamException {
                return this.getClass().getClassLoader().getResourceAsStream("testdata/c14n/in/" + systemID);
            }
        };
        this.xmlInputFactory.setXMLResolver(xmlResolver);
    }

    /**
     * 3.1 PIs, Comments, and Outside of Document Element
     */
    @Test
    public void test31withCommentsSubtree() throws Exception {

        String descri =
                "3.1: PIs, Comments, and Outside of Document Element. (commented)";

        URL fileIn = this.getClass().getClassLoader().getResource("testdata/c14n/in/31_input.xml");
        URL fileRef = this.getClass().getClassLoader().getResource("testdata/c14n/in/31_c14n-comments.xml");

        c14nAndCompare(fileIn, fileRef, false);
    }

    /**
     * 3.1 PIs, Comments, and Outside of Document Element
     *
     * @see <A HREF="http://www.w3.org/TR/2001/PR-xml-c14n-20010119#Example-OutsideDoc">the example from the spec</A>
     */
    @Test
    public void test31subtree() throws Exception {

        String descri =
                "3.1: PIs, Comments, and Outside of Document Element. (uncommented)";
        URL fileIn = this.getClass().getClassLoader().getResource("testdata/c14n/in/31_input.xml");
        URL fileRef = this.getClass().getClassLoader().getResource("testdata/c14n/in/31_c14n.xml");

        c14nAndCompare(fileIn, fileRef, true);
    }

    /**
     * 3.2 Whitespace in Document Content
     *
     * @see <A HREF="http://www.w3.org/TR/2001/PR-xml-c14n-20010119#Example-WhitespaceInContent">the example from the spec</A>
     */
    @Test
    public void test32subtree() throws Exception {

        String descri = "3.2 Whitespace in Document Content. (uncommented)";
        URL fileIn = this.getClass().getClassLoader().getResource("testdata/c14n/in/32_input.xml");
        URL fileRef = this.getClass().getClassLoader().getResource("testdata/c14n/in/32_c14n.xml");

        c14nAndCompare(fileIn, fileRef, true);
    }

    /**
     * 3.3 Start and End Tags
     *
     * @see <A HREF="http://www.w3.org/TR/2001/PR-xml-c14n-20010119#Example-SETags">the example from the spec</A>
     */
    @Test
    public void test33subtree() throws Exception {

        String descri = "3.3 Start and End Tags. (uncommented)";

        URL fileIn = this.getClass().getClassLoader().getResource("testdata/c14n/in/33_input.xml");
        URL fileRef = this.getClass().getClassLoader().getResource("testdata/c14n/in/33_c14n.xml");

        c14nAndCompare(fileIn, fileRef, true);
    }

    /**
     * 3.4 Character Modifications and Character References
     *
     * @see <A HREF="http://www.w3.org/TR/2001/PR-xml-c14n-20010119#Example-Chars">the example from the spec</A>
     */
    @Test
    public void test34() throws Exception {

        String descri =
                "3.4 Character Modifications and Character References. (uncommented)";

        URL fileIn = this.getClass().getClassLoader().getResource("testdata/c14n/in/34_input.xml");
        URL fileRef = this.getClass().getClassLoader().getResource("testdata/c14n/in/34_c14n.xml");

        c14nAndCompare(fileIn, fileRef, true);
    }

    /**
     * 3.4 Character Modifications and Character References (patched to run on validating Parsers)
     * <p/>
     * <A HREF="http://www.w3.org/TR/2001/PR-xml-c14n-20010119"> The spec</A> states that:
     * <p/>
     * Note: The last element, normId, is well-formed but violates a validity
     * constraint for attributes of type ID. For testing canonical XML
     * implementations based on validating processors, remove the line
     * containing this element from the input and canonical form. In general,
     * XML consumers should be discouraged from using this feature of XML.
     *
     * @see <A HREF="http://www.w3.org/TR/2001/PR-xml-c14n-20010119#Example-Chars">the example from the spec</A>
     */
    @Test
    public void test34subtree() throws Exception {
        //todo enable validation
        String descri =
                "3.4 Character Modifications and Character References. (uncommented, patched to run on validating Parsers)";

        URL fileIn = this.getClass().getClassLoader().getResource("testdata/c14n/in/34_input_validatingParser.xml");
        URL fileRef = this.getClass().getClassLoader().getResource("testdata/c14n/in/34_c14n_validatingParser.xml");

        c14nAndCompare(fileIn, fileRef, true);
    }

    /**
     * 3.5 Entity References
     *
     * @see <A HREF="http://www.w3.org/TR/2001/PR-xml-c14n-20010119#Example-Entities">the example from the spec</A>
     */
    @Test
    public void test35subtree() throws Exception {
        //todo enable validation
        String descri = "3.5 Entity References. (uncommented)";

        URL fileIn = this.getClass().getClassLoader().getResource("testdata/c14n/in/35_input.xml");
        URL fileRef = this.getClass().getClassLoader().getResource("testdata/c14n/in/35_c14n.xml");

        c14nAndCompare(fileIn, fileRef, true);
    }

    /**
     * 3.6 UTF-8 Encoding
     *
     * @see <A HREF="http://www.w3.org/TR/2001/PR-xml-c14n-20010119#Example-UTF8">the example from the spec</A>
     */
    @Test
    public void test36subtree() throws Exception {

        String descri = "3.6 UTF-8 Encoding. (uncommented)";

        URL fileIn = this.getClass().getClassLoader().getResource("testdata/c14n/in/36_input.xml");
        URL fileRef = this.getClass().getClassLoader().getResource("testdata/c14n/in/36_c14n.xml");

        c14nAndCompare(fileIn, fileRef, true);
    }

    /**
     * 3.8 Document Subsets and XML Attributes (modified)
     *
     * @see <A HREF="http://www.w3.org/TR/2007/CR-xml-c14n11-20070621/#Example-DocSubsetsXMLAttrs">the example from the spec</A>
     */
    @Test
    public void test38() throws Exception {
        String descri = "3.6 UTF-8 Encoding. (uncommented)";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Canonicalizer11_OmitCommentsTransformer c = new Canonicalizer11_OmitCommentsTransformer(null, baos);
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(
                this.getClass().getClassLoader().getResourceAsStream("testdata/c14n/in/38_input.xml")
        );

        XMLEvent xmlEvent = null;
        while (xmlEventReader.hasNext()) {
            xmlEvent = xmlEventReader.nextEvent();
            if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().equals(new QName("http://www.ietf.org", "e1"))) {
                break;
            }
        }
        while (xmlEventReader.hasNext()) {

            c.transform(xmlEvent);

            if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().equals(new QName("http://www.ietf.org", "e1"))) {
                break;
            }
            xmlEvent = xmlEventReader.nextEvent();
        }

        byte[] reference = getBytesFromResource(this.getClass().getClassLoader().getResource("testdata/c14n/in/38_c14n.xml"));
        boolean equals = java.security.MessageDigest.isEqual(reference, baos.toByteArray());

        if (equals == false) {
            System.out.println("Expected:\n" + new String(reference, "UTF-8"));
            System.out.println("");
            System.out.println("Got:\n" + new String(baos.toByteArray(), "UTF-8"));
        }

        assertTrue(equals);
    }

//   /**
//    * 3.7 Document Subsets
//    *
//    * @throws CanonicalizationException
//    * @throws java.io.FileNotFoundException
//    * @throws java.io.IOException
//    * @throws InvalidCanonicalizerException
//    * @throws javax.xml.parsers.ParserConfigurationException
//    * @throws org.xml.sax.SAXException
//    * @see <A HREF="http://www.w3.org/TR/2001/PR-xml-c14n-20010119#Example-DocSubsets">the example from the spec</A>
//    * @throws javax.xml.transform.TransformerException
//    */
//   public static void test37() throws Exception {
//
//      String descri = "3.7 Document Subsets. (uncommented)";
//      String fileIn = prefix + "in/37_input.xml";
//      String fileRef = prefix + "in/37_c14n.xml";
//      String fileOut = prefix + "out/xpath_37_output.xml";
//      String c14nURI = Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS;
//      boolean validating = true;
//      Element xpath = null;
//      DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
//
//      dfactory.setNamespaceAware(true);
//
//      DocumentBuilder db = dfactory.newDocumentBuilder();
//      Document doc = db.newDocument();
//
//      xpath = XMLUtils.createElementInSignatureSpace(doc, Constants._TAG_XPATH);
//
//      xpath.setAttributeNS(Constants.NamespaceSpecNS, "xmlns:ietf", "http://www.ietf.org");
//
//      //J-
//         String xpathFromSpec =
//              "(//. | //@* | //namespace::*)"
//            + "[ "
//            + "self::ietf:e1 or "
//            + "(parent::ietf:e1 and not(self::text() or self::e2)) or "
//            + "count(id(\"E3\")|ancestor-or-self::node()) = count(ancestor-or-self::node()) "
//            + "]";
//
//         //J+
//      xpath.appendChild(doc.createTextNode(xpathFromSpec));
//      assertTrue(descri,
//                 c14nAndCompare(fileIn, fileRef, fileOut, c14nURI, validating,
//                                xpath));
//   }
//

    /**
     * Note: This specification supports the recent XML plenary decision to
     * deprecate relative namespace URIs as follows: implementations of XML
     * canonicalization MUST report an operation failure on documents containing
     * relative namespace URIs. XML canonicalization MUST NOT be implemented
     * with an XML parser that converts relative URIs to absolute URIs.
     * <p/>
     * Implementations MUST report an operation failure on documents containing
     * relative namespace URIs.
     */
    @Test
    public void testRelativeNSbehaviour() throws Exception {

        URL fileIn = this.getClass().getClassLoader().getResource("testdata/c14n/in/relative-ns-behaviour.xml");

        try {
            c14nAndCompare(fileIn, fileIn, true);
            fail();
        } catch (XMLStreamException cex) {
            assertTrue(cex != null);
        }
    }

//   /**
//    * The XPath data model represents data using UCS characters.
//    * Implementations MUST use XML processors that support UTF-8 and UTF-16
//    * and translate to the UCS character domain. For UTF-16, the leading byte
//    * order mark is treated as an artifact of encoding and stripped from the
//    * UCS character data (subsequent zero width non-breaking spaces appearing
//    * within the UTF-16 data are not removed) [UTF-16, Section 3.2]. Support
//    * for ISO-8859-1 encoding is RECOMMENDED, and all other character encodings
//    * are OPTIONAL.
//    *
//    * $todo$ implement the test
//    * @throws CanonicalizationException
//    * @throws java.io.FileNotFoundException
//    * @throws java.io.IOException
//    * @throws InvalidCanonicalizerException
//    * @throws javax.xml.parsers.ParserConfigurationException
//    * @throws org.xml.sax.SAXException
//    * @throws javax.xml.transform.TransformerException
//    */
//   public static void testTranslationFromUTF16toUTF8() throws Exception {
//
//      String val =
//         "<UTF16>The german &amp;auml (which is Unicode &amp;#xE4;):  &quot;&#xE4;&quot;</UTF16>";
//      byte utf16[] = convertToUTF16(val.getBytes());
//      Canonicalizer c14n =
//         Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS);
//      byte c14nBytes[] = c14n.canonicalize(utf16);
//      org.xml.sax.EntityResolver resolver = new TestVectorResolver();
//      InputStream refStream = resolver.resolveEntity(
//         null,
//            prefix + "/in/testTranslationFromUTF16toUTF8.xml")
//               .getByteStream();
//      byte refBytes[] = JavaUtils.getBytesFromStream(refStream);
//      boolean equal = java.security.MessageDigest.isEqual(refBytes, c14nBytes);
//
//      assertTrue("Parser does not translate to UCS character domain", equal);
//   }
//
//   /**
//    * Method testXMLAttributes1
//    *
//    * @throws CanonicalizationException
//    * @throws java.io.FileNotFoundException
//    * @throws java.io.IOException
//    * @throws InvalidCanonicalizerException
//    * @throws javax.xml.parsers.ParserConfigurationException
//    * @throws org.xml.sax.SAXException
//    * @throws javax.xml.transform.TransformerException
//    */
//   public static void testXMLAttributes1() throws Exception {
//      //J-
//      String input = ""
//         + "<included    xml:lang='de'>"
//         + "<notIncluded xml:lang='de'>"
//         + "<notIncluded xml:lang='uk'>"
//         + "<included                 >"
//         + "</included>"
//         + "</notIncluded>"
//         + "</notIncluded>"
//         + "</included>";
//
//      String definedOutput = ""
//         + "<included xml:lang=\"de\">"
//         + "<included xml:lang=\"uk\">"
//         + "</included>"
//         + "</included>";
//      //J+
//      assertTrue(doTestXMLAttributes(input, definedOutput));
//   }
//
//   /**
//    * Method testXMLAttributes2
//    *
//    * @throws CanonicalizationException
//    * @throws java.io.FileNotFoundException
//    * @throws java.io.IOException
//    * @throws InvalidCanonicalizerException
//    * @throws javax.xml.parsers.ParserConfigurationException
//    * @throws org.xml.sax.SAXException
//    * @throws javax.xml.transform.TransformerException
//    */
//   public static void testXMLAttributes2() throws Exception {
//      //J-
//      String input = ""
//         + "<included    xml:lang='uk'>"
//         + "<notIncluded xml:lang='de'>"
//         + "<notIncluded xml:lang='uk'>"
//         + "<included                 >"
//         + "</included>"
//         + "</notIncluded>"
//         + "</notIncluded>"
//         + "</included>";
//
//      String definedOutput = ""
//         + "<included xml:lang=\"uk\">"
//         + "<included xml:lang=\"uk\">"
//         + "</included>"
//         + "</included>";
//      //J+
//      assertTrue(doTestXMLAttributes(input, definedOutput));
//   }
//
//   /**
//    * Method testXMLAttributes3
//    *
//    * @throws CanonicalizationException
//    * @throws java.io.FileNotFoundException
//    * @throws java.io.IOException
//    * @throws InvalidCanonicalizerException
//    * @throws javax.xml.parsers.ParserConfigurationException
//    * @throws org.xml.sax.SAXException
//    * @throws javax.xml.transform.TransformerException
//    */
//   public static void testXMLAttributes3() throws Exception {
//      //J-
//      String input = ""
//         + "<included    xml:lang='de'>"
//         + "<notIncluded xml:lang='de'>"
//         + "<notIncluded xml:lang='uk'>"
//         + "<included    xml:lang='de'>"
//         + "</included>"
//         + "</notIncluded>"
//         + "</notIncluded>"
//         + "</included>";
//
//      String definedOutput = ""
//         + "<included xml:lang=\"de\">"
//         + "<included xml:lang=\"de\">"
//         + "</included>"
//         + "</included>";
//      //J+
//      assertTrue(doTestXMLAttributes(input, definedOutput));
//   }
//
//   /**
//    * Method testXMLAttributes4
//    *
//    * @throws CanonicalizationException
//    * @throws java.io.FileNotFoundException
//    * @throws java.io.IOException
//    * @throws InvalidCanonicalizerException
//    * @throws javax.xml.parsers.ParserConfigurationException
//    * @throws org.xml.sax.SAXException
//    * @throws javax.xml.transform.TransformerException
//    */
//   public static void _testXMLAttributes4() throws Exception {
//      //J-
//      String input = ""
//         + "<included    xml:lang='de'>"
//         + "<included    xml:lang='de'>"
//         + "<notIncluded xml:lang='uk'>"
//         + "<included                 >"
//         + "</included>"
//         + "</notIncluded>"
//         + "</included>"
//         + "</included>";
//
//      String definedOutput = ""
//         + "<included xml:lang=\"de\">"
//         + "<included>"
//         + "<included xml:lang=\"uk\">"
//         + "</included>"
//         + "</included>"
//         + "</included>";
//      //J+
//      assertTrue(doTestXMLAttributes(input, definedOutput));
//   }
//
//   /**
//    * Method testXMLAttributes5
//    *
//    * @throws CanonicalizationException
//    * @throws java.io.FileNotFoundException
//    * @throws java.io.IOException
//    * @throws InvalidCanonicalizerException
//    * @throws javax.xml.parsers.ParserConfigurationException
//    * @throws org.xml.sax.SAXException
//    * @throws javax.xml.transform.TransformerException
//    */
//   public static void _testXMLAttributes5() throws Exception {
//      //J-
//      String input = ""
//         + "<included                         xml:lang='de'>"
//         + "<included                         xml:lang='de'>"
//         + "<notIncluded xml:space='preserve' xml:lang='uk'>"
//         + "<included                 >"
//         + "</included>"
//         + "</notIncluded>"
//         + "</included>"
//         + "</included>";
//
//      String definedOutput = ""
//         + "<included xml:lang=\"de\">"
//         + "<included>"
//         + "<included xml:lang=\"uk\" xml:space=\"preserve\">"
//         + "</included>"
//         + "</included>"
//         + "</included>";
//      //J+
//      assertTrue(doTestXMLAttributes(input, definedOutput));
//   }
//
//   /**
//    * Method testXMLAttributes6
//    *
//    * @throws CanonicalizationException
//    * @throws java.io.FileNotFoundException
//    * @throws java.io.IOException
//    * @throws InvalidCanonicalizerException
//    * @throws javax.xml.parsers.ParserConfigurationException
//    * @throws org.xml.sax.SAXException
//    * @throws javax.xml.transform.TransformerException
//    */
//   public static void _testXMLAttributes6() throws Exception {
//      //J-
//      String input = ""
//         + "<included   xml:space='preserve'  xml:lang='de'>"
//         + "<included                         xml:lang='de'>"
//         + "<notIncluded                      xml:lang='uk'>"
//         + "<included>"
//         + "</included>"
//         + "</notIncluded>"
//         + "</included>"
//         + "</included>";
//
//      String definedOutput = ""
//         + "<included xml:lang=\"de\" xml:space=\"preserve\">"
//         + "<included>"
//         + "<included xml:lang=\"uk\" xml:space=\"preserve\">"
//         + "</included>"
//         + "</included>"
//         + "</included>";
//      //J+
//      assertTrue(doTestXMLAttributes(input, definedOutput));
//   }
//
//   /**
//    * Method doTestXMLAttributes
//    *
//    * @param input
//    * @param definedOutput
//    * @param writeResultsToFile
//    *
//    * @throws CanonicalizationException
//    * @throws java.io.FileNotFoundException
//    * @throws java.io.IOException
//    * @throws InvalidCanonicalizerException
//    * @throws javax.xml.parsers.ParserConfigurationException
//    * @throws org.xml.sax.SAXException
//    * @throws javax.xml.transform.TransformerException
//    */
//   private static boolean doTestXMLAttributes(
//           String input, String definedOutput) throws Exception {
//
//      DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
//
//      dfactory.setNamespaceAware(true);
//      dfactory.setValidating(true);
//
//      DocumentBuilder db = dfactory.newDocumentBuilder();
//
//      db.setErrorHandler(new org.apache.xml.security.utils
//         .IgnoreAllErrorHandler());
//
//      Document doc = db.parse(new ByteArrayInputStream(input.getBytes()));
//      Canonicalizer c14nizer =
//         Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS);
//      CachedXPathAPI xpathAPI = new CachedXPathAPI();
//
//      //XMLUtils.circumventBug2650(doc);
//
//      NodeList nodes =
//         xpathAPI.selectNodeList(doc, "(//*[local-name()='included'] | //@*[parent::node()[local-name()='included']])");
//      byte result[] = c14nizer.canonicalizeXPathNodeSet(nodes);
//      byte defined[] = definedOutput.getBytes();
//      assertEquals(definedOutput, new String(result));
//      return java.security.MessageDigest.isEqual(defined, result);
//   }

    /**
     * Method c14nAndCompare
     */
    private void c14nAndCompare(
            URL fileIn, URL fileRef, boolean omitComments) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        CanonicalizerBase canonicalizerBase;
        if (omitComments) {
            canonicalizerBase = new Canonicalizer11_OmitCommentsTransformer(null, baos);
        } else {
            canonicalizerBase = new Canonicalizer11_WithCommentsTransformer(null, baos);
        }

        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(fileIn.openStream());
        while (xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            canonicalizerBase.transform(xmlEvent);
        }

        // org.xml.sax.InputSource refIs = resolver.resolveEntity(null, fileRef);
        // byte refBytes[] = JavaUtils.getBytesFromStream(refIs.getByteStream());
        byte refBytes[] = getBytesFromResource(fileRef);

        // if everything is OK, result is true; we do a binary compare, byte by byte
        boolean result = java.security.MessageDigest.isEqual(refBytes, baos.toByteArray());
        if (!result) {
            assertEquals(new String(baos.toByteArray()), new String(refBytes));
        }
        assertTrue(result);
    }


    public static byte[] getBytesFromResource(URL resource) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream inputStream = resource.openStream();
        try {
            byte buf[] = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                baos.write(buf, 0, len);
            }

            return baos.toByteArray();
        } finally {
            inputStream.close();
        }
    }

//   /**
//    * This method takes the input bytes as XML Document and converts it to an
//    * UTF-16 encoded XML document which is serialized to byte[] and returned.
//    *
//    * @param input
//    *
//    * @throws java.io.IOException
//    * @throws javax.xml.parsers.ParserConfigurationException
//    * @throws org.xml.sax.SAXException
//    * @throws javax.xml.transform.TransformerConfigurationException
//    * @throws javax.xml.transform.TransformerException
//    */
//   public static byte[] convertToUTF16(byte input[]) throws Exception {
//
//      //String ENCODING_ISO8859_1 = "ISO-8859-1";
//      //String ENCODING_UTF8 = "UTF-8";
//      String ENCODING_UTF16 = "UTF-16";
//      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//      DocumentBuilder db = dbf.newDocumentBuilder();
//      Document doc = db.parse(new ByteArrayInputStream(input));
//      TransformerFactory tFactory = TransformerFactory.newInstance();
//      Transformer transformer = tFactory.newTransformer();
//
//      transformer.setOutputProperty(OutputKeys.ENCODING, ENCODING_UTF16);
//      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
//
//      DOMSource source = new DOMSource(doc);
//      ByteArrayOutputStream os = new ByteArrayOutputStream();
//      StreamResult result = new StreamResult(os);
//
//      transformer.transform(source, result);
//
//      return os.toByteArray();
//   }
}
