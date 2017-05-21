package com.tenshun.tc.service.dto;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * @author Robert S.
 */
public class ComparisonResult {

    private int count;

    private Collection<Pattern> templates;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Collection<Pattern> getTemplates() {
        return templates;
    }

    public void setTemplates(Collection<Pattern> templates) {
        this.templates = templates;
    }
}
