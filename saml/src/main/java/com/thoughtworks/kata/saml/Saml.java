package com.thoughtworks.kata.saml;

import static com.google.common.collect.ImmutableList.of;
import static org.joda.time.DateTime.now;

import com.google.common.collect.ImmutableMap;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.xml.namespace.QName;
import org.opensaml.core.config.InitializationService;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.core.xml.schema.impl.XSStringBuilder;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.AttributeValue;
import org.opensaml.saml.saml2.core.Audience;
import org.opensaml.saml.saml2.core.AudienceRestriction;
import org.opensaml.saml.saml2.core.AuthnContext;
import org.opensaml.saml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml.saml2.core.AuthnStatement;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.opensaml.saml.saml2.core.impl.AssertionMarshaller;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.xml.util.XMLHelper;
import org.opensaml.xmlsec.config.JavaCryptoValidationInitializer;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureConstants;
import org.opensaml.xmlsec.signature.support.Signer;
import org.w3c.dom.Element;

public class Saml {

  public static void main(String[] args)
      throws Throwable {
    JavaCryptoValidationInitializer javaCryptoValidationInitializer =
        new JavaCryptoValidationInitializer();
    javaCryptoValidationInitializer.init();

    InitializationService.initialize();

    Assertion assertion = buildSAMLObject(Assertion.class);

    assertion.setID(UUID.randomUUID().toString());
    assertion.setIssueInstant(now());
    assertion.setIssuer(issuerOf("yaodan"));
    assertion.setSignature(signature());
    assertion.setSubject(subject());
    assertion.setConditions(conditions());
    assertion.getAttributeStatements()
        .add(createAttributeStatement(new ImmutableMap.Builder<String, List<String>>()
            .put("http://schemas.microsoft.com/identity/claims/tenantid", of("d1ee1acd-bc7a-4bc4-a787-938c49a83906"))
            .put("http://schemas.microsoft.com/identity/claims/objectidentifier", of("6370afc6-638b-4757-b979-384d590350ea"))
            .put("http://schemas.microsoft.com/identity/claims/displayname", of("Zhang, Yaodan /C"))
            .put("http://schemas.microsoft.com/identity/claims/identityprovider", of("https://sts.windows.net/d1ee1acd-bc7a-4bc4-a787-938c49a83906/"))
            .put("http://schemas.microsoft.com/claims/authnmethodsreferences", of("http://schemas.microsoft.com/ws/2008/06/identity/authenticationmethod/windows"))
            .put("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/givenname", of("Yaodan"))
            .put("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/surname", of("Zhang"))
            .put("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress", of("yaodan.zhang@exxonmobil.com"))
            .put("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name", of("yaodan.zhang@exxonmobil.com"))
            .build()));
    assertion.getAuthnStatements().add(authnStatement());

    AssertionMarshaller marshaller = new AssertionMarshaller();
    Element element = marshaller.marshall(assertion);

    Signer.signObject(assertion.getSignature());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    XMLHelper.writeNode(element, baos);
    String responseStr = new String(baos.toByteArray());


    System.out.println(responseStr);
  }

  private static AuthnStatement authnStatement()
      throws NoSuchFieldException, IllegalAccessException {
    AuthnContextClassRef authnContextClassRef = buildSAMLObject(AuthnContextClassRef.class);
    authnContextClassRef.setAuthnContextClassRef("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");

    AuthnContext authnContext = buildSAMLObject(AuthnContext.class);
    authnContext.setAuthnContextClassRef(authnContextClassRef);

    AuthnStatement authnStatement = buildSAMLObject(AuthnStatement.class);
    authnStatement.setAuthnInstant(now().minusMinutes(1));
    authnStatement.setAuthnContext(authnContext);
    return authnStatement;
  }

  private static Conditions conditions() throws NoSuchFieldException, IllegalAccessException {
    Conditions conditions = buildSAMLObject(Conditions.class);
    conditions.setNotBefore(now().minusMinutes(30));
    conditions.setNotOnOrAfter(now().plusMinutes(30));

    Audience audience = buildSAMLObject(Audience.class);
    audience.setAudienceURI("spn:50bc4351-35ae-4b13-9b87-2e21e25f782f");

    AudienceRestriction audienceRestriction = buildSAMLObject(AudienceRestriction.class);
    audienceRestriction.getAudiences().add(audience);

    conditions.getAudienceRestrictions().add(audienceRestriction);
    return conditions;
  }

