package com.iwinner.wats.rs.reports;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<Date, Date> {
	/*public Date marshal(Timestamp v) {
        return new Date(v.getTime());
    }*/

	public Date marshal(Date v) throws Exception {
		  return v;
	}

	public Date unmarshal(Date v) throws Exception {
		  return v;
	}
}
