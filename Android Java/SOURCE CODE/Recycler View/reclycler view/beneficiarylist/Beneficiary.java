package com.app.alevant.DMT.beneficiarylist;

public class Beneficiary {
  private String BENE_ID;
  private String BENE_NAME;
  private String BENE_MOBILENO;
  private String BENE_NICKNAME;
  private String BENE_BANKNAME;
  private String BANK_ACCOUNTNO;
  private String BANKIFSC_CODE;
  private String BENE_OTP_VERIFIED;
  private String IS_BENEVERIFIED;
  private String BENEVERIFIED_STATUS;

  public Beneficiary(String BENE_ID, String BENE_NAME, String BENE_BANKNAME, String BANK_ACCOUNTNO, String BANKIFSC_CODE) {
    this.BENE_ID = BENE_ID;
    this.BENE_NAME = BENE_NAME;
    this.BENE_BANKNAME = BENE_BANKNAME;
    this.BANK_ACCOUNTNO = BANK_ACCOUNTNO;
    this.BANKIFSC_CODE = BANKIFSC_CODE;
  }

  public Beneficiary(String BENE_ID, String BENE_NAME, String BENE_BANKNAME, String BANK_ACCOUNTNO, String BANKIFSC_CODE, String BENE_OTP_VERIFIED, String IS_BENEVERIFIED) {
    this.BENE_ID = BENE_ID;
    this.BENE_NAME = BENE_NAME;
    this.BENE_BANKNAME = BENE_BANKNAME;
    this.BANK_ACCOUNTNO = BANK_ACCOUNTNO;
    this.BANKIFSC_CODE = BANKIFSC_CODE;
    this.BENE_OTP_VERIFIED = BENE_OTP_VERIFIED;
    this.IS_BENEVERIFIED = IS_BENEVERIFIED;
  }


  public String getBENE_ID() {
    return BENE_ID;
  }

  public void setBENE_ID(String BENE_ID) {
    this.BENE_ID = BENE_ID;
  }

  public String getBENE_NAME() {
    return BENE_NAME;
  }

  public void setBENE_NAME(String BENE_NAME) {
    this.BENE_NAME = BENE_NAME;
  }

  public String getBENE_MOBILENO() {
    return BENE_MOBILENO;
  }

  public void setBENE_MOBILENO(String BENE_MOBILENO) {
    this.BENE_MOBILENO = BENE_MOBILENO;
  }

  public String getBENE_NICKNAME() {
    return BENE_NICKNAME;
  }

  public void setBENE_NICKNAME(String BENE_NICKNAME) {
    this.BENE_NICKNAME = BENE_NICKNAME;
  }

  public String getBENE_BANKNAME() {
    return BENE_BANKNAME;
  }

  public void setBENE_BANKNAME(String BENE_BANKNAME) {
    this.BENE_BANKNAME = BENE_BANKNAME;
  }

  public String getBANK_ACCOUNTNO() {
    return BANK_ACCOUNTNO;
  }

  public void setBANK_ACCOUNTNO(String BANK_ACCOUNTNO) {
    this.BANK_ACCOUNTNO = BANK_ACCOUNTNO;
  }

  public String getBANKIFSC_CODE() {
    return BANKIFSC_CODE;
  }

  public void setBANKIFSC_CODE(String BANKIFSC_CODE) {
    this.BANKIFSC_CODE = BANKIFSC_CODE;
  }

  public String getBENE_OTP_VERIFIED() {
    return BENE_OTP_VERIFIED;
  }

  public void setBENE_OTP_VERIFIED(String BENE_OTP_VERIFIED) {
    this.BENE_OTP_VERIFIED = BENE_OTP_VERIFIED;
  }

  public String getIS_BENEVERIFIED() {
    return IS_BENEVERIFIED;
  }

  public void setIS_BENEVERIFIED(String IS_BENEVERIFIED) {
    this.IS_BENEVERIFIED = IS_BENEVERIFIED;
  }

  public String getBENEVERIFIED_STATUS() {
    return BENEVERIFIED_STATUS;
  }

  public void setBENEVERIFIED_STATUS(String BENEVERIFIED_STATUS) {
    this.BENEVERIFIED_STATUS = BENEVERIFIED_STATUS;
  }
}
