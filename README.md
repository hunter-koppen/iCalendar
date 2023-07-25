## iCalendar/ics Module
An easy way to generate appointment (ics) files to attach to an email.

## Features
- Generate Create/Update/Delete ics appointment files
- Add desired extra input to the appointment like location, organizer etc.

## Dependencies
- Community Commons

The module itself is based on the ical4j java library and comes with a few java libs, some are the same that are used in the community commons library so make sure both are updated to prevent any conflicts.

You can read more about the ical4j library here:
https://www.ical4j.org/

## Usage
Simply add the SUB_IcalMessage_CreateFile microflow to wherever you wish to generate a ics file. It simply needs an IcalMessage object.
Only the start/endtime and subject are mandatory but its recommended to populate all fields.
The Attendee Emails parameter can contain multiple e-mail addresses seperated by a , or a ;

If you wish to test the ics files you can add the SNIP_ICalendar to a page.

It is also recommended to update the sequence number if you want to send an update about an existing appointment you have already send earlier. Like for example when you cancel an already send appointment later on (the first mail will have an ics file with sequence 0 and the next one should have a 1). In that case it is wise to commit the IcalMessage object after sending it the first time and then retrieving it when sending an update. In the SUB_IcalMessage_CreateFile it will already have updated the sequence number.

## Issues, suggestions and feature requests
https://github.com/hunter-koppen/iCalendar/issues