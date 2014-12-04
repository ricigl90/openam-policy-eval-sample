package org.forgerock.openam.examples;

import com.sun.identity.entitlement.EntitlementException;
import com.sun.identity.entitlement.ResourceAttribute;
import org.json.JSONException;
import org.json.JSONObject;

import javax.security.auth.Subject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Sample attribute provider that static "sample" attribute values.
 */
public class SampleAttributeType implements ResourceAttribute {
    private String propertyName;
    private Set<String> propertyValues;
    private String pResponseProviderName;

    @Override
    public void setPropertyName(String name) {
        this.propertyName = name;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public Set<String> getPropertyValues() {
        propertyValues = new HashSet<String>();
        propertyValues.add("sample");
        return propertyValues;
    }

    @Override
    public Map<String, Set<String>> evaluate(Subject adminSubject,
                                             String realm,
                                             Subject subject,
                                             String resourceName,
                                             Map<String, Set<String>> advices)
            throws EntitlementException {
        Map<String, Set<String>> map = new HashMap<String, Set<String>>();
        map.put(propertyName, propertyValues);
        return map;
    }

    @Override
    public void setPResponseProviderName(String pResponseProviderName) {
        this.pResponseProviderName = pResponseProviderName;
    }

    @Override
    public String getPResponseProviderName() {
        return pResponseProviderName;
    }

    @Override
    public String getState() {
        try {
            JSONObject json = new JSONObject();
            json.put("propertyName", propertyName);
            json.put("propertyValues", propertyValues);
            json.put("pResponseProviderName", pResponseProviderName);
            return json.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setState(String state) {
        try {
            JSONObject json = new JSONObject(state);
            propertyName = json.getString("propertyName");
            if (json.has("pResponseProviderName")) {
                pResponseProviderName = json.getString("pResponseProviderName");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}