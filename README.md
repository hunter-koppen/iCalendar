## iCalendar/ics Module

An easy way to generate appointment (ics) files to attach to an email.

## Features

- Generate Create/Update/Delete ics appointment files
- Add desired extra input to the appointment like location, organizer, etc.
- Optionally set a TimeZone

## Dependencies

- None

## Usage

Simply add the `SUB_IcalMessage_CreateFile` microflow to wherever you wish to generate an ics file. It simply needs an `IcalMessage` object. Only the start/end time and subject are mandatory, but it's recommended to populate all fields. The `Attendee Emails` parameter can contain multiple e-mail addresses separated by a comma or semicolon.

If you wish to test the ics files, you can add the `SNIP_ICalendar` to a page.

## Sending an update for an appointment
Updated on the same appointment should have the same UID so that the system understands its about the same event. It is also recommended to update the sequence number if you want to send an update about an existing appointment you have already sent earlier. For example, when you cancel an already sent appointment later on (the first mail will have an ics file with sequence 0, and the next one should have a 1). In that case, it is wise to commit the `IcalMessage` object after sending it the first time and then retrieving it when sending an update. In the `SUB_IcalMessage_CreateFile`, it will already have updated the sequence number.

## TimeZones
There is an optional parameter for a TimeZone. You can leave this empty to use the server timezone. If you do want to use TimeZones I recommended using the TimeZone entity that already exists in the System module of Studio Pro since it already contains the code you need to set a TimeZone. Simply put the Code attribute in the TimeZone field and it should work. You can also set the TimeZone manually if you only want to use 1 perticular zone. (for example "Europe/London").

## Issues, Suggestions, and Feature Requests

Please feel free to raise any issues, share suggestions, or request new features on the GitHub repository:
[iCalendar GitHub Issues](https://github.com/hunter-koppen/iCalendar/issues)
