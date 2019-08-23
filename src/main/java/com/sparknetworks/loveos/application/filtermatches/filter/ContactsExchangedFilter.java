package com.sparknetworks.loveos.application.filtermatches.filter;

import com.sparknetworks.loveos.domain.UserMatched;
import com.sparknetworks.loveos.domain.UserMatchedFilter;

public class ContactsExchangedFilter implements UserMatchedFilter {

    private static final int MINIMUM_CONTACTS_EXCHANGED_TO_FILTER = 1;

    @Override
    public boolean isEligible(UserMatched userMatched) {
        return userMatched.getContactsExchanged() >= MINIMUM_CONTACTS_EXCHANGED_TO_FILTER;
    }
}