  private static Subject subject() throws NoSuchFieldException, IllegalAccessException {
    Subject subject = buildSAMLObject(Subject.class);
    subject.setNameID(nameId());

    SubjectConfirmationData subjectConfirmationData = buildSAMLObject(
        SubjectConfirmationData.class);
    subjectConfirmationData.setNotOnOrAfter(now());
    subjectConfirmationData.setRecipient("https://hoengyw1.na.xom.com/sap/saml2/sp/acs/100");

    SubjectConfirmation subjectConfirmation = buildSAMLObject(SubjectConfirmation.class);
    subjectConfirmation.setSubjectConfirmationData(subjectConfirmationData);
    subjectConfirmation.setMethod("urn:oasis:names:tc:SAML:2.0:cm:bearer");

    subject.getSubjectConfirmations().add(subjectConfirmation);
    return subject;
  }

  private static NameID nameId() throws NoSuchFieldException, IllegalAccessException {
    NameID nameID = buildSAMLObject(NameID.class);
    nameID.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:persistent");
    nameID.setValue("yaodan.zhang");
    return nameID;
  }

  private static Signature signature() throws Throwable {
    Signature signature = buildSAMLObject(Signature.class);
    signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
    signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA256);
    signature.setSigningCredential(getSigningCredential());
    return signature;
  }

  private static Issuer issuerOf(String issuerValue)
      throws NoSuchFieldException, IllegalAccessException {
    Issuer issuer = buildSAMLObject(Issuer.class);
    issuer.setValue(issuerValue);
    return issuer;
  }

  @SuppressWarnings({"unchecked", "ConstantConditions"})
  private static <T> T buildSAMLObject(final Class<T> clazz)
      throws NoSuchFieldException, IllegalAccessException {
    XMLObjectBuilderFactory builderFactory =
        XMLObjectProviderRegistrySupport.getBuilderFactory();
    QName defaultElementName = (QName) clazz.getDeclaredField(
        "DEFAULT_ELEMENT_NAME").get(null);
    return (T) builderFactory.getBuilder(defaultElementName)
        .buildObject(defaultElementName);
  }

  private static Credential getSigningCredential() throws Throwable {
    // create public key (cert) portion of credential
    InputStream inStream = new FileInputStream("/Users/twer/exxon/spike/domain.crt");
    CertificateFactory cf = CertificateFactory.getInstance("X.509");
    X509Certificate publicKey = (X509Certificate) cf.generateCertificate(inStream);
    inStream.close();

    // create private key
    RandomAccessFile raf = new RandomAccessFile("/Users/twer/exxon/spike/pkcs8_key", "r");
    byte[] buf = new byte[(int) raf.length()];
    raf.readFully(buf);
    raf.close();

    PKCS8EncodedKeySpec kspec = new PKCS8EncodedKeySpec(buf);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    PrivateKey privateKey = kf.generatePrivate(kspec);

    return new BasicX509Credential(publicKey, privateKey);
  }


  private static AttributeStatement createAttributeStatement(
      Map<String, List<String>> attributes)
      throws NoSuchFieldException, IllegalAccessException {
    AttributeStatement attributeStatement = buildSAMLObject(AttributeStatement.class);

    attributeStatement.getAttributes().addAll(attributes.entrySet().stream().map(entry -> {
      try {
        Attribute attribute = buildSAMLObject(Attribute.class);
        attribute.setName(entry.getKey());

        attribute.getAttributeValues().addAll(entry.getValue().stream().map(it -> {
          XSString attributeValue = new XSStringBuilder()
              .buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
          attributeValue.setValue(it);
          return attributeValue;
        }).collect(Collectors.toList()));

        return attribute;

      } catch (NoSuchFieldException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }).collect(Collectors.toList()));

    return attributeStatement;
  }
}
