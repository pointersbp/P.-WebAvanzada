package com.example.CartBase.Utilidades.Paypal.Webhook;

import com.example.CartBase.Utilidades.Paypal.PaypalLinkDescription;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class PaypalEvent {
    @JsonProperty("id")
    private String id;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("resource_type")
    private String resourceType;

    @JsonProperty("event_version")
    private String eventVersion;

    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("summary")
    private String summary;

    @JsonProperty("resource_version")
    private String resourceVersion;

    @JsonProperty("resource")
    private Map<String, Object> resource;

    @JsonProperty("links")
    private List<PaypalLinkDescription> links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getEventVersion() {
        return eventVersion;
    }

    public void setEventVersion(String eventVersion) {
        this.eventVersion = eventVersion;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public Map<String, Object> getResource() {
        return resource;
    }

    public void setResource(Map<String, Object> resource) {
        this.resource = resource;
    }

    public List<PaypalLinkDescription> getLinks() {
        return links;
    }

    public void setLinks(List<PaypalLinkDescription> links) {
        this.links = links;
    }
}
