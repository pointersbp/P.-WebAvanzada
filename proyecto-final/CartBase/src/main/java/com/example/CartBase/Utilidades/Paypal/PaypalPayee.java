package com.example.CartBase.Utilidades.Paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

//final
public class PaypalPayee {
    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("merchant_id")
    private String merchandId;

    @JsonProperty("display_data")
    private PaypalPayeeDisplayData displayData;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMerchandId() {
        return merchandId;
    }

    public void setMerchandId(String merchandId) {
        this.merchandId = merchandId;
    }

    public PaypalPayeeDisplayData getDisplayData() {
        return displayData;
    }

    public void setDisplayData(PaypalPayeeDisplayData displayData) {
        this.displayData = displayData;
    }
}
