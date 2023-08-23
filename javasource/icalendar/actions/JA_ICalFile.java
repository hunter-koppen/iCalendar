// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package icalendar.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import com.mendix.core.Core;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import icalendar.proxies.Attendee;

public class JA_ICalFile extends CustomJavaAction<java.lang.Boolean>
{
	private java.lang.String UID;
	private java.lang.Long Sequence;
	private icalendar.proxies.ENUM_ICalStatus Status;
	private java.util.Date StartDateTime;
	private java.util.Date EndDateTime;
	private java.lang.String TimeZoneCode;
	private java.lang.String Subject;
	private java.lang.String BodyText;
	private java.lang.String Location;
	private java.lang.String OrganizerName;
	private java.lang.String OrganizerEmail;
	private java.util.List<IMendixObject> __AttendeeList;
	private java.util.List<icalendar.proxies.Attendee> AttendeeList;
	private IMendixObject __IcalFile;
	private system.proxies.FileDocument IcalFile;

	public JA_ICalFile(IContext context, java.lang.String UID, java.lang.Long Sequence, java.lang.String Status, java.util.Date StartDateTime, java.util.Date EndDateTime, java.lang.String TimeZoneCode, java.lang.String Subject, java.lang.String BodyText, java.lang.String Location, java.lang.String OrganizerName, java.lang.String OrganizerEmail, java.util.List<IMendixObject> AttendeeList, IMendixObject IcalFile)
	{
		super(context);
		this.UID = UID;
		this.Sequence = Sequence;
		this.Status = Status == null ? null : icalendar.proxies.ENUM_ICalStatus.valueOf(Status);
		this.StartDateTime = StartDateTime;
		this.EndDateTime = EndDateTime;
		this.TimeZoneCode = TimeZoneCode;
		this.Subject = Subject;
		this.BodyText = BodyText;
		this.Location = Location;
		this.OrganizerName = OrganizerName;
		this.OrganizerEmail = OrganizerEmail;
		this.__AttendeeList = AttendeeList;
		this.__IcalFile = IcalFile;
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		this.AttendeeList = java.util.Optional.ofNullable(this.__AttendeeList)
			.orElse(java.util.Collections.emptyList())
			.stream()
			.map(__AttendeeListElement -> icalendar.proxies.Attendee.initialize(getContext(), __AttendeeListElement))
			.collect(java.util.stream.Collectors.toList());

		this.IcalFile = this.__IcalFile == null ? null : system.proxies.FileDocument.initialize(getContext(), __IcalFile);

		// BEGIN USER CODE
        if (IcalFile == null) {
        	LOG.error("No file provided");
            return false;
        }
        
        if (Status == null) {
        	LOG.error("No status set");
        	return false;
        }
		
        if (TimeZoneCode == null) {
            ZoneId timeZone = ZoneId.systemDefault(); // Get the system default time zone
            TimeZoneCode = timeZone.getId();
        }
        
		// Convert the dates
	    Date 	start 	= convertToUtcDateTime(StartDateTime.toInstant()), 
	    		end 	= convertToUtcDateTime(EndDateTime.toInstant());
	    
	    String method = Status.toString();
		
	    SimpleDateFormat dateFormatStamp = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

	    StringBuilder icalBuilder = new StringBuilder();
	    icalBuilder.append("BEGIN:VCALENDAR\r\n");
	    icalBuilder.append("VERSION:2.0\r\n");
	    icalBuilder.append("CALSCALE:GREGORIAN\r\n");
	    icalBuilder.append("METHOD:" + method + "\r\n");
	    icalBuilder.append("PRODID:-//Mendix//iCal4j\r\n");
	    
	    icalBuilder.append("BEGIN:VEVENT\r\n");
	    icalBuilder.append("UID:" + UID + "\r\n");
	    icalBuilder.append("DTSTAMP:" + dateFormatStamp.format(new Date()) + "\r\n");
	    icalBuilder.append("DTSTART;TZID=" + TimeZoneCode + ":" + dateFormat.format(start) + "\r\n");
	    icalBuilder.append("DTEND;TZID=" + TimeZoneCode + ":" + dateFormat.format(end) + "\r\n");
	    icalBuilder.append("SUMMARY:" + Subject + "\r\n");
	    icalBuilder.append("SEQUENCE:" + Sequence + "\r\n");
	    icalBuilder.append("ORGANIZER;CN=\"" + OrganizerName + "\":mailto:" + OrganizerEmail + "\r\n");
	    
	    AttendeeList.stream().forEach(obj -> {
	    	try {
	    		Attendee attendee = Attendee.initialize(getContext(), obj.getMendixObject());
		    	icalBuilder.append("ATTENDEE;PARTSTAT=" + attendee.getStatus().getCaption()+ ";CN=\"" + attendee.getFullname() + "\";EMAIL=" + attendee.getEmail() + ":MAILTO:" + attendee.getEmail() + "\r\n");
	    	} catch (Exception e) {
	    		LOG.error("Could not parse the Attendees, you should not rename the entity Attendee or any of the attributes");
	        }
	    });
	    
	    if (Location != null) {
	    	icalBuilder.append("LOCATION:" + Location + "\r\n");
	    }
	    if (BodyText != null) {
	    	icalBuilder.append("DESCRIPTION:" + BodyText + "\n");
	    }
	    
	    icalBuilder.append("END:VEVENT\r\n");
	    icalBuilder.append("END:VCALENDAR");
	  
	    //Working Example
	    //StringBuilder icalBuilder2 = new StringBuilder();
	    //icalBuilder2.append("BEGIN:VCALENDAR\r\n"
	    		//+ "VERSION:2.0\r\n"
	    		//+ "CALSCALE:GREGORIAN\r\n"
	    		//+ "METHOD:REQUEST\r\n"
	    		//+ "BEGIN:VEVENT\r\n"
	    		//+ "UID:8@<site>.com\r\n"
	    		//+ "DTSTAMP:20230823T151651Z\r\n"
	    		//+ "DTSTART;TZID=Europe/Amsterdam:" + dateFormat.format(start) + "\r\n"
	    		//+ "DTEND;TZID=Europe/Amsterdam:" + dateFormat.format(end) + "\r\n"
	    		//+ "SUMMARY:My Event8\r\n"
	    		//+ "ORGANIZER;CN=\"HomeZero\":mailto:info@homezero.nl\r\n"
	    		//+ "ATTENDEE;PARTSTAT=ACCEPTED;CN=\"Hunter Koppen\";EMAIL=hunter@homezero.nl:MAILTO:hunter@homezero.nl\r\n"
	    		//+ "END:VEVENT\r\n"
	    		//+ "END:VCALENDAR");
	    
		Charset charset = StandardCharsets.UTF_8;
        byte[] byteArray = icalBuilder.toString().getBytes(charset);
        try (InputStream is = new ByteArrayInputStream(byteArray)) {
            // Your code to store the InputStream content
            Core.storeFileDocumentContent(getContext(), this.__IcalFile, is);
            this.IcalFile.setName(getContext(), "event.ics");
        } catch (Exception e) {
        	LOG.error(e);
        }
		return true;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "JA_ICalFile";
	}

	// BEGIN EXTRA CODE
    public static Date convertToUtcDateTime(Instant date) throws Exception {
        // Remove the timezone data from the date so that it get parse properly we convert to a formatted String, Example: "2023-08-03T14:00:00Z"
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        String formattedString  = formatter.format(date);
        //LOG.info("formattedString " + formattedString);
        
        // Parse it back to a date
    	Date newDate = null;
        if (formattedString.length() == 20) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            newDate = sdf.parse(formattedString);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            newDate = sdf.parse(formattedString);
        }
        return newDate;
    }
    
    public static Date applyTimeZone(Date dateTime, String timeZoneId) {
        Date tempDate = dateTime;
		return tempDate;
    }
    
    public static ILogNode LOG = Core.getLogger("iCalendar");
	// END EXTRA CODE
}
